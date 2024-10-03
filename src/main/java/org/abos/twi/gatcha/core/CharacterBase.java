package org.abos.twi.gatcha.core;

import org.abos.common.Describable;
import org.abos.common.Registerable;
import org.abos.twi.gatcha.core.effect.PersistentEffect;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

/**
 * The base of a character, not including modifications made by the {@link Player}.
 * <p>
 * Character bases are immutable during a running game and are only changed during updates.
 */
public record CharacterBase(String id, String name, String description, String pronoun, String genitive,
                            CharacterStats startStats, CharacterStats endStats, CharacterAttacks attacks,
                            List<PersistentEffect> effects, Rarity rarity, InventoryKind token, String imageName) implements Describable, Registerable<CharacterBase> {

    public CharacterBase {
        Objects.requireNonNull(id);
        Objects.requireNonNull(name);
        Objects.requireNonNull(description);
        Objects.requireNonNull(pronoun);
        Objects.requireNonNull(genitive);
        Objects.requireNonNull(startStats);
        Objects.requireNonNull(endStats);
        Objects.requireNonNull(attacks);
        Objects.requireNonNull(effects);
        Objects.requireNonNull(rarity);
        // token can be null
        Objects.requireNonNull(imageName);
    }

    @Override
    public @NotNull String getId() {
        return id;
    }

    @Override
    public @NotNull String getName() {
        return name;
    }

    @Override
    public @NotNull String getDescription() {
        return description;
    }

    /**
     * Reflexive form of the pronoun.
     * @return same as {@code genitive() + "self"}
     */
    public String self() {
        return genitive + "self";
    }

}
