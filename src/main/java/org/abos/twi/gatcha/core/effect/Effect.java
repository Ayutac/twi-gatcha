package org.abos.twi.gatcha.core.effect;

import org.abos.twi.gatcha.core.Group;
import org.abos.twi.gatcha.data.Lookups;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

import java.util.Objects;
import java.util.Optional;

public abstract class Effect {

    protected final @NotNull EffectType effectType;
    protected final @Range(from = 0, to = Integer.MAX_VALUE) int maxPower;
    protected final @Range(from = 0, to = Integer.MAX_VALUE) int maxDuration;
    protected final @Nullable String affectedGroupId;
    private @Nullable Optional<Group> affectedGroup;

    protected Effect(final @NotNull EffectType effectType,
                  final @Range(from = 0, to = Integer.MAX_VALUE) int maxPower,
                  final @Range(from = 0, to = Integer.MAX_VALUE) int maxDuration,
                  final @Nullable String affectedGroupId) {
        this.effectType = Objects.requireNonNull(effectType);
        if (maxPower < 0 || maxDuration < 0) {
            throw new IllegalArgumentException("Power and duration cannot be negative!");
        }
        this.maxPower = maxPower;
        this.maxDuration = maxDuration;
        this.affectedGroupId = affectedGroupId;
    }

    public @NotNull EffectType getEffectType() {
        return effectType;
    }

    public @Range(from = 0, to = Integer.MAX_VALUE) int getMaxPower() {
        return maxPower;
    }

    public @Range(from = 0, to = Integer.MAX_VALUE) int getMaxDuration() {
        return maxDuration;
    }

    public Optional<Group> getAffectedGroup() {
        if (affectedGroup == null) {
            if (affectedGroupId == null) {
                affectedGroup = Optional.empty();
            }
            else {
                affectedGroup = Optional.of(Lookups.GROUPS.get(affectedGroupId));
            }
        }
        return affectedGroup;
    }
}
