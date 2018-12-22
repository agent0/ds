package de.agentlab.ds.table;

public interface RowKeyFormatter<S> {
    String format(S rowKey);
}
