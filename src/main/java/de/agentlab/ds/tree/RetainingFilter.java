package de.agentlab.ds.tree;

public interface RetainingFilter<T> extends Filter<T> {

    boolean retain(T data);
}
