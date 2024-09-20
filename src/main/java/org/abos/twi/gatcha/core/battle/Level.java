package org.abos.twi.gatcha.core.battle;

import org.abos.common.Named;
import org.abos.common.Registerable;
import org.abos.common.Vec2i;
import org.abos.twi.gatcha.core.InventoryMap;
import org.abos.twi.gatcha.core.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.util.List;
import java.util.Objects;
import java.util.Set;

public record Level(String id, int width, int height, List<Terrain> terrainList, Set<Wave> waves, Set<Vec2i> playerSpawns, InventoryMap reward, int staminaNeeded, Set<Level> requirements) implements Named, Registerable<Level> {

    public Level(final @NotNull String id,
                 final @Range(from = 1, to = Integer.MAX_VALUE) int width,
                 final @Range(from = 1, to = Integer.MAX_VALUE) int height,
                 final @NotNull List<Terrain> terrainList,
                 final @NotNull Set<Wave> waves,
                 final @NotNull Set<Vec2i> playerSpawns,
                 final @NotNull InventoryMap reward,
                 final @Range(from = 0, to = Integer.MAX_VALUE) int staminaNeeded,
                 final @NotNull Set<Level> requirements) {
        this.id = Objects.requireNonNull(id);
        this.height = height;
        this.width = width;
        this.terrainList = List.copyOf(terrainList);
        this.waves = Set.copyOf(waves);
        this.playerSpawns = Set.copyOf(playerSpawns);
        this.reward = Objects.requireNonNull(reward);
        if (staminaNeeded < 0) {
            throw new IllegalArgumentException("Stamina needed cannot be negative!");
        }
        this.staminaNeeded = staminaNeeded;
        this.requirements = Set.copyOf(requirements);
    }

    @Override
    public @NotNull String getId() {
        return id;
    }

    @Override
    public @NotNull String getName() {
        return id;
    }

    public @NotNull Battle prepareBattle() {
        return new Battle(id, width, height, terrainList, waves, playerSpawns, reward);
    }

    public boolean satisfiesRequirements(final @NotNull Player player) {
        for (final Level level : requirements) {
            if (player.getStats().getLevelWon(level.id) == 0) {
                return false;
            }
        }
        return true;
    }
}
