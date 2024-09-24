package org.abos.twi.gatcha.core.battle;

import org.abos.common.Vec2i;

import java.util.Objects;

public record Terrain(TerrainType type, Vec2i position) {

    public Terrain {
        Objects.requireNonNull(type);
        Objects.requireNonNull(position);
    }

}
