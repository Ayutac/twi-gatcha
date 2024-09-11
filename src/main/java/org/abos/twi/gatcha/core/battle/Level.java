package org.abos.twi.gatcha.core.battle;

import org.abos.common.Vec2i;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.util.List;
import java.util.Set;

public record Level(int width, int height, List<Terrain> terrainList, Set<Wave> waves, Set<Vec2i> playerSpawns) {

    public Level(final @Range(from = 1, to = Integer.MAX_VALUE) int width,
                 final @Range(from = 1, to = Integer.MAX_VALUE) int height,
                 final @NotNull List<Terrain> terrainList,
                 final @NotNull Set<Wave> waves,
                 final @NotNull Set<Vec2i> playerSpawns) {
        this.height = height;
        this.width = width;
        this.terrainList = List.copyOf(terrainList);
        this.waves = Set.copyOf(waves);
        this.playerSpawns = Set.copyOf(playerSpawns);
    }

    public Battle prepareBattle() {
        return new Battle(width, height, terrainList, waves, playerSpawns);
    }
}
