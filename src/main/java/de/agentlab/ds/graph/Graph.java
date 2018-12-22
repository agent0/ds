package de.agentlab.ds.graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Graph {
    private String name;

    private List<Graph> subgraphs = new ArrayList<Graph>();
    private List<Node> nodes = new ArrayList<Node>();
    private List<String> nodeNames = new ArrayList<String>();
    private List<Edge> edges = new ArrayList<Edge>();

    private boolean visible = true;

    public Graph(String name) {
        super();
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void add(Graph g) {
        this.subgraphs.add(g);
    }

    public List<Graph> getSubgraphs() {
        return this.subgraphs;
    }

    public void add(Node n) {
        if (!nodeNames.contains(n.getId())) {
            this.nodes.add(n);
            this.nodeNames.add(n.getId());
        }
    }

    public Node findNodeById(String id) {
        if (nodeNames.contains(id)) {
            for (Node node : this.nodes) {
                if (node.getId().equals(id)) {
                    return node;
                }
            }
        }
        for (Graph subgraph : subgraphs) {
            Node tmp = subgraph.findNodeById(id);
            if (tmp != null) {
                return tmp;
            }
        }
        return null;
    }

    public List<Node> findNodesByGroupId(String groupId) {
        List<Node> result = new ArrayList<Node>();
        for (Node node : this.nodes) {
            if (node.getGroupId().equals(groupId)) {
                result.add(node);
            }
        }
        return result;
    }

    public List<String> getGroupIdList() {
        List<String> result = new ArrayList<String>();
        for (Node node : this.nodes) {
            if (node.getGroupId() != null) {
                result.add(node.getGroupId());
            }
        }
        return result;
    }

    public List<Node> getNodes() {
        return this.nodes;
    }

    public void add(Edge e) {
        this.edges.add(e);
    }

    public List<Edge> getEdges() {
        return this.edges;
    }

    public boolean contains(Edge edge) {
        for (Edge e : this.edges) {
            if (e.getFrom().equals(edge.getFrom()) && e.getTo().equals(edge.getTo())) {
                return true;
            }
        }

        return false;
    }

    public String toString() {
        String result = "";
        result += "Graph: " + this.name + "\n";
        for (Graph sg : this.subgraphs) {
            result += sg.toString() + "\n";
        }
        for (Node node : this.nodes) {
            result += node.toString() + "\n";
        }
        for (Edge edge : this.edges) {
            result += edge.toString() + "\n";
        }
        return result;
    }

    public String toDot() {
        return this.toDot(false);
    }

    public String toDot(boolean isSubgraph) {
        String result = "";
        if (!isSubgraph) {
            result += "digraph \"cluster_" + this.name + "\" {\n";
        } else {
            result += "subgraph \"cluster_" + this.name + "\" {\n";
            result += "label=\"" + this.name + "\"";
        }
        for (Graph sg : this.subgraphs) {
            result += sg.toDot(true) + "\n";
        }
        for (Node node : this.nodes) {
            result += node.toDot(this.name) + "\n";
        }
        for (Edge edge : this.edges) {
            result += edge.toDot(this.name) + "\n";
        }
        result += "}";
        return result;
    }

    public String toGraphML() {
        List<Node> drawn = new ArrayList<Node>();
        return this.toGraphML(false, drawn);
    }

    public String toGraphML(boolean isSubgraph, List<Node> drawn) {
        String result = "";

        if (this.isVisible()) {
            if (!isSubgraph) {
                result += "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
                result += "<graphml xmlns=\"http://graphml.graphdrawing.org/xmlns/graphml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:y=\"http://www.yworks.com/xml/graphml\" xsi:schemaLocation=\"http://graphml.graphdrawing.org/xmlns/graphml http://www.yworks.com/xml/schema/graphml/1.0/ygraphml.xsd\">\n";
                result += "<key for=\"node\" id=\"d0\" yfiles.type=\"nodegraphics\"/>\n";
                result += "<key for=\"edge\" id=\"d2\" yfiles.type=\"edgegraphics\"/>\n";
                result += "<graph id=\"" + this.name + "\" edgedefault=\"directed\">\n";
            } else {
                result += "<node id=\"subgraph::" + this.name + "\">\n";
                result += GraphmlUtils.createNode(this.name, "ShapeNode", null, null, null,
                        new String[]{"modelPosition=\"br\""});
                result += "<graph id=\"" + this.name + "\" edgedefault=\"directed\">\n";
            }
            for (Node node : nodes) {
                if (!drawn.contains(node)) {
                    drawn.add(node);
                    result += node.toGraphML();
                }
            }
            for (Graph sg : this.subgraphs) {
                result += sg.toGraphML(true, drawn) + "\n";
            }
            for (Edge edge : this.edges) {
                result += edge.toGraphML(drawn) + "\n";
            }
            result += "</graph>\n";
            if (isSubgraph) {
                result += "</node>\n";
            } else {
                result += "</graphml>\n";
            }
        }

        return result;
    }

    public String toGexf() {
        List<Node> drawn = new ArrayList<Node>();
        return this.toGexf(false, drawn);
    }

    public String toGexf(boolean isSubgraph, List<Node> drawn) {
        String result = "";

        if (this.isVisible()) {

            result += "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
            result += "<gexf xmlns=\"http://www.gephi.org/gexf\" xmlns:viz=\"http://www.gephi.org/gexf/viz\">\n";
            result += "<graph type=\"static\">\n";

            Set<String> tags = new HashSet<String>();
            for (Node node : this.nodes) {
                tags.addAll(node.getTags());
            }
            List<String> sorted = new ArrayList<String>(tags);
            Collections.sort(sorted);

            if (sorted.size() > 0) {
                result += "<attributes class=\"node\" mode=\"static\">\n";
                for (String tag : sorted) {
                    result += "<attribute id=\"" + tag + "\" title=\"" + tag + "\" type=\"string\"/>\n";
                }
                result += "</attributes>\n";
            }

            result += "<nodes>\n";
            for (Node node : nodes) {
                if (!drawn.contains(node)) {
                    drawn.add(node);
                    result += node.toGexf();
                }
            }
            result += "</nodes>\n";

            result += "<edges>\n";
            for (Edge edge : this.edges) {
                result += edge.toGexf(drawn) + "\n";
            }
            result += "</edges>\n";

            result += "</graph>\n";
            result += "</gexf>\n";
        }

        return result;
    }

    public boolean isVisible() {
        return this.visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

}
