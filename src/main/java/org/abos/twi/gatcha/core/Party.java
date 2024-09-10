package org.abos.twi.gatcha.core;

import org.abos.common.Named;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public record Party(String name, List<CharacterModified> characters) implements Named {

    public static final Party EMPTY = new Party("empty", List.of());

    public Party(final @NotNull String name, final @NotNull List<CharacterModified> characters) {
        this.name = Objects.requireNonNull(name);
        this.characters = List.copyOf(characters);
    }

    @Override
    public @NotNull String getName() {
        return name;
    }
}
