package de.agentlab.ds.common;

public class ColorUtils {

    public static int[] toRGB(String color) {
        int[] result = new int[3];

        try {

            if (color.startsWith("#")) {
                color = color.substring(1);
            }
            result[0] = Integer.parseInt(color.substring(0, 1), 16) * 16
                    + Integer.parseInt(color.substring(1, 2), 16);
            result[1] = Integer.parseInt(color.substring(2, 3), 16) * 16
                    + Integer.parseInt(color.substring(3, 4), 16);
            result[2] = Integer.parseInt(color.substring(4, 5), 16) * 16
                    + Integer.parseInt(color.substring(5, 6), 16);
        } catch (Exception e) {
            throw new IllegalArgumentException("Wrong color format: " + color);
        }

        return result;
    }
}
