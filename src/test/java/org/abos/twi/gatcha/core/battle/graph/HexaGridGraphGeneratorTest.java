package org.abos.twi.gatcha.core.battle.graph;

import org.abos.common.Vec2i;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class HexaGridGraphGeneratorTest {

    @Test
    void testGenerator() {
        final var hexGridGraph = new SimpleDirectedWeightedGraph<Vec2i, DefaultEdge>(DefaultEdge.class);
        final HexaGridGraphGenerator<Vec2i, DefaultEdge> hexGridGraphGen = new HexaGridGraphGenerator<>(4, 5);
        hexGridGraph.setVertexSupplier(new GridSupplier(4, 5));
        hexGridGraphGen.generateGraph(hexGridGraph);
        Assertions.assertEquals(86, hexGridGraph.edgeSet().size());
        Assertions.assertTrue(hexGridGraph.containsEdge(new Vec2i(2, 2), new Vec2i(1, 1)));
        Assertions.assertTrue(hexGridGraph.containsEdge(new Vec2i(2, 2), new Vec2i(2, 1)));
        Assertions.assertTrue(hexGridGraph.containsEdge(new Vec2i(2, 2), new Vec2i(3, 2)));
        Assertions.assertTrue(hexGridGraph.containsEdge(new Vec2i(2, 2), new Vec2i(2, 3)));
        Assertions.assertTrue(hexGridGraph.containsEdge(new Vec2i(2, 2), new Vec2i(1, 3)));
        Assertions.assertTrue(hexGridGraph.containsEdge(new Vec2i(2, 2), new Vec2i(1, 2)));
    }

}