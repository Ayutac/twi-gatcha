package org.abos.twi.gatcha.core;

import org.abos.twi.gatcha.core.battle.Attack;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public record CharacterAttacks(Attack normal, Attack special1, Attack special2) {

    public CharacterAttacks(final @NotNull Attack normal, final @NotNull Attack special1, final @NotNull Attack special2) {
        this.normal = Objects.requireNonNull(normal);
        this.special1 = Objects.requireNonNull(special1);
        this.special2 = Objects.requireNonNull(special2);
    }

}
