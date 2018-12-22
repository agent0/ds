package de.agentlab.ds.tree;

public interface Mapper<T, S> {

    S map(T data);
}
