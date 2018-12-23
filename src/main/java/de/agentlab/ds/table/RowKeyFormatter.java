package de.agentlab.ds.table;

/**
 * String formatter for the row key type.
 *
 * @param <T> the row data type
 */
public interface RowKeyFormatter<S> {
    String format(S rowKey);
}
