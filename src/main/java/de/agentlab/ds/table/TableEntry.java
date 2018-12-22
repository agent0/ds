package de.agentlab.ds.table;

public class TableEntry<S, T, V> {
    private S rowKey;
    private T colKey;
    private V value;

    public TableEntry(S rowKey, T colKey, V value) {
        this.rowKey = rowKey;
        this.colKey = colKey;
        this.value = value;
    }

    public S getRowKey() {
        return rowKey;
    }

    public T getColKey() {
        return colKey;
    }

    public V getValue() {
        return value;
    }
}
