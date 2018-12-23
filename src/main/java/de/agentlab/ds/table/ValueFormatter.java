package de.agentlab.ds.table;

/**
 * String formatter for the value type.
 *
 * @param <T> the value data type
 */
public interface ValueFormatter<V> {
    String format(V value);
}
