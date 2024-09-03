package org.abos.twi.gatcha.core;

import org.abos.common.Describable;
import org.abos.common.Named;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Character implements Named, Describable {

    protected final @NotNull CharacterBase base;

    public Character(final @NotNull CharacterBase base) {
        this.base = Objects.requireNonNull(base);
    }

    @Override
    public @NotNull String getName() {
        return base.getName();
    }

    @Override
    public @NotNull String getDescription() {
        return base.getDescription();
    }
}
