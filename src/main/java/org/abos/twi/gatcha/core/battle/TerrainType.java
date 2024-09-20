package org.abos.twi.gatcha.core.battle;

import org.jetbrains.annotations.Range;

public enum TerrainType {

    BLOCKED(Integer.MAX_VALUE),
    HILL(2),
    DEFAULT(1);

    final @Range(from = 1, to = Integer.MAX_VALUE) int movementCost;

    TerrainType(final @Range(from = 1, to = Integer.MAX_VALUE) int movementCost) {
        if (movementCost < 1) {
            throw new IllegalArgumentException("Movement Cost must be positive!");
        }
        this.movementCost = movementCost;
    }

    @Range(from = 1, to = Integer.MAX_VALUE)
    public int getMovementCost() {
        return movementCost;
    }

    public boolean isBlocked() {
        return movementCost == Integer.MAX_VALUE;
    }
}
