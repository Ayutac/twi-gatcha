package org.abos.twi.gatcha.core.battle;

import org.abos.common.Vec2i;
import org.abos.twi.gatcha.core.CharacterModified;
import org.abos.twi.gatcha.core.battle.ai.AiCharacter;
import org.abos.twi.gatcha.core.battle.graph.GridSupplier;
import org.abos.twi.gatcha.core.battle.graph.HexaGridGraphGenerator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;
import org.jgrapht.alg.shortestpath.BFSShortestPath;
import org.jgrapht.alg.shortestpath.BellmanFordShortestPath;
import org.jgrapht.graph.AbstractBaseGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.logging.Logger;

public class Battle {

    protected final @Range(from = 1, to = Integer.MAX_VALUE) int height;
    protected final @Range(from = 1, to = Integer.MAX_VALUE) int width;
    protected final @Range(from = 1, to = Integer.MAX_VALUE) int size;

    protected final List<CharacterInBattle> characters = new LinkedList<>();
    protected final List<Terrain> terrainList;
    protected final AbstractBaseGraph<Vec2i, DefaultEdge> terrainGraph;
    protected final @NotNull Set<Vec2i> playerSpawns;
    protected final @NotNull Set<Wave> waves;
    protected final @NotNull Queue<CharacterModified> placementParty = new LinkedList<>();
    protected final @NotNull Map<CharacterInBattle, Double> initiativeRolls = new HashMap<>();
    protected final @NotNull List<CharacterInBattle> characterOrder = new ArrayList<>();
    protected final @NotNull Map<Vec2i, Double> possiblePlayerFields = new HashMap<>();
    protected final @NotNull List<Vec2i> possibleAttackFields = new LinkedList<>();

    protected @NotNull BattlePhase phase = BattlePhase.INACTIVE;
    protected @NotNull Random random;
    protected @Nullable CharacterInBattle currentCharacter = null;
    protected @Nullable Attack selectedAttack = null;
    protected boolean playerMoveDone = true;
    protected boolean playerAttackDone = true;

    public Battle(final @Range(from = 1, to = Integer.MAX_VALUE) int height,
                  final @Range(from = 1, to = Integer.MAX_VALUE) int width,
                  final @NotNull List<Terrain> terrainList,
                  final @NotNull Set<Wave> waves,
                  final @NotNull Set<Vec2i> playerSpawns) {
        if (height < 1 || width < 1) {
            throw new IllegalArgumentException("Dimensions must be positive!");
        }
        this.height = height;
        this.width = width;
        try {
            this.size = Math.multiplyExact(height, width);
        }
        catch (ArithmeticException ex) {
            throw new IllegalArgumentException("Dimensions are too big!");
        }
        for (final Terrain terrain : terrainList) {
            if (!contains(terrain.position())) {
                throw new IllegalArgumentException("Terrain must be within field!");
            }
        }
        this.terrainList = List.copyOf(terrainList);
        if (waves.size() != waves.stream().mapToInt(Wave::turn).distinct().count()) {
            throw new IllegalArgumentException("A turn can have at most one wave!");
        }
        this.waves = Set.copyOf(waves);
        this.playerSpawns = Set.copyOf(playerSpawns);
        // generate terrain graph
        terrainGraph = new SimpleDirectedWeightedGraph<>(DefaultEdge.class);
        terrainGraph.setVertexSupplier(new GridSupplier(height, width));
        new HexaGridGraphGenerator<Vec2i, DefaultEdge>(height, width).generateGraph(terrainGraph);
        // set default weight
        for (final DefaultEdge e : terrainGraph.edgeSet()) {
            terrainGraph.setEdgeWeight(e, 1d);
        }
        // set terrain weight
        for (final Terrain terrain : terrainList) {
            if (terrain.type().isBlocked()) {
                terrainGraph.removeVertex(terrain.position());
            }
            else {
                for (final DefaultEdge e : terrainGraph.incomingEdgesOf(terrain.position())) {
                    terrainGraph.setEdgeWeight(e, terrain.type().getMovementCost());
                }
            }
        }
        random = new Random((long) size * (1 + terrainList.size()));
    }

    @Range(from = 1, to = Integer.MAX_VALUE)
    public int getHeight() {
        return height;
    }

    @Range(from = 1, to = Integer.MAX_VALUE)
    public int getWidth() {
        return width;
    }

    @Range(from = 1, to = Integer.MAX_VALUE)
    public int getSize() {
        return size;
    }

    public @NotNull BattlePhase getPhase() {
        return phase;
    }

    public @NotNull Queue<CharacterModified> getPlacementParty() {
        return placementParty;
    }

    public @NotNull List<CharacterInBattle> getCharacterOrder() {
        return characterOrder;
    }

    public @NotNull Map<Vec2i, Double> getPossiblePlayerFields() {
        return possiblePlayerFields;
    }

    public @NotNull List<Vec2i> getPossibleAttackFields() {
        return possibleAttackFields;
    }

    public @Nullable CharacterInBattle getCurrentCharacter() {
        return currentCharacter;
    }

    public @Nullable Attack getSelectedAttack() {
        return selectedAttack;
    }

    public void setSelectedAttack(@Nullable Attack selectedAttack) {
        this.selectedAttack = selectedAttack;
        possibleAttackFields.clear();
        if (currentCharacter == null || selectedAttack == null) {
            return;
        }
        var paths = new BFSShortestPath<>(terrainGraph).getPaths(currentCharacter.getPosition());
        for (final Vec2i position : terrainGraph.vertexSet()) {
            final int length = paths.getPath(position).getLength();
            if (selectedAttack.rangeMin() <= length && length <= selectedAttack.rangeMax()) {
                possibleAttackFields.add(position);
            }
        }
    }

    public boolean contains(final @NotNull Vec2i position) {
        return position.x() >= 0 && position.y() >= 0 && position.x() < width && position.y() < height;
    }

    public boolean isCharacterAt(final @NotNull Vec2i position) {
        for (final CharacterInBattle character : characters) {
            if (character.isAt(position)) {
                return true;
            }
        }
        return false;
    }

    public boolean isPlayerSpawnAt(final @NotNull Vec2i position) {
        return playerSpawns.contains(position);
    }

    public void startPlacement(final @NotNull List<CharacterModified> party) {
        final Optional<Wave> firstWave = waves.stream().filter(w -> w.turn() == 0).findFirst();
        // TODO avoid character collision
        firstWave.ifPresent(wave -> {
            for (final WaveUnit unit : wave.units()) {
                characters.add(unit.createCharacterInBattle(Battle.this));
            }
        });
        phase = BattlePhase.PLACEMENT;
        this.placementParty.addAll(party);
    }

    public CharacterInBattle placePlayerCharacterAt(final @NotNull CharacterModified character, final @NotNull Vec2i position) {
        if (!isPlayerSpawnAt(position)) {
            throw new IllegalArgumentException("Position cannot be used!");
        }
        if (!getCharacterMovementGraph(null).containsVertex(position)) {
            throw new IllegalArgumentException("Position is already taken!");
        }
        final CharacterInBattle cib = new CharacterInBattle(character, this, TeamKind.PLAYER, position);
        characters.add(cib);
        return cib;
    }

    @NotNull
    public Optional<CharacterInBattle> getCharacterAt(final Vec2i position) {
        for (final CharacterInBattle character : characters) {
            if (character.isAt(position)) {
                return Optional.of(character);
            }
        }
        return Optional.empty();
    }

    public void removeCharacter(final CharacterInBattle character) {
        characters.remove(character);
        characterOrder.remove(character);
    }

    public AbstractBaseGraph<Vec2i, DefaultEdge> getTerrainGraph() {
        return terrainGraph;
    }

    public AbstractBaseGraph<Vec2i, DefaultEdge> getCharacterMovementGraph(final @Nullable CharacterInBattle character) {
        AbstractBaseGraph<Vec2i, DefaultEdge> result = (AbstractBaseGraph<Vec2i, DefaultEdge>)terrainGraph.clone();
        for (final CharacterInBattle other : characters) {
            if (other == character) {
                continue;
            }
            // we can't move onto another character
            result.removeVertex(other.getPosition());
        }
        return result;
    }

    public void start() {
        phase = BattlePhase.IN_PROGRESS;
        rollForInitiative();
        // TODO use an ExecutionService
        new Thread(() -> {
            while (!checkDone()) {
                turn();
            }
        }).start();
    }

    public void rollForInitiative() {
        for (final CharacterInBattle character : characters) {
            initiativeRolls.put(character, character.getInitiative());
        }
        // sort distinct values starting with the greatest
        final List<Double> values = initiativeRolls.values().stream().sorted((d1, d2) -> Double.compare(d2, d1)).distinct().toList();
        for (double init : values) { // the order will be preserved in this for-loop
            // get all characters with the same initiative
            final List<CharacterInBattle> allWithInit = new ArrayList<>(initiativeRolls.entrySet().stream().filter(entry -> entry.getValue() == init).map(Map.Entry::getKey).toList());
            if (allWithInit.isEmpty()) {
                throw new AssertionError("Character vanished from the list!"); // shouldn't happen
            }
            Collections.shuffle(allWithInit, random);
            characterOrder.addAll(allWithInit);
        }
    }

    protected void turn() {
        currentCharacter = characterOrder.getFirst();
        while (currentCharacter != null) {
            currentCharacter.startTurn();
            if (currentCharacter instanceof AiCharacter) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    Logger.getGlobal().warning(ex.getMessage());
                }
                currentCharacter.turn();
            }
            else {
                waitForPlayer(currentCharacter);
            }
            currentCharacter.endTurn();
            // next character
            final int index = characterOrder.indexOf(currentCharacter) + 1;
            if (index == characterOrder.size()) {
                currentCharacter = null;
            }
            else {
                currentCharacter = characterOrder.get(index);
            }
        }
        // TODO introduce new waves
    }

    public void waitForPlayer(final @NotNull CharacterInBattle character) {
        playerMoveDone = false;
        playerAttackDone = false;
        possiblePlayerFields.clear();
        final var moveGraph = getCharacterMovementGraph(character);
        final var paths = new BellmanFordShortestPath<>(moveGraph).getPaths(character.position);
        for (final Vec2i position : moveGraph.vertexSet()) {
            double weight = paths.getWeight(position);
            if (weight <= character.getMovement()) {
                possiblePlayerFields.put(position, weight);
            }
        }
        while (!playerMoveDone) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        while (!playerAttackDone) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public boolean isPlayerMove() {
        return !playerMoveDone;
    }

    public boolean isPlayerAttack() {
        return !playerAttackDone;
    }

    public boolean isPlayerTurn() {
        return !playerMoveDone || !playerAttackDone;
    }

    public void playerMoveIsDone() {
        playerMoveDone = true;
    }

    public void playerAttackIsDone() {
        playerAttackDone = true;
    }

    /**
     * Checks if the battle is over.
     * @return {@code true} if the battle is over, else {@code false}
     */
    public boolean checkDone() {
        if (phase == BattlePhase.DONE) {
            return true;
        }
        // game is over if there are no player characters or no enemies anymore
        if (characters.stream().noneMatch(character -> character.getTeam() == TeamKind.PLAYER) ||
                characters.stream().noneMatch(character -> character.getTeam() == TeamKind.ENEMY)) {
            phase = BattlePhase.DONE;
        }
        return phase == BattlePhase.DONE;
    }
}
