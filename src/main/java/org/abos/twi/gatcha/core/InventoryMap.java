package org.abos.twi.gatcha.core;

import org.jetbrains.annotations.NotNull;

import java.util.EnumMap;
import java.util.stream.Collectors;

public class InventoryMap extends EnumMap<InventoryKind, Integer> {

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

    public void addAll(final @NotNull InventoryMap reward) {
        reward.forEach(this::add);
    }

    @Override
    public String toString() {
        return entrySet().stream()
                .map(entry -> String.format("%d %s", entry.getValue(), entry.getKey().getName(entry.getValue() == 1)))
                .collect(Collectors.joining(", "));
    }
}
