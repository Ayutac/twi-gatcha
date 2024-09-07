package org.abos.twi.gatcha.core.effect;

import org.abos.common.Vec2i;
import org.abos.twi.gatcha.core.battle.Battle;
import org.abos.twi.gatcha.core.battle.CharacterInBattle;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.util.Objects;
import java.util.Optional;

public record SimpleAttackEffect(EffectType type, int power) implements AttackEffect {

    public SimpleAttackEffect(final @NotNull EffectType type, final @Range(from = 0, to = Integer.MAX_VALUE) int power) {
        this.type = Objects.requireNonNull(type);
        if (power < 0) {
            throw new IllegalArgumentException("Power cannot be negative!");
        }
        this.power = power;
    }

    @Override
    public @NotNull EffectType getEffectType() {
        return type();
    }

    @Override
    public void apply(final CharacterInBattle from, final Vec2i target, final Battle battle) {
        final Optional<CharacterInBattle> to = battle.getCharacterAt(target);
        if (to.isEmpty()) {
            return;
        }
        switch (type) {
            case DAMAGE_BLUNT, DAMAGE_SLASH, DAMAGE_PIERCE -> {
                int dmg = Math.min(1, from.getAttack() - to.get().getDefense() + power);
                to.get().takeDamage(dmg);
            }
            case HEALING -> {
                int heal = from.getAttack() + power;
                to.get().heal(heal);
            }
            case INVISIBILITY -> {
                to.get().getActiveEffects().add(new SimpleDurationEffect(EffectType.INVISIBILITY, power));
            }
            default -> throw new IllegalStateException("An unfitting effect type has been associated with this " + SimpleAttackEffect.class.getSimpleName() + "!");
        }
    }
}
