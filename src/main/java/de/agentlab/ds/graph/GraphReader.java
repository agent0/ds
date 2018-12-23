package de.agentlab.ds.graph;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;


public class GraphReader {

    public void buildGraph(InputStream is, OutputStream os) {
        Graph g = buildGraph(is);
        PrintStream p = new PrintStream(os);
        p.println(g.toGraphML());
        p.close();
    }

    public Graph buildGraph(InputStream is) {
        try {
            Graph g = new Graph("graph");
            BufferedReader r = new BufferedReader(new InputStreamReader(is));
            String line = r.readLine();
            while (line != null) {
                String[] content = line.split(" ");
                if (content[0].equals("graph")) {
                    g.setName(content[1]);
                } else if (content[0].equals("node")) {
                    Node node = new Node(content[1]);
                    g.add(node);
                } else if (content[0].equals("edge")) {
                    Node from = new Node(content[1]);
                    Node to = new Node(content[2]);
                    Edge edge = new Edge(from, to);
                    g.add(edge);
                }
                line = r.readLine();
            }

            return g;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        try {
            new GraphReader().buildGraph(new FileInputStream(new File(args[0])), new FileOutputStream(new File(args[1])));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
