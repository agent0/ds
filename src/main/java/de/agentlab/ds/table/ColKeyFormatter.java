package de.agentlab.ds.table;

/**
 * String formatter for the column key type.
 *
 * @param <T> the column data type
 */
public interface ColKeyFormatter<T> {
    String format(T colKey);
}
