package org.abos.twi.gatcha.core;

import org.abos.common.Describable;
import org.abos.common.Named;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * The base of a character, not including modifications made by the {@link Player}.
 * <p>
 * Character bases are immutable during a running game and are only changed during updates.
 */
public record CharacterBase(String name, String description, Rarity rarity) implements Named, Describable {

    public CharacterBase(final String name, final String description, final Rarity rarity) {
        this.name = Objects.requireNonNull(name);
        this.description = Objects.requireNonNull(description);
        this.rarity = Objects.requireNonNull(rarity);
    }

    @Override
    public @NotNull String getName() {
        return name;
    }

    @Override
    public @NotNull String getDescription() {
        return description;
    }
}
