package org.abos.twi.gatcha.core;

import org.abos.common.Describable;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public enum InventoryKind implements Describable {

    GOLD("Gold", "It's gold."),
    MAGICORE("Magicore", "Stones filled with and made up from magic"),
    FAERIE_FLOWERS("Faerie Flowers", "Faerie Flower", "Flowers from another land."),

    TOKEN_ERIN("Erin Solstice Token", "Token for Erin Solstice."),
    TOKEN_PISCES("Pisces Token", "Token for Pisces."),
    TOKEN_PISCES_CRELER("Pisces Jealnet, [Deathbane Necromancer] Token", "Token for Pisces Jealnet, [Deathbane Necromancer]."),
    TOKEN_CERIA("Ceria Springwalker Token", "Token for Ceria Springwalker."),
    TOKEN_CERIA_CRELER("Ceria Springwalker, [Arctic Cyromancer] Token", "Token for Ceria Springwalker, [Arctic Cyromancer]."),
    TOKEN_KSMVR("Ksmvr Token", "Token for Ksmvr."),
    TOKEN_KSMVR_CRELER("Ksmvr, [Skirmisher] Token", "Token for Ksmvr, [Skirmisher]."),
    TOKEN_YVLON("Yvlon Byres Token", "Token for Yvlon Byres."),
    TOKEN_YVLON_CRELER("Yvlon Byres, [Silversteel Armsmistress] Token", "Token for Yvlon Byres, [Silversteel Armsmistress]."),
    TOKEN_RYOKA("Ryoka Griffin Token", "Token for Ryoka Griffin."),
    TOKEN_TROYDEL("Troydel Token", "Token for Troydel."),
    ;

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
