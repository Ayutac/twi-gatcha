package org.abos.twi.gatcha.core.battle;

import org.abos.common.Describable;
import org.abos.common.Named;
import org.abos.twi.gatcha.core.battle.effect.Effect;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.util.List;
import java.util.Objects;

/**
 * Representation of an attack a {@link CharacterInBattle} can do. The attacks are already saved with {@link org.abos.twi.gatcha.core.CharacterBase}.
 */
public record Attack(String name, String description, int rangeMin, int rangeMax, int cooldown, List<Effect> effects) implements Named, Describable {

    /**
     * Constructs a new {@link Attack} instance,
     * @param name the name of the attack, not {@code null}
     * @param description the name of the attack, not {@code null}
     * @param rangeMin the minimum range of the attack, not negative
     * @param rangeMax the maximum range of the attack, not negative
     * @param cooldown how many turns are needed to use the attack again, positive
     * @param effects a non-{@code null} list of effects this attack has, not {@code null} and shouldn't contain {@code null}
     * @throws NullPointerException If {@code name}, {@code description} or {@code effects} is {@code null}.
     * @throws IllegalArgumentException If any int parameter is out of range or if {@code rangeMin > rangeMax}
     */
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
