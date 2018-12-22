package de.agentlab.ds.graph;

import de.agentlab.ds.output.GraphmlUtils;

import java.util.ArrayList;
import java.util.List;


public class Edge {
    private Node from;
    private Node to;
    private String color;
    private boolean visible = true;
    private boolean directed = true;
    private boolean addNodesAutomatically = true;

    public Edge(Node from, Node to) {
        super();
        this.from = from;
        this.to = to;
    }

    public Node getFrom() {
        return this.from;
    }

    public void setFrom(Node from) {
        this.from = from;
    }

    public Node getTo() {
        return this.to;
    }

    public void setTo(Node to) {
        this.to = to;
    }

    public String getColor() {
        return this.color;
    }

    public void setColor(String color) {
        if (color.startsWith("#")) {
            this.color = color.substring(1);
        } else {
            this.color = color;
        }
    }

    public boolean isDirected() {
        return this.directed;
    }

    public void setDirected(boolean directed) {
        this.directed = directed;
    }

    public String toString() {
        return "Edge: " + this.from.getId() + " -> " + this.to.getId();
    }

    public String toDot(String graphname) {
        String result = "";
        result += "\"" + graphname + "::" + this.from.getId() + "\"[label=\"" + this.from.getId()
                + "\"]";
        result += "\"" + graphname + "::" + this.to.getId() + "\"[label=\"" + this.to.getId() + "\"]";
        result += "\"" + graphname + "::" + this.from.getId() + "\" -> \"" + graphname + "::"
                + this.to.getId() + "\";";

        return result;
    }

    public String toGraphML() {
        return this.toGraphML(new ArrayList<Node>());
    }

    public String toGraphML(List<Node> drawn) {
        String result = "";

        if (this.isAddNodesAutomatically()) {
            if (!drawn.contains(this.from)) {
                drawn.add(this.from);
                result += this.from.toGraphML();
            }
            if (!drawn.contains(this.to)) {
                drawn.add(this.to);
                result += this.to.toGraphML();
            }
        }

        if (this.isVisible()) {
            result += "<edge id=\"" + this.getFromId() + "#" + this.getToId() + "\" source=\""
                    + this.getFromId() + "\" target=\"" + this.getToId() + "\">\n";

            if (this.color != null) {
                result += GraphmlUtils.createArrowStyle(this.color, this.directed);
            } else {
                result += GraphmlUtils.createArrowStyle("000000", this.directed);
            }
            result += "</edge>";
        }

        return result;
    }

    public String getFromId() {
        return this.from.getId();
    }

    public String getToId() {
        return this.to.getId();
    }

    public boolean isVisible() {
        return this.visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isAddNodesAutomatically() {
        return this.addNodesAutomatically;
    }

    public void setAddNodesAutomatically(boolean addNodesAutomatically) {
        this.addNodesAutomatically = addNodesAutomatically;
    }

    public String toGexf(List<Node> drawn) {
        String result = "";

        if (this.isVisible()) {
            result += "<edge id=\"" + this.getFromId() + "#" + this.getToId() + "\" source=\""
                    + this.getFromId() + "\" target=\"" + this.getToId() + "\">\n";

            result += "</edge>";
        }

        return result;
    }
}
