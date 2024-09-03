package org.abos.twi.gatcha.core.battle;

import org.abos.common.Describable;
import org.abos.common.Named;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.util.List;
import java.util.Objects;

public record Attack(String name, String description, int rangeMin, int rangeMax, int cooldown, List<Effect> effects) implements Named, Describable {

    public Attack(final @NotNull String name, final @NotNull String description,
                  final @Range(from = 0, to = Integer.MAX_VALUE) int rangeMin,
                  final @Range(from = 0, to = Integer.MAX_VALUE) int rangeMax,
                  final @Range(from = 1, to = Integer.MAX_VALUE) int cooldown,
                  final @NotNull List<Effect> effects) {
        this.name = Objects.requireNonNull(name);
        this.description = Objects.requireNonNull(description);
        if (rangeMin < 0 || rangeMax < 0 || cooldown < 1) {
            throw new IllegalArgumentException("Range cannot be negative and Cooldown must be positive!");
        }
        if (rangeMin > rangeMax) {
            throw new IllegalArgumentException("Min range cannot be bigger than max range!");
        }
        this.rangeMin = rangeMin;
        this.rangeMax = rangeMax;
        this.cooldown = cooldown;
        if (effects.contains(null)) {
            throw new NullPointerException("List cannot contain null!");
        }
        this.effects = List.copyOf(effects);
    }

    @Override
    public @NotNull String getName() {
        return name();
    }

    @Override
    public @NotNull String getDescription() {
        return description();
    }
}
