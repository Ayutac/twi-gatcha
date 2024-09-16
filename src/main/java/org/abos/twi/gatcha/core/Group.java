package org.abos.twi.gatcha.core;

import org.abos.common.Describable;
import org.abos.common.Registerable;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Set;

public record Group(String name, String description, Set<CharacterBase> characters) implements Describable, Registerable<Group> {

    public Group(final @NotNull String name, final @NotNull String description, final @NotNull Set<CharacterBase> characters) {
        this.name = Objects.requireNonNull(name);
        this.description = Objects.requireNonNull(description);
        this.characters = Set.copyOf(characters);
    }

    @Override
    public @NotNull String getId() {
        return name;
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
