package org.abos.twi.gatcha.core.battle;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.util.List;

public record Wave(int turn, List<WaveUnit> units) {

    public Wave(final @Range(from = 0, to = Integer.MAX_VALUE) int turn, final @NotNull List<WaveUnit> units) {
        if (turn < 0) {
            throw new IllegalArgumentException("Turn cannot be negative!");
        }
        this.turn = turn;
        if (units.isEmpty()) {
            throw new IllegalArgumentException("Wave must contain at least one character!");
        }
        this.units = units;
    }

}
