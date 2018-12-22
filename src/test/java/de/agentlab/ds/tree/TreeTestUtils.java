package de.agentlab.ds.tree;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class TreeTestUtils {

    public static void populate(Tree<TestItem> t, int depth, int count) {
        TreeTestUtils.populate(t, null, depth, count);
    }

    private static void populate(Tree<TestItem> t, TestItem parent, int depth, int count) {
        if (depth == 0) {
            return;
        }
        for (int i = 0; i < count; i++) {
            if (parent == null) {
                TestItem node = new TestItem(String.valueOf(i));
                t.add(node);
                populate(t, node, depth - 1, count);
            } else {
                TestItem node = new TestItem(parent + "." + String.valueOf(i));
                t.addChild(parent, node);
                populate(t, node, depth - 1, count);
            }
        }
    }

    public static <T> String toFlatString(Tree<T> t) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(baos);
        out.print(t);
        String flat = baos.toString().replaceAll("\n", "|");
        return flat;
    }

    public static <T> String toFlatString(Tree<T> t, ElementFormatter<T> f) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(baos);
        out.print(t.toString(f));
        String flat = baos.toString().replaceAll("\n", "|");
        return flat;
    }

}
