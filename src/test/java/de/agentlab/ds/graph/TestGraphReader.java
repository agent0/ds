package de.agentlab.ds.graph;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import org.testng.annotations.Test;

@Test
public class TestGraphReader {

    @Test
    public void testRead() {
        GraphReader graphReader = new GraphReader();

        InputStream is = TestGraphReader.class.getResourceAsStream("/test_read.txt");
        Graph g = graphReader.buildGraph(is);

        String s = g.toString();
        System.out.println(s);

        s = g.toDot();
        System.out.println(s);

        s = g.toGraphML();
        System.out.println(s);

        s = g.toGexf();
        System.out.println(s);
    }

    @Test
    public void testOutputToFile() {
        GraphReader graphReader = new GraphReader();

        InputStream is = TestGraphReader.class.getResourceAsStream("/test_read.txt");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        graphReader.buildGraph(is, baos);
    }

}
