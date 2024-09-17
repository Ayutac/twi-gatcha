package org.abos.twi.gatcha.core.effect;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

public class DeterioratingEffect extends PersistentEffect {

    protected @Range(from = 0, to = Integer.MAX_VALUE) int remainingPower;

    public DeterioratingEffect(final @NotNull EffectType effectType,
                               final @Range(from = 0, to = Integer.MAX_VALUE) int maxPower,
                               final @Range(from = 0, to = Integer.MAX_VALUE) int maxDuration,
                               final @Nullable String affectedGroupId) {
        super(effectType, maxPower, maxDuration, affectedGroupId);
        remainingPower = maxPower;
    }

    public @Range(from = 0, to = Integer.MAX_VALUE) int getRemainingPower() {
        return remainingPower;
    }

    public void setRemainingPower(final @Range(from = 0, to = Integer.MAX_VALUE) int remainingPower) {
        if (remainingPower < 0) {
            throw new IllegalArgumentException("Remaining power cannot be negative!");
        }
        this.remainingPower = remainingPower;
    }

    public void decreaseRemainingPower() {
        if (remainingPower > 0) {
            remainingPower--;
        }
    }
}
