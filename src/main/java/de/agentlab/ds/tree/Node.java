package de.agentlab.ds.tree;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

class Node<T> implements Serializable {

    public static final long serialVersionUID = 42L;
    private final List<Node<T>> children = new ArrayList<>();
    private Node<T> parent;
    private T data;

    public Node() {
    }

    public Node(Node<T> parent, T data) {
        this.parent = parent;
        this.data = data;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public List<Node<T>> getChildren() {
        return this.children;
    }

    public void add(Node<T> child) {
        this.children.add(child);
        child.parent = this;
    }

    public void add(int index, Node<T> child) {
        this.children.add(index, child);
        child.parent = this;
    }

    public void push(Node<T> child) {
        this.children.add(0, child);
        child.parent = this;
    }

    public void remove(Node<T> child) {
        this.children.remove(child);
    }

    public String toString(int depth, ElementFormatter<T> formatter) {
        String result = indent(depth * 2);

        if (this.data != null) {
            result += formatter.format(this.data) + "\n";
        } else {
            // ignore root node
            if (depth != -1) {
                result += "\n";
            }
        }
        for (Node<T> child : this.children) {
            result += child.toString(depth + 1, formatter);
        }
        return result;
    }

    public int getDepth() {
        int depth = 0;

        Node<T> node = this;
        while (node.getParent() != null) {
            depth++;
            node = node.getParent();
        }

        return depth - 1;
    }

    public Node<T> getParent() {
        return this.parent;
    }

    @Override
    public String toString() {
        return this.data != null ? this.data.toString() : "root";
    }

    private String indent(int depth) {
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < depth; i++) {
            b.append(" ");
        }
        return b.toString();
    }
}
