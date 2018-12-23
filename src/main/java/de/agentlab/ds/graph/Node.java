package de.agentlab.ds.graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class Node {

    private String id;
    private String label;
    private String groupId;

    private boolean visible = true;
    private String color;
    private String textColor;
    private String type = "ShapeNode"; // "UMLClassNode";
    private Shape shape;
    private Geometry geometry;

    private Map<String, String> tags = new HashMap<String, String>();

    public Node(String id) {
        super();
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        if (this.label != null) {
            return this.label;
        } else {
            return this.id;
        }
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getGroupId() {
        return this.groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
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

    public String getTextColor() {
        return this.textColor;
    }

    public void setTextColor(String textColor) {
        if (color.startsWith("#")) {
            this.textColor = textColor.substring(1);
        } else {
            this.textColor = textColor;
        }
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Shape getShape() {
        return this.shape;
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }

    public Geometry getGeometry() {
        return this.geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public boolean isVisible() {
        return this.visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void addTag(String key, String value) {
        this.tags.put(key, value);
    }

    public String getTagValue(String key) {
        return this.tags.get(key);
    }

    public void removeTag(String key) {
        this.tags.remove(key);
    }

    public void clearTags() {
        this.tags.clear();
    }

    public int getTagCount() {
        return this.tags.size();
    }

    public List<String> getTags() {
        ArrayList<String> l = new ArrayList<String>(this.tags.keySet());
        Collections.sort(l);
        return l;
    }

    public String toString() {
        return "Node: " + this.id;
    }

    public String toDot(String graphname) {
        return "";
    }

    public String toGraphML() {
        String result = "";

        if (this.isVisible()) {
            result += "<node id=\"" + this.getId() + "\">\n";

            if (this.getTextColor() != null) {
                result += GraphmlUtils.createNode(this.getLabel(), this.getType(), this.getColor(),
                        this.getShape(), this.getGeometry(),
                        new String[]{"textColor=\"#" + this.getTextColor() + "\""});

            } else {
                result += GraphmlUtils.createNode(this.getLabel(), this.getType(), this.getColor(), this.getShape(),
                        this.getGeometry());
            }
            result += "</node>\n";
        }
        return result;
    }

    public String toGexf() {
        String result = "";

        if (this.isVisible()) {

            result += GexfUtils.createNode(this);
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return Objects.equals(id, node.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
