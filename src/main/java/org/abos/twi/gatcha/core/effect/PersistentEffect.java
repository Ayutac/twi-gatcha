package org.abos.twi.gatcha.core.effect;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

public class PersistentEffect extends Effect {

    protected @Range(from = 0, to = Integer.MAX_VALUE) int remainingDuration;

    public PersistentEffect(final @NotNull EffectType effectType,
                            final @Range(from = 0, to = Integer.MAX_VALUE) int maxPower,
                            final @Range(from = 0, to = Integer.MAX_VALUE) int maxDuration,
                            final @Nullable String affectedGroupId) {
        super(effectType, maxPower, maxDuration, affectedGroupId);
        remainingDuration = maxDuration;
    }

    public @Range(from = 0, to = Integer.MAX_VALUE) int getRemainingDuration() {
        return remainingDuration;
    }

    public void setRemainingDuration(final @Range(from = 0, to = Integer.MAX_VALUE) int remainingDuration) {
        if (remainingDuration < 0) {
            throw new IllegalArgumentException("Remaining duration cannot be negative!");
        }
        this.remainingDuration = remainingDuration;
    }

    public void decreaseRemainingDuration() {
        if (remainingDuration > 0) {
            remainingDuration--;
        }
    }
}
