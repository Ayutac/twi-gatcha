package org.abos.common;

import java.util.Map;

public interface Registerable<T extends Registerable<T>> extends Id {

    default void registerWith(Map<String, T> registry) {
        if (registry.containsKey(getId())) {
            throw new IllegalStateException("ID is already registered!");
        }
        registry.put(getId(), (T) this);
    }

}
