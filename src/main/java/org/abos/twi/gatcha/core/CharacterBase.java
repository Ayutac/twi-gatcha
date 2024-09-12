package org.abos.twi.gatcha.core;

import org.abos.common.Describable;
import org.abos.common.Named;
import org.abos.twi.gatcha.core.effect.Effect;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

/**
 * The base of a character, not including modifications made by the {@link Player}.
 * <p>
 * Character bases are immutable during a running game and are only changed during updates.
 */
public record CharacterBase(String name, String description, String pronoun, String genitive, CharacterClass cclass, CharacterStats stats, CharacterAttacks attacks, List<Effect> effects, Rarity rarity, String imageName) implements Named, Describable {

    public CharacterBase(final @NotNull String name, final @NotNull String description, final @NotNull String pronoun, final @NotNull String genitive,
                         final @NotNull CharacterClass cclass,
                         final @NotNull CharacterStats stats, final @NotNull CharacterAttacks attacks, final @NotNull List<Effect> effects,
                         final @NotNull Rarity rarity, final @NotNull String imageName) {
        this.name = Objects.requireNonNull(name);
        this.description = Objects.requireNonNull(description);
        this.pronoun = Objects.requireNonNull(pronoun);
        this.genitive = Objects.requireNonNull(genitive);
        this.cclass = Objects.requireNonNull(cclass);
        this.stats = Objects.requireNonNull(stats);
        this.attacks = Objects.requireNonNull(attacks);
        this.effects = Objects.requireNonNull(effects);
        this.rarity = Objects.requireNonNull(rarity);
        this.imageName = Objects.requireNonNull(imageName);
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
