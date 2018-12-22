package de.agentlab.ds.tree;

public class IdentityMapper<S> implements Mapper<S, S> {

    @Override
    public S map(S data) {
        return data;
    }
}
