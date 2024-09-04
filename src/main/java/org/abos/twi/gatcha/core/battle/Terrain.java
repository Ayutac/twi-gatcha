package org.abos.twi.gatcha.core.battle;

import org.abos.common.Vec2i;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public record Terrain(TerrainType type, Vec2i position) {

    public Terrain(final @NotNull TerrainType type, final @NotNull Vec2i position) {
        this.type = Objects.requireNonNull(type);
        this.position = Objects.requireNonNull(position);
    }

}
