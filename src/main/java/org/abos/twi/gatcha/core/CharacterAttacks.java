package org.abos.twi.gatcha.core;

import org.abos.twi.gatcha.core.battle.Attack;

import java.util.Objects;

public record CharacterAttacks(Attack normal, Attack special1, Attack special2) {

    public CharacterAttacks {
        Objects.requireNonNull(normal);
        Objects.requireNonNull(special1);
        Objects.requireNonNull(special2);
    }

}
