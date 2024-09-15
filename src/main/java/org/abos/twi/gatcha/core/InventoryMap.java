package org.abos.twi.gatcha.core;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.EnumMap;
import java.util.Map;
import java.util.stream.Collectors;

public class InventoryMap extends EnumMap<InventoryKind, Integer> {

    protected PlayerStats associatedStats;

    public InventoryMap() {
        super(InventoryKind.class);
    }

    public InventoryMap(InventoryKind[] kinds, int[] amounts) {
        this();
        if (kinds.length != amounts.length) {
            throw new IllegalArgumentException("Kinds and amounts must have the same length!");
        }
        for (int i = 0; i < kinds.length; i++) {
            add(kinds[i], amounts[i]);
        }
    }

    public InventoryMap(InventoryKind kind, int amount) {
        this(new InventoryKind[]{kind}, new int[]{amount});
    }

    public InventoryMap(InventoryKind kind1, int amount1, InventoryKind kind2, int amount2) {
        this(new InventoryKind[]{kind1, kind2}, new int[]{amount1, amount2});
    }

    public InventoryMap(InventoryKind kind1, int amount1, InventoryKind kind2, int amount2, InventoryKind kind3, int amount3) {
        this(new InventoryKind[]{kind1, kind2, kind3}, new int[]{amount1, amount2, amount3});
    }

    @Override
    public Integer put(final InventoryKind key, final Integer value) {
        if (value < 0) {
            throw new IllegalArgumentException("Value cannot be negative!");
        }
        if (value == 0) {
            return remove(key);
        }
        return super.put(key, value);
    }

    @Override
    public Integer get(Object key) {
        final Integer result = super.get(key);
        if (result == null) {
            return 0;
        }
        return result;
    }

    public Integer add(final InventoryKind key, final Integer value) {
        if (value == 0) {
            return get(key);
        }
        if (value > 0 && associatedStats != null) {
            associatedStats.increaseItemGot(key, value);
        }
        if (!containsKey(key)) {
            return put(key, value);
        }
        int sum = get(key);
        try {
            sum = Math.addExact(sum, value);
        }
        catch (final ArithmeticException ex) {
            sum = Integer.MAX_VALUE;
        }
        put(key, sum); // throws IAE
        return sum;
    }

    public Integer subtract(final InventoryKind key, final Integer value) {
        return add(key, -value);
    }

    public void addAll(final @NotNull InventoryMap map) {
        map.forEach(this::add);
    }

    public boolean canSubtract(final @NotNull InventoryMap map) {
        for (final InventoryKind kind : InventoryKind.values()) {
            if (get(kind) - map.get(kind) < 0) {
                return false;
            }
        }
        return true;
    }

    public void subtractAll(final @NotNull InventoryMap map) {
        map.forEach(this::subtract);
    }

    public PlayerStats getAssociatedStats() {
        return associatedStats;
    }

    public void setAssociatedStats(PlayerStats associatedStats) {
        this.associatedStats = associatedStats;
    }

    @Override
    public String toString() {
        return entrySet().stream()
                .map(entry -> String.format("%d %s", entry.getValue(), entry.getKey().getName(entry.getValue() == 1)))
                .collect(Collectors.joining(", "));
    }

    public void save(final @NotNull ObjectOutputStream oos) throws IOException {
        oos.writeInt(size());
        for (Map.Entry<InventoryKind, Integer> entry : entrySet()) {
            oos.writeInt(entry.getKey().ordinal());
            oos.writeInt(entry.getValue());
        }
    }

    public static InventoryMap load(final @NotNull ObjectInputStream ois) throws IOException {
        final int size = ois.readInt();
        final InventoryKind[] keys = InventoryKind.values();
        final InventoryMap result = new InventoryMap();
        for (int i = 0; i < size; i++) {
            result.put(keys[ois.readInt()], ois.readInt());
        }
        return result;
    }
}
