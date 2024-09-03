package org.abos.twi.gatcha.core;

import org.abos.common.Named;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

/**
 * Represents the player of this game.
 */
public class Player implements Named {

    protected String name;

    protected Map<InventoryKind, Integer> inventory = new EnumMap<>(InventoryKind.class);

    public Player(final @NotNull String name) {
        this.name = Objects.requireNonNull(name);
    }

    @NotNull
    @Override
    public String getName() {
        return name;
    }

    /**
     * Sets the name of this player.
     * @param name the new name, not {@code null}
     */
    public void setName(final @NotNull String name) {
        this.name = Objects.requireNonNull(name);
    }

    /**
     * Returns the count of an item in the inventory.
     * @param kind the kind of item to return
     * @return the count of an item in the inventory, not negative
     */
    @Range(from = 0, to = Integer.MAX_VALUE)
    public int getInventoryCount(final @NotNull InventoryKind kind) {
        return inventory.getOrDefault(Objects.requireNonNull(kind), 0);
    }

    /**
     * Sets the count of an item in the inventory.
     * @param kind the kind of item to change
     * @param amount the new amount of that item, not negative
     */
    public void setInventoryCount(final @NotNull InventoryKind kind, final @Range(from = 0, to = Integer.MAX_VALUE) int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount cannot be negative!");
        }
        if (amount == 0) {
            inventory.remove(Objects.requireNonNull(kind));
        }
        else {
            inventory.put(Objects.requireNonNull(kind), amount);
        }
    }

    /**
     * Increases the count of an item in the inventory. The new amount cannot be negative.
     * @param kind the kind of item to change
     * @param amount the additional amount of that item
     */
    public void increaseInventoryCount(final @NotNull InventoryKind kind, final @Range(from = 0, to = Integer.MAX_VALUE) int amount) {
        int sum = getInventoryCount(kind); // throws NPE
        try {
            sum = Math.addExact(sum, amount);
        }
        catch (final ArithmeticException ex) {
            sum = Integer.MAX_VALUE;
        }
        setInventoryCount(kind, sum); // throws IAE
    }

    /**
     * Decreases the count of an item in the inventory. The new amount cannot be negative.
     * @param kind the kind of item to change
     * @param amount the amount of that item to reduce
     */
    public void decreaseInventoryCount(final @NotNull InventoryKind kind, final @Range(from = 0, to = Integer.MAX_VALUE) int amount) {
        increaseInventoryCount(kind, -amount);
    }

}
