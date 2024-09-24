package org.abos.twi.gatcha.core.battle;

import java.util.List;

public record Wave(int turn, List<WaveUnit> units) {

    public Wave {
        if (turn < 0) {
            throw new IllegalArgumentException("Turn cannot be negative!");
        }
        if (units.isEmpty()) {
            throw new IllegalArgumentException("Wave must contain at least one character!");
        }
    }

}
