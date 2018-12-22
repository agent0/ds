package de.agentlab.ds.tree;

public interface Filter<T> {
    boolean accept(T data);
}
