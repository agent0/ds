package de.agentlab.ds.table;

/**
 * Represents a specific position within a table.
 *
 * @param <S> the row data type
 * @param <T> the column data type
 */
public class KeyPair<S, T> {
    private S rowKey;
    private T colKey;

    public KeyPair(S rowKey, T colKey) {
        this.rowKey = rowKey;
        this.colKey = colKey;
    }

    public S getRowKey() {
        return rowKey;
    }

    public T getColKey() {
        return colKey;
    }

    @Override
    public int hashCode() {
        int result = rowKey != null ? rowKey.hashCode() : 0;
        result = 31 * result + (colKey != null ? colKey.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        KeyPair<?, ?> keyPair = (KeyPair<?, ?>) o;

        if (rowKey != null ? !rowKey.equals(keyPair.rowKey) : keyPair.rowKey != null) return false;
        return colKey != null ? colKey.equals(keyPair.colKey) : keyPair.colKey == null;
    }

    @Override
    public String toString() {
        return "<" + rowKey + ", " + colKey + ">";
    }
}
