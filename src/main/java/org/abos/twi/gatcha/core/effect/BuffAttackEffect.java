package org.abos.twi.gatcha.core.effect;

import org.abos.common.Vec2i;
import org.abos.twi.gatcha.core.battle.Battle;
import org.abos.twi.gatcha.core.battle.CharacterInBattle;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.util.Objects;
import java.util.Optional;

public record BuffAttackEffect(EffectType type, int power, int duration) implements AttackEffect {

    public BuffAttackEffect(final @NotNull EffectType type,
                            final @Range(from = 0, to = Integer.MAX_VALUE) int power,
                            final @Range(from = 0, to = Integer.MAX_VALUE) int duration) {
        this.type = Objects.requireNonNull(type);
        if (power < 0 || duration < 0) {
            throw new IllegalArgumentException("Power and duration cannot be negative!");
        }
        this.power = power;
        this.duration = duration;
    }

    @Override
    public @NotNull EffectType getEffectType() {
        return type;
    }

    @Override
    public void apply(final CharacterInBattle from, final Vec2i target, final Battle battle) {
        final Optional<CharacterInBattle> to = battle.getCharacterAt(target);
        if (to.isEmpty()) {
            return;
        }
        switch (type) {
            case BUFF_ARMOR, DEBUFF_SPEED -> {
                to.get().getActiveEffects().add(new BuffEffect(type, power, duration));
            }
            default -> throw new IllegalStateException("An unfitting effect type has been associated with this " + BuffAttackEffect.class.getSimpleName() + "!");
        }
    }
}
