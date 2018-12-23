package de.agentlab.ds.table;

/**
 * Represents a table value together with its position.
 *
 * @param <S> The row key data type
 * @param <T> thr column key data type
 * @param <V> the value data type
 */
public class TableEntry<S, T, V> {
    private S rowKey;
    private T colKey;
    private V value;

    public TableEntry(S rowKey, T colKey, V value) {
        this.rowKey = rowKey;
        this.colKey = colKey;
        this.value = value;
    }

    /**
     * @return the row key of the entry
     */
    public S getRowKey() {
        return rowKey;
    }

    /**
     * @return the column key of the entry
     */
    public T getColKey() {
        return colKey;
    }

    /**
     * @return the value of the entry
     */
    public V getValue() {
        return value;
    }
}
