package org.abos.twi.gatcha.core.battle;

import org.abos.common.Vec2i;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.util.Map;

public record Wave(int turn, Map<Vec2i, CharacterInBattle> characters) {

    public Wave(final @Range(from = 0, to = Integer.MAX_VALUE) int turn, final @NotNull Map<Vec2i, CharacterInBattle> characters) {
        if (turn < 0) {
            throw new IllegalArgumentException("Turn cannot be negative!");
        }
        this.turn = turn;
        if (characters.containsKey(null) || characters.containsValue(null)) {
            throw new NullPointerException("null is not allowed as key or value!");
        }
        if (characters.isEmpty()) {
            throw new IllegalArgumentException("Wave must contain at least one character!");
        }
        this.characters = characters;
    }

}
