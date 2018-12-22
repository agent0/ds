package de.agentlab.ds.output;

import de.agentlab.ds.common.ColorUtils;
import de.agentlab.ds.graph.Node;


public class GexfUtils {

    public static String createNode(String id, String type, String label, Shape shape, String color,
                                    Geometry geometry) {
        String result = "";
        if (label != null) {
            result += "<node id=\"" + id + "\" label=\"" + label + "\">\n";
        } else {
            result += "<node id=\"" + id + "\">\n";
        }

        if (color != null) {
            int[] rgb = ColorUtils.toRGB(color);
            result += "<viz:color r=\"" + rgb[0] + "\" g=\"" + rgb[1] + "\" b=\"" + rgb[2] + "\"/>";
        } else {
            result += "<viz:color g=\"51\" r=\"255\" b=\"51\"/>";
        }
        result += "</node>\n";

        return result;
    }

    public static String createNode(Node node) {
        String result = "";
        if (node.getLabel() != null) {
            result += "<node id=\"" + node.getId() + "\" label=\"" + node.getLabel() + "\">\n";
        } else {
            result += "<node id=\"" + node.getId() + "\">\n";
        }

        if (node.getTagCount() > 0) {
            result += "<attvalues>\n";
            for (String tag : node.getTags()) {
                result += "<attvalue for=\"" + tag + "\" value=\"" + node.getTagValue(tag) + "\"/>\n";
            }
            result += "</attvalues>\n";
        }

        if (node.getColor() != null) {
            int[] rgb = ColorUtils.toRGB(node.getColor());
            result += "<viz:color r=\"" + rgb[0] + "\" g=\"" + rgb[1] + "\" b=\"" + rgb[2] + "\"/>";
        } else {
            result += "<viz:color g=\"51\" r=\"255\" b=\"51\"/>";
        }

        result += "</node>\n";

        return result;
    }

}
