package org.abos.twi.gatcha.core.battle.graph;

import org.jetbrains.annotations.Range;
import org.jgrapht.Graph;
import org.jgrapht.GraphTests;
import org.jgrapht.generate.GraphGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Generates a graph in form of a hexagonal grid.
 * @param <V> The vertex kind for this generator. If {@link org.abos.common.Vec2i} is used, maybe use the {@link GridSupplier} as well.
 * @param <E> The edge kind for this generator.
 */
public class HexaGridGraphGenerator<V, E> implements GraphGenerator<V, E, V> {

    protected final @Range(from = 1, to = Integer.MAX_VALUE) int rows;
    protected final @Range(from = 1, to = Integer.MAX_VALUE) int cols;

    /**
     * Constructs a new {@link HexaGridGraphGenerator} in the specified size.
     * @param rows the number of rows, at least 1
     * @param cols the number of columns, at least 1
     * @throws IllegalArgumentException If the size constraints are violated.
     */
    public HexaGridGraphGenerator(final @Range(from = 1, to = Integer.MAX_VALUE) int rows,
                                  final @Range(from = 1, to = Integer.MAX_VALUE) int cols) {
        if (rows < 1 || cols < 1) {
            throw new IllegalArgumentException("There must be at least one row and one column!");
        }
        this.rows = rows;
        this.cols = cols;
    }

    /**
     * Generates the graph.
     * @param target an existing graph with a vertex supplier
     * @param resultMap irrelevant
     * @throws ArithmeticException If the size of the graph is too big.
     */
    @Override
    public void generateGraph(Graph<V, E> target, Map<String, V> resultMap) {
        GraphTests.requireDirectedOrUndirected(target);
        boolean isDirected = target.getType().isDirected();

        List<V> vertices = new ArrayList<>(Math.multiplyExact(rows, cols));
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                final V vertex = target.addVertex();
                vertices.add(vertex);
                if (x > 0) {
                    // connect to tile left of this one
                    final V left = vertices.get(y * cols + x - 1);
                    target.addEdge(left, vertex);
                    if (isDirected) {
                        target.addEdge(vertex, left);
                    }
                }
                if (y > 0) {
                    // connect one of the upper tiles
                    final V upper = vertices.get((y - 1) * cols + x);
                    target.addEdge(upper, vertex);
                    if (isDirected) {
                        target.addEdge(vertex, upper);
                    }
                    // connect the other upper tile, which can either be a step to the left or to the right
                    if (y % 2 == 1 && x < cols - 1) {
                        final V upper2 = vertices.get((y - 1) * cols + x + 1);
                        target.addEdge(upper2, vertex);
                        if (isDirected) {
                            target.addEdge(vertex, upper2);
                        }
                    }
                    else if (y % 2 == 0 && x > 0) {
                        final V upper2 = vertices.get((y - 1) * cols + x - 1);
                        target.addEdge(upper2, vertex);
                        if (isDirected) {
                            target.addEdge(vertex, upper2);
                        }
                    }
                }
            }
        }
    }
}
