package org.abos.twi.gatcha.core.battle.graph;

import org.abos.common.Vec2i;
import org.jetbrains.annotations.Range;

import java.util.function.Supplier;

/**
 * A {@link Vec2i} supplier that delivers its elements in a grid form.
 *
 * @see #get()
 */
public class GridSupplier implements Supplier<Vec2i> {

    protected final @Range(from = 1, to = Integer.MAX_VALUE) int rows;
    protected final @Range(from = 1, to = Integer.MAX_VALUE) int cols;
    protected @Range(from = 1, to = Integer.MAX_VALUE) int x = 0;
    protected @Range(from = 1, to = Integer.MAX_VALUE) int y = 0;

    /**
     * Constructs a new {@link GridSupplier} in the specified size.
     * @param rows the number of rows, at least 1
     * @param cols the number of columns, at least 1
     * @throws IllegalArgumentException If the size constraints are violated.
     */
    public GridSupplier(final @Range(from = 1, to = Integer.MAX_VALUE) int rows,
                        final @Range(from = 1, to = Integer.MAX_VALUE) int cols) {
        if (rows < 1 || cols < 1) {
            throw new IllegalArgumentException("There must be at least one row and one column!");
        }
        this.rows = rows;
        this.cols = cols;
    }

    /**
     * Returns points in the plane starting with (0,0), (0,1), …, (0,cols-1), (1,0), (1,1), … in this order until
     * the number of columns and rows are depleted.
     * @return a {@link Vec2i} with {@code 0 <= x < cols} and {@code 0 <= y < rows}
     * @throws IllegalStateException If all grid elements have been supplied and this method is called again.
     */
    @Override
    public Vec2i get() {
        if (y == rows) {
            throw new IllegalStateException("This supplier is depleted!");
        }
        final Vec2i result = new Vec2i(x++, y);
        if (x == cols) {
            x = 0;
            y++;
        }
        return result;
    }
}
