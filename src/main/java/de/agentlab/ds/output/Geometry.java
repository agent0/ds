package de.agentlab.ds.output;

public class Geometry {
    private int width = -1;
    private int height = -1;
    private int x = -1;
    private int y = -1;

    public Geometry(int height, int width) {
        super();
        this.height = height;
        this.width = width;
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String toGraphML() {
        String result = "";
        result += "<y:Geometry";
        if (this.height != -1) {
            result += " height=\"" + this.height + "\"";
        }
        if (this.width != -1) {
            result += " width=\"" + this.width + "\"";
        }
        if (this.x != -1) {
            result += " x=\"" + this.x + "\"";
        }
        if (this.y != -1) {
            result += " y=\"" + this.y + "\"";
        }
        result += "/>\n";
        return result;
    }

}
