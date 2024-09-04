package org.abos.twi.gatcha.core.battle;

import org.abos.common.Vec2i;
import org.abos.twi.gatcha.core.battle.graph.GridSupplier;
import org.abos.twi.gatcha.core.battle.graph.HexaGridGraphGenerator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;
import org.jgrapht.graph.AbstractBaseGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public class Battle {

    protected final @Range(from = 1, to = Integer.MAX_VALUE) int height;
    protected final @Range(from = 1, to = Integer.MAX_VALUE) int width;
    protected final @Range(from = 1, to = Integer.MAX_VALUE) int size;

    protected final List<CharacterInBattle> characters = new LinkedList<>();
    protected final List<Terrain> terrainList;
    protected final AbstractBaseGraph<Vec2i, DefaultEdge> terrainGraph;
    protected final Set<Wave> waves = new HashSet<>();

    protected @NotNull BattlePhase phase = BattlePhase.INACTIVE;

    public Battle(final @Range(from = 1, to = Integer.MAX_VALUE) int height,
                  final @Range(from = 1, to = Integer.MAX_VALUE) int width,
                  final @NotNull List<Terrain> terrainList) {
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

    public boolean contains(final Vec2i position) {
        return position.x() >= 0 && position.y() >= 0 && position.x() < width && position.y() < height;
    }

    public void addWave(final @NotNull Wave wave) {
        if (phase != BattlePhase.INACTIVE) {
            throw new IllegalStateException("No more waves can be added!");
        }
        if (waves.stream().anyMatch(w -> wave.turn() == w.turn())) {
            throw new IllegalArgumentException("Each turn can at most have one wave!");
        }
        waves.add(wave);
    }

    public void startPlacement() {
        final Optional<Wave> firstWave = waves.stream().filter(w -> w.turn() == 0).findFirst();
        // TODO avoid character collision
        firstWave.ifPresent(wave -> characters.addAll(wave.characters()));
        phase = BattlePhase.PLACEMENT;
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

    public boolean removeCharacter(final CharacterInBattle character) {
        return characters.remove(character);
    }

    public AbstractBaseGraph<Vec2i, DefaultEdge> getCharacterMovementGraph(final CharacterInBattle character) {
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
}
