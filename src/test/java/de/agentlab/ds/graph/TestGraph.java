package de.agentlab.ds.graph;

import org.testng.Assert;
import org.testng.annotations.Test;

@org.testng.annotations.Test
public class TestGraph {

    @Test
    public void testAddExplicitAddNodes() {
        Graph g = new Graph("g");

        Node n_1 = new Node("1");
        Node n_2 = new Node("2");
        g.add(n_1);
        g.add(n_2);

        Edge e = new Edge(n_1, n_2);
        g.add(e);

        Assert.assertEquals(g.getEdges().size(), 1);
        Assert.assertEquals(g.getNodes().size(), 2);
    }

    @Test
    public void testAddImplicitAddNodes() {
        Graph g = new Graph("g");

        Node n_1 = new Node("1");
        Node n_2 = new Node("2");

        Edge e = new Edge(n_1, n_2);
        g.add(e);

        Assert.assertEquals(g.getEdges().size(), 1);
        Assert.assertEquals(g.getNodes().size(), 2);
    }

    @Test
    public void testContains() {
        Graph g = new Graph("g");

        Node n_1 = new Node("1");
        Node n_2 = new Node("2");

        Edge e = new Edge(n_1, n_2);
        g.add(e);

        Assert.assertEquals(g.getEdges().size(), 1);
        Assert.assertEquals(g.getNodes().size(), 2);

        Edge e2 = new Edge(n_1, n_2);

        Assert.assertTrue(g.contains(e2));
    }
}
