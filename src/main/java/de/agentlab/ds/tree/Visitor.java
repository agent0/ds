package de.agentlab.ds.tree;

public interface Visitor<T> {

    boolean visit(T data);
}
