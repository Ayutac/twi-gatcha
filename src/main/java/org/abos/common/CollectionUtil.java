package org.abos.common;

import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

/**
 * Additional utilities where {@link java.util.Collections} isn't enough.
 */
public final class CollectionUtil {

    private CollectionUtil() {
        /* No instantiation. */
    }

    /**
     * Returns a random entry of the given collection.
     *
     * @param collection the collection to get an entry from
     * @param random     a {@link Random} instance
     * @param <T>        the type of elements in the collection
     * @return a random entry of the collection, might be {@code null} if the collection contains {@code null}
     * @throws NullPointerException     If {@code collection} or {@code random} refers to {@code null}.
     * @throws IllegalArgumentException If the collection is empty.
     */
    public static <T> T getRandomEntry(final Collection<T> collection, final Random random) {
        if (collection.isEmpty()) {
            throw new IllegalArgumentException("Collection cannot be empty!");
        }
        int selection = random.nextInt(collection.size());
        final Iterator<T> it = collection.iterator();
        while (selection > 0) {
            it.next();
            selection--;
        }
        return it.next();
    }
}