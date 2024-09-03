package org.abos.twi.gatcha.core;

import org.abos.common.Describable;
import org.abos.common.Named;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public abstract class CharacterInBattle implements Named, Describable {

    protected final @NotNull CharacterModified modified;

    protected int health;
    protected int moved;

    public CharacterInBattle(final @NotNull CharacterModified modified) {
        this.modified = Objects.requireNonNull(modified);
        this.health = modified.getMaxHealth();
    }

    @Override
    public @NotNull String getName() {
        return modified.getName();
    }

    @Override
    public @NotNull String getDescription() {
        return modified.getDescription();
    }

    public void startTurn() {
        moved = 0;
    }

    public abstract void turn();

    public void endTurn() {
        // intentionally left empty
    }
}
