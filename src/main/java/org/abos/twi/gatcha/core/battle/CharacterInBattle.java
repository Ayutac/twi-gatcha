package org.abos.twi.gatcha.core.battle;

import org.abos.common.Describable;
import org.abos.common.Named;
import org.abos.common.Vec2d;
import org.abos.twi.gatcha.core.CharacterModified;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.util.Objects;

public abstract class CharacterInBattle implements Named, Describable {

    protected final @NotNull CharacterModified modified;
    protected final @NotNull Field field;
    protected final @NotNull Vec2d position;

    protected int health;
    protected int moved;

    public CharacterInBattle(final @NotNull CharacterModified modified, final @NotNull Field field, final Vec2d position) {
        this.modified = Objects.requireNonNull(modified);
        this.field = Objects.requireNonNull(field);
        this.position = Objects.requireNonNull(position);
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

    public @NotNull CharacterModified getModified() {
        return modified;
    }

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return modified.getMaxHealth();
    }

    public int getSpeed() {
        return modified.getSpeed();
    }

    public int getAttack() {
        return modified.getAttack();
    }

    public int getDefense() {
        return modified.getDefense();
    }

    public @NotNull Vec2d getPosition() {
        return position;
    }

    public boolean isAt(final Vec2d position) {
        return position.equals(this.position);
    }

    public void takeDamage(final @Range(from = 0, to = Integer.MAX_VALUE) int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount of damage must be positive!");
        }
        health = health <= amount ? 0 : health - amount;
        if (health == 0) {
            field.removeCharacter(this);
        }
    }

    public void heal(final @Range(from = 0, to = Integer.MAX_VALUE) int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount of damage must be positive!");
        }
        health = getMaxHealth() - health <= amount ? getMaxHealth() : health + amount;
    }

    public void startTurn() {
        moved = 0;
    }

    public abstract void turn();

    public void endTurn() {
        // intentionally left empty
    }
}
