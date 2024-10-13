package org.abos.twi.gatcha.core;

import org.abos.common.CollectionUtil;
import org.abos.common.Describable;
import org.abos.common.Registerable;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;
import java.util.Random;

public record Booster(String name, String description, List<CharacterBase> characters, InventoryMap price, Trigger availability) implements Describable, Registerable<Booster> {

    private static Random RANDOM = new Random();

    public Booster(final @NotNull String name, final @NotNull String description, final @NotNull List<CharacterBase> characters,
                   final @NotNull InventoryMap price, final @NotNull Trigger availability) {
        this.name = Objects.requireNonNull(name);
        this.description = Objects.requireNonNull(description);
        this.characters = List.copyOf(characters);
        this.price = Objects.requireNonNull(price);
        this.availability = Objects.requireNonNull(availability);
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

    public CharacterBase pull() {
        final double prob = RANDOM.nextDouble();
        Rarity pullRarity;
        if (prob < 0.05) { // 5%
            pullRarity = Rarity.ULTRA_RARE;
        }
        else if (prob < 0.2) { // 15%
            pullRarity = Rarity.SUPER_RARE;
        }
        else if (prob < 0.55) { // 35%
            pullRarity = Rarity.RARE;
        }
        else { // 45%
            pullRarity = Rarity.COMMON;
        }
        return CollectionUtil.getRandomEntry(characters.stream()
            .filter(base -> base.rarity() == pullRarity)
            .toList(), RANDOM);
    }
}
