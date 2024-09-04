package org.abos.twi.gatcha.core.battle.graph;

import org.abos.common.Vec2i;
import org.jetbrains.annotations.Range;

import java.util.function.Supplier;

public class GridSupplier implements Supplier<Vec2i> {

    protected final @Range(from = 1, to = Integer.MAX_VALUE) int rows;
    protected final @Range(from = 1, to = Integer.MAX_VALUE) int cols;
    protected @Range(from = 1, to = Integer.MAX_VALUE) int x = 0;
    protected @Range(from = 1, to = Integer.MAX_VALUE) int y = 0;

    public GridSupplier(final @Range(from = 1, to = Integer.MAX_VALUE) int rows,
                        final @Range(from = 1, to = Integer.MAX_VALUE) int cols) {
        if (rows < 1 || cols < 1) {
            throw new IllegalArgumentException("There must be at least one row and one column!");
        }
        this.rows = rows;
        this.cols = cols;
    }

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
