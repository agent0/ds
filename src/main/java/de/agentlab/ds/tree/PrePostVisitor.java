package de.agentlab.ds.tree;

public interface PrePostVisitor<T> {

    boolean visitPre(T data);

    boolean visitPost(T data);
}
