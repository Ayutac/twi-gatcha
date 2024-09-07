package org.abos.twi.gatcha.core.effect;

import org.abos.common.Vec2i;
import org.abos.twi.gatcha.core.battle.Battle;
import org.abos.twi.gatcha.core.battle.CharacterInBattle;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.util.Optional;

/**
 * For attacks that lead to a duration effect that also has power.
 */
public class DurationAttackEffect extends SimpleAttackEffect {

    protected final @Range(from = 0, to = Integer.MAX_VALUE) int duration;

    public DurationAttackEffect(final @NotNull EffectType type,
                                final @Range(from = 0, to = Integer.MAX_VALUE) int power,
                                final @Range(from = 0, to = Integer.MAX_VALUE) int duration) {
        super(type, power);
        if (duration < 0) {
            throw new IllegalArgumentException("Duration cannot be negative!");
        }
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
        // changes here should be reflected in AoeDurationAttackEffect
        switch (type) {
            case BUFF_DEFENSE, BUFF_ATTACK, BUFF_SPEED, DEBUFF_SPEED -> {
                to.get().getActiveEffects().add(new DurationEffect(type, power, duration));
            }
            default -> throw new IllegalStateException("An unfitting effect type has been associated with this " + DurationAttackEffect.class.getSimpleName() + "!");
        }
    }
}
