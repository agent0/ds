package de.agentlab.ds.graph;

public class GraphmlUtils {
    public static String createNode(String type, String name, String color) {
        return createNode(name, type, color, null, null, new String[]{});
    }

    public static String createNode(String name, String nodeStyle, String color, Shape shape,
                                    Geometry geometry, String[] labelAttributes) {
        String result = "";
        result += "<data key=\"d0\">\n";
        result += "<y:" + nodeStyle + ">\n";
        if (color != null) {
            result += "<y:Fill color=\"#" + color + "\" transparent=\"false\"/>\n";
        }
        result += "<y:NodeLabel alignment=\"center\" visible=\"true\" ";
        for (int i = 0; i < labelAttributes.length; i++) {
            result += labelAttributes[i] + " ";
        }
        result += ">";
        result += name;
        result += "</y:NodeLabel>\n";
        if (shape != null) {
            result += shape.toGraphML();
        }
        if (geometry != null) {
            result += geometry.toGraphML();
        }
        result += "</y:" + nodeStyle + ">\n";
        result += "</data>\n";
        return result;
    }

    public static String createNode(String type, String name) {
        return createNode(name, type, null, null, null, new String[]{});
    }

    public static String createNode(String type, String name, Shape shape, String color,
                                    Geometry geometry) {
        return createNode(name, type, color, shape, geometry, new String[]{});
    }

    public static String createArrowStyle(String color, boolean directed) {

        String result = "";
        result += "<data key=\"d2\">\n";
        result += "<y:PolyLineEdge>\n";
        result += "<y:LineStyle color=\"#" + color + "\" type=\"line\" width=\"1.0\"/>";

        if (directed) {
            result += "<y:Arrows source=\"none\" target=\"standard\"/>";
        } else {
            result += "<y:Arrows source=\"none\" target=\"none\"/>";
        }
        result += "</y:PolyLineEdge>\n";
        result += "</data>\n";
        return result;
    }
}
