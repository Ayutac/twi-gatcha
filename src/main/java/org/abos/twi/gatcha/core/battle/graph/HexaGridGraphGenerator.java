package org.abos.twi.gatcha.core.battle.graph;

import org.jetbrains.annotations.Range;
import org.jgrapht.Graph;
import org.jgrapht.GraphTests;
import org.jgrapht.generate.GraphGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HexaGridGraphGenerator<V, E> implements GraphGenerator<V, E, V> {

    protected final @Range(from = 1, to = Integer.MAX_VALUE) int rows;
    protected final @Range(from = 1, to = Integer.MAX_VALUE) int cols;

    public HexaGridGraphGenerator(final @Range(from = 1, to = Integer.MAX_VALUE) int rows,
                                  final @Range(from = 1, to = Integer.MAX_VALUE) int cols) {
        if (rows < 1 || cols < 1) {
            throw new IllegalArgumentException("There must be at least one row and one column!");
        }
        this.rows = rows;
        this.cols = cols;
    }


    @Override
    public void generateGraph(Graph<V, E> target, Map<String, V> resultMap) {
        GraphTests.requireDirectedOrUndirected(target);
        boolean isDirected = target.getType().isDirected();

        List<V> vertices = new ArrayList<>(rows * cols);
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                final V vertex = target.addVertex();
                vertices.add(vertex);
                if (x > 0) {
                    final V left = vertices.get(y * cols + x - 1);
                    target.addEdge(left, vertex);
                    if (isDirected) {
                        target.addEdge(vertex, left);
                    }
                }
                if (y > 0) {
                    final V upper = vertices.get((y - 1) * cols + x);
                    target.addEdge(upper, vertex);
                    if (isDirected) {
                        target.addEdge(vertex, upper);
                    }
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
