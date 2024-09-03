package org.abos.twi.gatcha.core;

import org.abos.common.Describable;
import org.abos.common.Named;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public record Booster(String name, String description, List<CharacterBase> characters) implements Named, Describable {

    public Booster(final @NotNull String name, final @NotNull String description, final @NotNull List<CharacterBase> characters) {
        this.name = Objects.requireNonNull(name);
        this.description = Objects.requireNonNull(description);
        if (characters.contains(null)) {
            throw new NullPointerException("Character list cannot contain null!");
        }
        this.characters = List.copyOf(characters);
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
