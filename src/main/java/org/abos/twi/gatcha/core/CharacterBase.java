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
                            CharacterStats startStats, CharacterStats endStats, CharacterAttacks attacks, List<PersistentEffect> effects, Rarity rarity, String imageName) implements Describable, Registerable<CharacterBase> {

    public CharacterBase(final @NotNull String id, final @NotNull String name, final @NotNull String description, final @NotNull String pronoun, final @NotNull String genitive,
                         final @NotNull CharacterStats startStats, final @NotNull CharacterStats endStats,
                         final @NotNull CharacterAttacks attacks, final @NotNull List<PersistentEffect> effects,
                         final @NotNull Rarity rarity, final @NotNull String imageName) {
        this.id = Objects.requireNonNull(id);
        this.name = Objects.requireNonNull(name);
        this.description = Objects.requireNonNull(description);
        this.pronoun = Objects.requireNonNull(pronoun);
        this.genitive = Objects.requireNonNull(genitive);
        this.startStats = Objects.requireNonNull(startStats);
        this.endStats = Objects.requireNonNull(endStats);
        this.attacks = Objects.requireNonNull(attacks);
        this.effects = Objects.requireNonNull(effects);
        this.rarity = Objects.requireNonNull(rarity);
        this.imageName = Objects.requireNonNull(imageName);
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
