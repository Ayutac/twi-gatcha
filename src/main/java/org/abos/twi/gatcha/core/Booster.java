package org.abos.twi.gatcha.core;

import org.abos.common.Describable;
import org.abos.common.Named;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public record Booster(String name, String description, List<CharacterBase> characters, InventoryMap price, Trigger availability) implements Named, Describable {

    public Booster(final @NotNull String name, final @NotNull String description, final @NotNull List<CharacterBase> characters,
                   final @NotNull InventoryMap price, final @NotNull Trigger availability) {
        this.name = Objects.requireNonNull(name);
        this.description = Objects.requireNonNull(description);
        this.characters = List.copyOf(characters);
        this.price = Objects.requireNonNull(price);
        this.availability = Objects.requireNonNull(availability);
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
