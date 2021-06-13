package de.agentlab.ds.common;

import java.text.Collator;

public class StringUtils {

    public static String leftPad(String str, int length, char padChar) {
        return String.format("%1$" + length + "s", str).replace(' ', padChar);
    }

    public static int compareWithCollator(String i1, String i2) {
        String s1 = i1 != null ? i1 : "";
        String s2 = i2 != null ? i2 : "";

        return Collator.getInstance().compare(s1, s2);
    }
}
