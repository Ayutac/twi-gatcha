package org.abos.common;

import org.jetbrains.annotations.NotNull;

/**
 * Represents a named object that also has a description.
 */
public interface Describable extends Named {

    /**
     * Returns the description of this object
     * @return the description of this object, might be empty but not {@code null}
     */
    @NotNull String getDescription();

}