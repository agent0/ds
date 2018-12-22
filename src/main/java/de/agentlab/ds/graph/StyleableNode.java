package de.agentlab.ds.graph;

public class StyleableNode {

    private String id;
    private String label;

    private String color;
    private String textColor;
    private String type = "ShapeNode"; // "UMLClassNode";
    private Shape shape;
    private Geometry geometry;

    private boolean visible = true;

    public String getId() {
        return this.id;
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        StyleableNode other = (StyleableNode) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

}
