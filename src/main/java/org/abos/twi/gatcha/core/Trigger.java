package org.abos.twi.gatcha.core;

import org.jetbrains.annotations.Nullable;

@FunctionalInterface
public interface Trigger {

    Trigger ALWAYS = player -> true;
    Trigger NEVER = player -> false;

    boolean check(final @Nullable Player player);

}
