package org.abos.twi.gatcha.core.battle;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.util.Objects;
import java.util.Optional;

public record SimpleEffect(EffectType type, int power) implements Effect {

    public SimpleEffect(final @NotNull EffectType type, final @Range(from = 0, to = Integer.MAX_VALUE) int power) {
        this.type = Objects.requireNonNull(type);
        if (power < 0) {
            throw new IllegalArgumentException("Power cannot be negative!");
        }
        this.power = power;
    }

    @Override
    public EffectType getEffectType() {
        return type();
    }

    @Override
    public void apply(final CharacterInBattle from, final Position target, final Field field) {
        final Optional<CharacterInBattle> to = field.getCharacterAt(target);
        if (to.isEmpty()) {
            return;
        }
        switch (type) {
            case DAMAGE -> {
                int dmg = Math.min(1, from.getAttack() - to.get().getDefense() + power);
                to.get().takeDamage(dmg);
            }
            case HEALING -> {
                int heal = from.getAttack() + power;
                to.get().heal(heal);
            }
            default -> throw new IllegalStateException("An unfitting effect type has been associated with this " + SimpleEffect.class.getSimpleName() + "!");
        }
    }
}
