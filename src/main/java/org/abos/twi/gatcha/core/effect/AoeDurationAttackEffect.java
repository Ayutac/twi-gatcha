package org.abos.twi.gatcha.core.effect;

import org.abos.common.Vec2i;
import org.abos.twi.gatcha.core.battle.Battle;
import org.abos.twi.gatcha.core.battle.CharacterInBattle;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.util.List;

/**
 * For attacks that lead to a duration effect that also has power.
 */
public class AoeDurationAttackEffect extends AoeAttackEffect {

    protected final @Range(from = 0, to = Integer.MAX_VALUE) int duration;

    public AoeDurationAttackEffect(final @NotNull EffectType type,
                                   final @Range(from = 0, to = Integer.MAX_VALUE) int power,
                                   final @Range(from = 0, to = Integer.MAX_VALUE) int duration,
                                   final @Range(from = 0, to = Integer.MAX_VALUE) int rangeMin,
                                   final @Range(from = 0, to = Integer.MAX_VALUE) int rangeMax) {
        super(type, power, rangeMin, rangeMax);
        if (duration < 0) {
            throw new IllegalArgumentException("Duration cannot be negative!");
        }
        this.duration = duration;
    }

    @Override
    public void apply(final CharacterInBattle from, final Vec2i target, final Battle battle) {
        List<CharacterInBattle> aoeTargets = getAoeTargets(target, battle);
        for (final CharacterInBattle aoeTarget : aoeTargets) {
            // changes here should be reflected in DurationAttackEffect
            switch (type) {
                case BUFF_ARMOR, DEBUFF_SPEED -> {
                    aoeTarget.getActiveEffects().add(new DurationEffect(type, power, duration));
                }
                default -> throw new IllegalStateException("An unfitting effect type has been associated with this " + AoeDurationAttackEffect.class.getSimpleName() + "!");
            }
        }
    }
}
