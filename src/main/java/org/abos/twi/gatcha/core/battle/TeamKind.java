package org.abos.twi.gatcha.core.battle;

import org.jetbrains.annotations.NotNull;

public enum TeamKind {

    PLAYER,
    ALLY,
    ENEMY;

    public boolean attacks(final @NotNull TeamKind kind) {
        return switch (this) {
            case PLAYER, ALLY -> kind == ENEMY;
            case ENEMY -> kind != ENEMY;
        };
    }

}
