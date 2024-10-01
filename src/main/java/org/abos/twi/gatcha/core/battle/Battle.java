package org.abos.twi.gatcha.core.battle;

import org.abos.common.Vec2i;
import org.abos.twi.gatcha.core.CharacterModified;
import org.abos.twi.gatcha.core.InventoryMap;
import org.abos.twi.gatcha.core.Party;
import org.abos.twi.gatcha.core.PlayerStats;
import org.abos.twi.gatcha.core.battle.ai.AiCharacter;
import org.abos.twi.gatcha.core.battle.graph.GridSupplier;
import org.abos.twi.gatcha.core.battle.graph.HexaGridGraphGenerator;
import org.abos.twi.gatcha.core.effect.EffectType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;
import org.jgrapht.alg.shortestpath.BFSShortestPath;
import org.jgrapht.alg.shortestpath.BellmanFordShortestPath;
import org.jgrapht.graph.AbstractBaseGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import org.jgrapht.traverse.BreadthFirstIterator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

/**
 * An instantiation of a {@link Level}.
 */
public class Battle {

    private static final ExecutorService EXECUTOR = Executors.newFixedThreadPool(1);

    /**
     * @see #getLevelId()
     */
    protected final @NotNull String levelId;

    /**
     * @see #getHeight()
     */
    protected final @Range(from = 1, to = Integer.MAX_VALUE) int height;
    /**
     * @see #getWidth()
     */
    protected final @Range(from = 1, to = Integer.MAX_VALUE) int width;
    /**
     * @see #getSize()
     */
    protected final @Range(from = 1, to = Integer.MAX_VALUE) int size;

    /**
     * @see #getUi()
     */
    protected @Nullable BattleUi ui;

    /**
     * @see #getStats();
     */
    protected @Nullable PlayerStats stats;

    /**
     * A list of all characters that were introduced to this battle, in introduction order.
     */
    protected final @NotNull List<CharacterInBattle> characters = new LinkedList<>();
    /**
     * A list of all the non-default terrains on the map.
     * @see #getTerrainTypeAt(Vec2i)
     */
    protected final @NotNull List<Terrain> terrainList;
    /**
     * @see #getTerrainGraph()
     */
    protected final @NotNull AbstractBaseGraph<Vec2i, DefaultEdge> terrainGraph;
    /**
     * Shortest paths on the terrain graph.
     */
    protected final @NotNull BFSShortestPath<Vec2i, DefaultEdge> terrainShortestPaths;
    /**
     * All the places where the player can place their characters at the beginning of the battle.
     */
    protected final @NotNull Set<Vec2i> playerSpawns;
    /**
     * The waves of enemies (or allies) to appear during the battle.
     */
    protected final @NotNull Set<Wave> waves;
    /**
     * @see #getReward()
     */
    protected final @NotNull InventoryMap reward;
    /**
     * @see #getPlacementParty()
     */
    protected final @NotNull Queue<CharacterModified> placementParty = new LinkedList<>();
    /**
     * Saves the initiative rolls of each character participating in the battle.
     */
    protected final @NotNull Map<CharacterInBattle, Double> initiativeRolls = new HashMap<>();
    /**
     * @see #getCharacterOrder()
     */
    protected final @NotNull List<CharacterInBattle> characterOrder = new ArrayList<>();
    /**
     * @see #getPossiblePlayerFields()
     */
    protected final @NotNull Map<Vec2i, Double> possiblePlayerFields = new HashMap<>();
    /**
     * @see #getPossibleAttackFields()
     */
    protected final @NotNull List<Vec2i> possibleAttackFields = new LinkedList<>();

    /**
     * @see #getPhase()
     */
    protected @NotNull BattlePhase phase = BattlePhase.INACTIVE;
    /**
     * @see #getRandom()
     */
    protected @NotNull Random random;
    /**
     * @see #getCurrentCharacter()
     */
    protected @Nullable CharacterInBattle currentCharacter = null;
    /**
     * @see #getSelectedAttack()
     */
    protected @Nullable Attack selectedAttack = null;

    /**
     * Creates a new {@link Battle}.
     * @param width the number of horizontal hex tiles, positive
     * @param height the number of vertical hex tiles, positive
     * @param terrainList a list with terrains to supplement this battle's field with, can be empty but not {@code null}
     * @param waves the waves for this battle, can be empty but not {@code null}; each turn can only have one wave
     * @param playerSpawns spots the player can place their characters, not empty or {@code null}
     * @throws IllegalArgumentException If the constraints are violated, the field is too big or any positions
     *                                  given by the terrain, waves or spawns are outside the field.
     */
    public Battle(final @NotNull String levelId,
                  final @Range(from = 1, to = Integer.MAX_VALUE) int width,
                  final @Range(from = 1, to = Integer.MAX_VALUE) int height,
                  final @NotNull List<Terrain> terrainList,
                  final @NotNull Set<Wave> waves,
                  final @NotNull Set<Vec2i> playerSpawns,
                  final @NotNull InventoryMap reward) {
        this.levelId = Objects.requireNonNull(levelId);
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
        for (final Wave wave : waves) {
            for (final WaveUnit unit : wave.units()) {
                if (!contains(unit.startPos())) {
                    throw new IllegalArgumentException("Unit spawn must be within field!");
                }
            }
        }
        this.waves = Set.copyOf(waves);
        this.reward = Objects.requireNonNull(reward);
        if (playerSpawns.isEmpty()) {
            throw new IllegalArgumentException("At least 1 player spawn point must be provided!");
        }
        for (final Vec2i spawnPosition : playerSpawns) {
            if (!contains(spawnPosition)) {
                throw new IllegalArgumentException("Player spawn must be within field!");
            }
        }
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
        // misc
        terrainShortestPaths = new BFSShortestPath<>(terrainGraph);
        random = new Random((long) size * (1 + terrainList.size()));
    }

    /**
     * Returns the ID of the level this battle was generated from.
     * @return the ID of this battle's level, not {@code null}
     */
    public @NotNull String getLevelId() {
        return levelId;
    }

    /**
     * @return the positive number of vertical tiles on this battle's field
     */
    public @Range(from = 1, to = Integer.MAX_VALUE) int getHeight() {
        return height;
    }

    /**
     * @return the positive number of horizontal tiles on this battle's field
     */
    public @Range(from = 1, to = Integer.MAX_VALUE) int getWidth() {
        return width;
    }

    /**
     * @return the positive number of tiles on this battle's field
     */
    public @Range(from = 1, to = Integer.MAX_VALUE) int getSize() {
        return size;
    }

    /**
     * @return the reward for this battle
     */
    public @NotNull InventoryMap getReward() {
        return reward;
    }

    /**
     * Returns the UI associated with this battle.
     * @return the UI of this battle, might be {@code null}
     * @see #setUi(BattleUi)
     */
    public @Nullable BattleUi getUi() {
        return ui;
    }

    /**
     * Sets the UI associated with this battle.
     * @param ui the new UI for this battle, might be {@code null}
     * @see #getUi()
     */
    public void setUi(final @Nullable BattleUi ui) {
        this.ui = ui;
    }

    /**
     * Returns the player stats associated with this battle.
     * @return the player stats of this battle, might be {@code null}
     * @see #setStats(PlayerStats)
     */
    public @Nullable PlayerStats getStats() {
        return stats;
    }

    /**
     * Sets the player stats associated with this battle.
     * @param stats the new player stats for this battle, might be {@code null}
     * @see #getStats()
     */
    public void setStats(final @Nullable PlayerStats stats) {
        this.stats = stats;
    }

    /**
     * @return the {@link BattlePhase} this battle is in, not {@code null}
     */
    public @NotNull BattlePhase getPhase() {
        return phase;
    }

    /**
     * Once the {@link BattlePhase#PLACEMENT} started, this queue contains all party members of the player that still have to be placed.
     * @return the player's party to place, not {@code null} but might be empty
     * @see #startPlacement(Party)
     */
    public @NotNull Queue<CharacterModified> getPlacementParty() {
        return placementParty;
    }

    /**
     * Once the {@link BattlePhase#IN_PROGRESS} started, contains the characters currently involved in battle in the order of their turns.
     * @return the battle's characters in their turn order, not {@code null} but might be empty
     */
    public @NotNull List<CharacterInBattle> getCharacterOrder() {
        return characterOrder;
    }

    /**
     * During a player's turn before they moved, this will contain all the fields the player's character can move to.
     * @return a map of the current (player) character's possible fields to go to, not {@code null}
     * @see #getCurrentCharacter()
     */
    public @NotNull Map<Vec2i, Double> getPossiblePlayerFields() {
        return possiblePlayerFields;
    }

    public @NotNull List<Vec2i> getPossibleAttackFields() {
        return possibleAttackFields;
    }

    /**
     * A {@link Random} instance for this battle.
     * @return a random instance, not {@code null}
     */
    public @NotNull Random getRandom() {
        return random;
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
        var paths = terrainShortestPaths.getPaths(currentCharacter.getPosition());
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

    public TerrainType getTerrainTypeAt(final @NotNull Vec2i position) {
        if (!contains(position)) {
            throw new IllegalArgumentException("Position isn't part of the battlefield!");
        }
        for (final Terrain terrain : terrainList) {
            if (position.equals(terrain.position())) {
                return terrain.type();
            }
        }
        return TerrainType.DEFAULT;
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

    public void startPlacement(final @NotNull Party party) {
        releaseWave(0);
        phase = BattlePhase.PLACEMENT;
        this.placementParty.addAll(party.characters());
        if (stats != null) {
            for (final CharacterModified character : party.characters()) {
                stats.increaseCharacterInSquat(character);
            }
        }
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
        if (stats != null) {
            stats.increaseCharacterDeployed(character);
        }
        if (ui != null) {
            ui.characterPlaced(cib, cib.getPosition());
        }
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

    public boolean noFreeSpawns() {
        boolean result = true;
        for (final Vec2i position : playerSpawns) {
            if (!isCharacterAt(position)) {
                result = false;
                break;
            }
        }
        return result;
    }

    public void removeCharacter(final @NotNull CharacterInBattle character) {
        characters.remove(character);
        characterOrder.remove(character);
        if (stats != null) {
            if (character.getTeam() == TeamKind.PLAYER) {
                stats.increaseCharacterDeployedDefeated(character.getModified());
            }
            else if (character.getTeam() == TeamKind.ENEMY) {
                stats.increaseEnemyDefeated(character.getModified());
            }
        }
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
        EXECUTOR.submit(() -> {
            int turnNr = 0;
            while (!checkDone()) {
                turn(++turnNr);
            }
        });
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

    protected void turn(final @Range(from = 1, to = Integer.MAX_VALUE) int turn) {
        currentCharacter = characterOrder.getFirst();
        while (currentCharacter != null && !checkDone()) {
            currentCharacter.startTurn();
            // stunned characters don't get to execute their turn
            if (currentCharacter.getPersistentEffects().stream().noneMatch(e -> e.getEffectType() == EffectType.STUN)) {
                if (currentCharacter instanceof AiCharacter) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ex) {
                        Logger.getGlobal().warning(ex.getMessage());
                    }
                    currentCharacter.turn();
                }
                // if no UI is set, the player character's turn is skipped
                else if (ui != null) {
                    prepareForPlayer();
                    ui.waitForPlayer().join();
                }
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
        releaseWave(turn);
    }

    private void releaseWave(final @Range(from = 0, to = Integer.MAX_VALUE) int turn) {
        final Optional<Wave> wave = waves.stream().filter(w -> w.turn() == turn).findFirst();
        if (wave.isEmpty()) {
            return;
        }
        for (final WaveUnit unit : wave.get().units()) {
            final CharacterInBattle character = unit.createCharacterInBattle(Battle.this);
            // if position is already taken
            if (isCharacterAt(character.getPosition())) {
                Vec2i newPos = null;
                // appear at next nearest position
                final var it = new BreadthFirstIterator<>(terrainGraph);
                while (it.hasNext()) {
                    final Vec2i position = it.next();
                    if (!isCharacterAt(position)) {
                        newPos = position;
                        break;
                    }
                }
                // if there is no position available, this unit doesn't get placed
                if (newPos == null) {
                    continue;
                }
                // update the position
                character.setPosition(newPos);
            }
            summon(character);
        }
    }

    public boolean summon(CharacterInBattle character) {
        if (isCharacterAt(character.getPosition())) {
            return false;
        }
        // actually set the character on the board
        characters.add(character);
        if (ui != null) {
            ui.characterPlaced(character, character.getPosition());
        }
        // add character to the turn order
        addCharacterToOrder(character);
        return true;
    }

    private void addCharacterToOrder(CharacterInBattle character) {
        final double initiative = character.getInitiative();
        final ListIterator<CharacterInBattle> it = characterOrder.listIterator(characterOrder.size());
        CharacterInBattle other;
        while (it.hasPrevious()) {
            other = it.previous();
            if (other.getInitiative() > initiative) {
                it.next();
                it.add(character);
                break;
            }
        }
    }

    private void prepareForPlayer() {
        if (currentCharacter == null) {
            return;
        }
        possiblePlayerFields.clear();
        final var moveGraph = getCharacterMovementGraph(currentCharacter);
        final var paths = new BellmanFordShortestPath<>(moveGraph).getPaths(currentCharacter.position);
        for (final Vec2i position : moveGraph.vertexSet()) {
            double weight = paths.getWeight(position);
            if (weight <= currentCharacter.getMovement()) {
                possiblePlayerFields.put(position, weight);
            }
        }
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
        final boolean allEnemiesDefeated = characters.stream().noneMatch(character -> character.getTeam() == TeamKind.ENEMY);
        final boolean allPlayersDefeated = characters.stream().noneMatch(character -> character.getTeam() == TeamKind.PLAYER);
        if (allPlayersDefeated || allEnemiesDefeated) {
            phase = BattlePhase.DONE;
            if (ui != null) {
                if (allPlayersDefeated && allEnemiesDefeated) {
                    ui.hasTied();
                }
                else if (allPlayersDefeated) {
                    ui.hasLost();
                }
                else {
                    ui.hasWon();
                }
            }
        }
        return phase == BattlePhase.DONE;
    }

    public static void shutdown() {
        EXECUTOR.shutdownNow();
    }
}
