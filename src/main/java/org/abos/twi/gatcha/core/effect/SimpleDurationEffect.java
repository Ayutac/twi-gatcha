package org.abos.twi.gatcha.core.effect;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.util.Objects;

public class SimpleDurationEffect implements Effect {

    protected final @NotNull EffectType type;
    protected final @Range(from = 0, to = Integer.MAX_VALUE) int duration;
    protected @Range(from = 0, to = Integer.MAX_VALUE) int remainingDuration;

    public SimpleDurationEffect(final @NotNull EffectType type, final @Range(from = 0, to = Integer.MAX_VALUE) int duration) {
        this.type = Objects.requireNonNull(type);
        if (duration < 0) {
            throw new IllegalArgumentException("Duration cannot be negative!");
        }
        this.duration = duration;
        remainingDuration = duration;
    }

    @Override
    public @NotNull EffectType getEffectType() {
        return type;
    }

    @Range(from = 0, to = Integer.MAX_VALUE)
    public int getDuration() {
        return duration;
    }

    @Range(from = 0, to = Integer.MAX_VALUE)
    public int getRemainingDuration() {
        return remainingDuration;
    }

    public void decreaseRemainingDuration() {
        remainingDuration--;
    }
}
