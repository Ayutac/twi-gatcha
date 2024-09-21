package org.abos.twi.gatcha.core.battle.graph;

import org.jetbrains.annotations.NotNull;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.alg.shortestpath.BellmanFordShortestPath;
import org.jgrapht.graph.AbstractBaseGraph;

import java.util.List;
import java.util.Optional;

/**
 * Static graph methods.
 */
public final class GraphUtil {

    private GraphUtil() {
        /* No instantiation. */
    }

    /**
     * Checks if a directed path exists from one vertex to another.
     * @param graph the graph to check, not {@code null}
     * @param from the source vertex, not {@code null}
     * @param to the sink vertex, not {@code null}
     * @return {@code true} if there exists a directed path from one vertex to the other, else {@code false}
     * @param <V> the type of vertices
     * @param <E> the type of edges
     * @throws IllegalArgumentException If the graph is not directed.
     */
    public static <V, E> boolean connectsWeakly(final @NotNull AbstractBaseGraph<V, E> graph, final @NotNull V from, final @NotNull V to) {
        if (!graph.getType().isDirected()) {
            throw new IllegalArgumentException("Only directed graphs can be tested for weakly connection!");
        }
        return new ConnectivityInspector<>(graph).connectedSetOf(from).contains(to);
    }

    /**
     * Finds the shortest path in a directed weighted graph from one vertex to the other, if it exists.
     * @param graph the graph to check, not {@code null}
     * @param from the source vertex, not {@code null}
     * @param to the sink vertex, not {@code null}
     * @return the shortest weighted directed path or an empty {@link Optional} if it doesn't exist
     * @param <V> the type of vertices
     * @param <E> the type of edges
     * @throws IllegalArgumentException If the graph is not directed or not weighted.
     */
    public static <V, E> Optional<GraphPath<V, E>> findShortestDWPath(final @NotNull AbstractBaseGraph<V, E> graph, final @NotNull V from, final @NotNull V to) {
        if (!graph.getType().isWeighted()) {
            throw new IllegalArgumentException("Only directed weighted graphs can be used in this method!");
        }
        if (!connectsWeakly(graph, from, to)) {
            return Optional.empty();
        }
        /* Bellman-Ford Shortest Path doesn't terminate if the source and sink are not weakly connected */
        return Optional.of(BellmanFordShortestPath.findPathBetween(graph, from, to));
    }

    /**
     * Calculates a vertex that is closest to the specified sink vertex on a shortest path within the maximum allowed distance.
     * <p>
     * Note that this method does not necessarily return the closest vertex to the sink within the maximum allowed distance because
     * that vertex could lie on a path that is not a shortest path. E.g. if we have two paths, P1 being source-v1-v2-sink and
     * P2 being source-v3-v4-v5-sink with edge weights 1,3,1 for P1, 1,1,1,3 for P2 and maximum distance 3, then this method would
     * return v1, while v5 is also within maximum distance but less removed from the sink.
     * @param graph the graph to check, not {@code null}
     * @param from the source vertex, not {@code null}
     * @param to the sink vertex, not {@code null}
     * @param maxDistance maximal distance between the source and the result
     * @return the vertex closest to the sink on a shortest path if one exists, else an empty {@link Optional}
     * @param <V> the type of vertices
     * @param <E> the type of edges
     * @throws IllegalArgumentException If the graph is not directed or not weighted.
     */
    public static <V, E> Optional<WeightedVertex<V>> getNextClosest(final @NotNull AbstractBaseGraph<V, E> graph, final @NotNull V from, final @NotNull V to, double maxDistance) {
        final Optional<GraphPath<V, E>> graphPath = GraphUtil.findShortestDWPath(graph, from, to);
        if (graphPath.map(GraphPath::getWeight).orElse(Double.POSITIVE_INFINITY) != Double.POSITIVE_INFINITY) {
            final List<E> pathEdges = graphPath.get().getEdgeList();
            double pathWeight = 0d;
            int count = 0;
            // we go as far as possible
            for (final E e : pathEdges) {
                if (pathWeight + graph.getEdgeWeight(e) <= maxDistance) {
                    pathWeight += graph.getEdgeWeight(e);
                    count++;
                } else {
                    break;
                }
            }
            // even one step could be too much
            if (count != 0) {
                return Optional.of(new WeightedVertex<>(graph.getEdgeTarget(pathEdges.get(count - 1)), pathWeight));
            }
        }
        return Optional.empty();
    }

}
