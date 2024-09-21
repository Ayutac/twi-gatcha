package org.abos.twi.gatcha.core.battle.graph;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * A record of a vertex and an associated weight to get to it. The source vertex is not saved.
 * @param <V> the type of vertices
 */
public record WeightedVertex<V>(V vertex, double weight) {

    /**
     * Constructs a new {@link WeightedVertex}.
     * @param vertex the vertex, not {@code null}
     * @param weight the weight to get to said vertex, not negative
     */
    public WeightedVertex(final @NotNull V vertex, final double weight) {
        this.vertex = Objects.requireNonNull(vertex);
        if (weight < 0d) {
            throw new IllegalArgumentException("Weight cannot be negative!");
        }
        this.weight = weight;
    }

}
