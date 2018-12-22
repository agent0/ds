package de.agentlab.ds.output;

public class Shape {
    private String type;

    public Shape(String type) {
        super();
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String toGraphML() {
        String result = "";
        result += "<y:Shape type=\"" + this.type + "\"/>\n";
        return result;
    }

}
