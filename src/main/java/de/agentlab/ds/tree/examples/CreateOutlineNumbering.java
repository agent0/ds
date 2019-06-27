package de.agentlab.ds.tree.examples;

import de.agentlab.ds.tree.Tree;

import java.util.HashMap;
import java.util.Map;

public class CreateOutlineNumbering {

    public static void main(String[] args) {

        Tree<String> t = new Tree();
        t.add("A");
        t.addChild("A", "B");
        t.addChild("B", "C");
        t.addChild("B", "C#");
        t.addChild("C", "C++");
        t.add("D");
        t.addChild("D", "E");

        Map<String, String> outline = new HashMap<>();
        for (String item : t.getPreorderList()) {

            String parent = t.getParent(item);
            int index;
            String value;
            if (parent == null) {
                index = t.getRoots().indexOf(item);
                value = String.valueOf(index);
                outline.put(item, value);
            } else {
                index = t.getChildren(parent).indexOf(item);
                value = outline.get(parent) + "." + index;
                outline.put(item, value);
            }

            System.out.println(String.format("%-10s", value) + " " + item);
        }
    }
}
