package de.agentlab.ds.common;

import java.text.Collator;

public class StringUtils {

    /**
     * Pad a string with spaces to the given length.
     *
     * @param s   the string to pad
     * @param len to length to pad to
     * @return the padded string
     */
    public static String rightPad(String s, int len) {
        if (len > 0) {
            return String.format("%-" + len + "s", s);
        } else {
            return s;
        }
    }

    /**
     * Remove all whitespaces from the left side of a string.
     *
     * @param s the string to trim
     * @return the trimmed string
     */
    public static String leftTrim(String s) {
        int i = 0;
        while (i < s.length() && Character.isWhitespace(s.charAt(i))) {
            i++;
        }
        return s.substring(i);
    }

    /**
     * Repeat the given string.
     *
     * @param s     the string to repeat
     * @param count the number of repeats
     * @return the repeated string
     */
    public static String repeat(String s, int count) {
        return String.format("%0" + count + "d", 0).replace("0", s);
    }

    /**
     * Compare two string using the default collator.
     *
     * @param i1 the first input string, use an empty string if null
     * @param i2 the second input string, use an empty empty string if null
     * @return the result of the compare operation using the default collator
     */
    public static int compareWithCollator(String i1, String i2) {
        String s1 = i1 != null ? i1 : "";
        String s2 = i2 != null ? i2 : "";

        return Collator.getInstance().compare(s1, s2);
    }
}
