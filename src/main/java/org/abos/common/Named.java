package org.abos.common;

import org.jetbrains.annotations.NotNull;

/**
 * Represents an object that has a name.
 */
public interface Named {

    /**
     * Returns the name of this object.
     * @return the name of this object, not {@code null}
     */
    @NotNull String getName();

}