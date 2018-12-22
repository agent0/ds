package de.agentlab.ds.graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Node extends StyleableNode {

    private String groupId;
    private Map<String, String> tags = new HashMap<String, String>();

    public Node(String id) {
        super();
        this.setId(id);
    }

    public String getGroupId() {
        return this.groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
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

    @Override
    public String toString() {
        return "Node: " + this.getId();
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
                result += GraphmlUtils.createNode(this.getType(), this.getLabel(), this.getShape(),
                        this.getColor(), this.getGeometry());
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
}
