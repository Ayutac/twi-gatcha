package org.abos.twi.gatcha.core;

import org.abos.common.Named;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * The different rarities a {@link CharacterBase} can have.
 */
public enum Rarity implements Named {

    COMMON("Bronze Rank"),
    RARE("Silver Rank"),
    SUPER_RARE("Gold Rank"),
    ULTRA_RARE("Named Rank");

    private final @NotNull String name;

    Rarity(final @NotNull String name) {
        this.name = Objects.requireNonNull(name);
    }

    @Override
    public @NotNull String getName() {
        return name;
    }
}
