package org.abos.twi.gatcha.core.battle;

import org.abos.common.Describable;
import org.abos.common.Named;
import org.abos.common.Vec2i;
import org.abos.twi.gatcha.core.CharacterModified;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.util.Objects;

public class CharacterInBattle implements Named, Describable {

    protected final @NotNull CharacterModified modified;
    protected final @NotNull Battle battle;

    protected @NotNull TeamKind team;
    protected @NotNull Vec2i position;
    protected @Range(from = 0, to = Integer.MAX_VALUE) int health;
    protected @Range(from = 0, to = Integer.MAX_VALUE) int moved;

    public CharacterInBattle(final @NotNull CharacterModified modified, final @NotNull Battle battle, final @NotNull TeamKind team, final @NotNull Vec2i position) {
        this.modified = Objects.requireNonNull(modified);
        this.battle = Objects.requireNonNull(battle);
        this.team = Objects.requireNonNull(team);
        this.position = Objects.requireNonNull(position);
        this.health = getMaxHealth();
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

    public @NotNull TeamKind getTeam() {
        return team;
    }

    public @Range(from = 0, to = Integer.MAX_VALUE) int getHealth() {
        return health;
    }

    public @Range(from = 0, to = Integer.MAX_VALUE) int getMaxHealth() {
        return modified.getMaxHealth();
    }

    public @Range(from = 0, to = Integer.MAX_VALUE) int getSpeed() {
        return modified.getSpeed();
    }

    public @Range(from = 0, to = Integer.MAX_VALUE) int getAttack() {
        return modified.getAttack();
    }

    public @Range(from = 0, to = Integer.MAX_VALUE) int getDefense() {
        return modified.getDefense();
    }

    public double getInitiative() {
        return modified.getInitiative();
    }

    public @Range(from = 0, to = Integer.MAX_VALUE) int getMovement() {
        return modified.getMovement();
    }

    public @Range(from = 0, to = Integer.MAX_VALUE) int getMoved() {
        return moved;
    }

    public void setMoved(final @Range(from = 0, to = Integer.MAX_VALUE) int moved) {
        if (moved < 0) {
            throw new IllegalArgumentException("Moved cannot be negative!");
        }
        this.moved = moved;
    }

    public @NotNull Vec2i getPosition() {
        return position;
    }

    public void setPosition(final @NotNull Vec2i position) {
        this.position = Objects.requireNonNull(position);
    }

    public boolean isAt(final Vec2i position) {
        return position.equals(this.position);
    }

    public void takeDamage(final @Range(from = 0, to = Integer.MAX_VALUE) int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount of damage must be positive!");
        }
        health = health <= amount ? 0 : health - amount;
        if (health == 0) {
            battle.removeCharacter(this);
        }
    }

    public void heal(final @Range(from = 0, to = Integer.MAX_VALUE) int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount of damage must be positive!");
        }
        health = getMaxHealth() - health <= amount ? getMaxHealth() : health + amount;
    }

    public void startTurn() {
        setMoved(0);
    }

    public void turn() {
        // intentionally left empty
    }

    public void endTurn() {
        // intentionally left empty
    }
}
