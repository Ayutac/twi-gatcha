package org.abos.twi.gatcha.core.effect;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

public class BuffEffect extends SimpleDurationEffect {
    protected final @Range(from = 0, to = Integer.MAX_VALUE) int power;

    public BuffEffect(@NotNull EffectType type, @Range(from = 0, to = Integer.MAX_VALUE) int power, @Range(from = 0, to = Integer.MAX_VALUE) int duration) {
        super(type, duration);
        if (power < 0) {
            throw new IllegalArgumentException("Power cannot be negative!");
        }
        this.power = power;
    }

    public @Range(from = 0, to = Integer.MAX_VALUE) int getPower() {
        return power;
    }
}
