package org.abos.twi.gatcha.core;

import org.abos.common.Describable;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public enum InventoryKind implements Describable {

    GOLD("Gold", "It's gold."),
    MAGICORE("Magicore", "Stones filled with and made up from magic"),
    FAERIE_FLOWERS("Faerie Flowers", "Faerie Flower", "Flowers from another land.");

    private final String name;
    private final String nameOne;
    private final String description;

    InventoryKind(final @NotNull String name, final @NotNull String nameOne, final @NotNull String description) {
        this.name = Objects.requireNonNull(name);
        this.nameOne = Objects.requireNonNull(nameOne);
        this.description = Objects.requireNonNull(description);
    }

    InventoryKind(final @NotNull String name, final @NotNull String description) {
        this(name, name, description);
    }

    @Override
    public @NotNull String getName() {
        return name;
    }

    public @NotNull String getName(final boolean one) {
        return one ? nameOne : name;
    }

    @Override
    public @NotNull String getDescription() {
        return description;
    }
}
