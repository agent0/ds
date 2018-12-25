package de.agentlab.ds.table;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Container class for row/column data
 *
 * @author Jürgen Lind, iteratec GmbH
 */
public class Table<S, T, V> {

    private Map<S, Map<T, V>> data = new HashMap<>();

    public Table() {
    }

    public Table(Table<S, T, V> m) {
        for (Entry<S, Map<T, V>> e : m.data.entrySet()) {
            Map<T, V> v = m.data.get(e.getKey());
            this.data.put(e.getKey(), new HashMap<>(v));
        }
    }

    public Table(Map<S, Map<T, V>> data) {
        for (Entry<S, Map<T, V>> e : data.entrySet()) {
            Map<T, V> v = data.get(e.getKey());
            this.data.put(e.getKey(), new HashMap<>(v));
        }
    }

    /**
     * Checks, if an element exists at a given position.
     *
     * @param rowKey the row key of the position
     * @param colKey the column key of position
     * @return <code>true</code> if an element exists at the given position, <code>false</code> otherwise
     */
    public boolean contains(S rowKey, T colKey) {
        return this.get(rowKey, colKey) != null;
    }

    /**
     * Provides the element at a given position.
     *
     * @param rowKey the row key of the position
     * @param colKey the column key of position
     * @return the element at the given position if such an element exists, <code>null</code> otherwise
     */
    public V get(S rowKey, T colKey) {
        Map<T, V> row = this.data.get(rowKey);
        if (row == null) {
            return null;
        } else {
            return row.get(colKey);
        }
    }

    /**
     * Checks, if a row with a given key exists.
     *
     * @param rowKey the row key
     * @return <code>true</code> if an element exists at the given position, <code>false</code> otherwise
     */
    public boolean containsRowKey(S rowKey) {
        return this.data.containsKey(rowKey);
    }

    /**
     * Provides the row data for a given row key as list.
     *
     * @param rowKey the row key
     * @return the list of elements for the given row key
     */
    public List<V> getRow(S rowKey) {
        Map<T, V> row = this.data.get(rowKey);
        if (row == null) {
            return new ArrayList<>();
        } else {
            return new ArrayList<>(row.values());
        }
    }

    /**
     * Provides the row data for a given row key as map.
     *
     * @param rowKey the row key
     * @return the map of elements for the given row key where each element is mapped by its column key
     */
    public Map<T, V> getRowMap(S rowKey) {
        Map<T, V> row = this.data.get(rowKey);
        if (row == null) {
            return Collections.emptyMap();
        } else {
            return row;
        }
    }

    /**
     * Provides the column data for a given column key as list.
     *
     * @param colKey the col key
     * @return the list of elements for the given column key
     */
    public List<V> getCol(T colKey) {
        List<V> result = new ArrayList<>();
        for (S rowKey : this.getRowKeys()) {
            result.add(this.get(rowKey, colKey));
        }
        return result;
    }

    /**
     * @return the set of all row keys
     */
    public Set<S> getRowKeys() {
        return this.data.keySet();
    }

    public List<S> getRowKeys(Comparator<S> comparator) {
        ArrayList<S> sorted = new ArrayList<>(this.data.keySet());
        sorted.sort(comparator);
        return sorted;
    }

    /**
     * @return the set of all column keys
     */
    public Set<T> getColKeys() {
        Set<T> colKeys = new HashSet<>();
        for (S row : getRowKeys()) {
            colKeys.addAll(data.get(row).keySet());
        }
        return colKeys;
    }

    public List<T> getColKeys(Comparator<T> comparator) {
        ArrayList<T> sorted = new ArrayList<>(this.getColKeys());
        sorted.sort(comparator);
        return sorted;
    }

    /**
     * Moves all data from a certain row key to another row key
     *
     * @param fromRowKey the origin row key
     * @param toRowKey   the target row key
     */
    public void moveRow(S fromRowKey, S toRowKey) {
        if (fromRowKey.equals(toRowKey)) {
            return;
        }
        Map<T, V> fromRow = this.data.get(fromRowKey);

        this.putRow(toRowKey, fromRow);
        this.data.remove(fromRowKey);
    }

    /**
     * Add complete row of data for a certain row key. The row data is given as map of column keys to data elements.
     *
     * @param rowKey the key for the new row
     * @param row    the row data
     */
    public void putRow(S rowKey, Map<T, V> row) {
        if (row != null) {
            for (Entry<T, V> e : row.entrySet()) {
                this.put(rowKey, e.getKey(), e.getValue());
            }
        }
    }

    /**
     * Stores a data element at the given position
     *
     * @param rowKey the row key of the position
     * @param colKey the column key of the position
     * @param value  the data element to store
     */
    public void put(S rowKey, T colKey, V value) {
        Map<T, V> row = this.data.get(rowKey);
        if (row == null) {
            row = new HashMap<>();
            this.data.put(rowKey, row);
        }
        row.put(colKey, value);
    }

    /**
     * Remove the row with the given key
     *
     * @param rowKey the key of the row to remove
     */
    public void removeRow(S rowKey) {
        this.data.remove(rowKey);
    }

    /**
     * Provides nested maps of the table data. The first level of the map maps the row keys to column maps. Each column
     * map maps a column key to a data element.
     *
     * @return nested maps of the table data
     */
    public Map<S, Map<T, V>> getData() {
        return data;
    }

    /**
     * Populates the table from nested maps.  The first level of the map maps the row keys to column maps. Each column
     * map maps a column key to a data element.
     *
     * @param data a map of maps with element data
     */
    public void setData(Map<S, Map<T, V>> data) {
        this.data = data;
    }

    /**
     * Removes an element a the given position.
     *
     * @param rowKey the row key of the position
     * @param colKey the column key of the position
     * @return the removed element
     */
    public V remove(S rowKey, T colKey) {
        Map<T, V> row = this.data.get(rowKey);
        if (row == null) {
            return null;
        } else {
            V removed = row.remove(colKey);
            if (row.isEmpty()) {
                this.data.remove(rowKey);
            }
            return removed;
        }
    }

    /**
     * @return the set of {@link KeyPair} elements of the table data.
     */
    public Set<KeyPair<S, T>> getKeyPairs() {
        Set<KeyPair<S, T>> result = new HashSet<>();
        Set<S> rowKeys = this.data.keySet();
        for (S rowKey : rowKeys) {
            Map<T, V> rowData = this.data.get(rowKey);
            Set<T> colKeys = rowData.keySet();
            for (T colKey : colKeys) {
                result.add(new KeyPair<>(rowKey, colKey));
            }
        }
        return result;
    }

    /**
     * Merges the data of another table into this table. Each element of the other table is added at the position defined
     * of the other table. If an element exists in this table, the element is overwritten.
     *
     * @param other the other table to merge
     */
    public void merge(Table<S, T, V> other) {
        List<TableEntry<S, T, V>> entries = other.getEntries();
        for (TableEntry<S, T, V> entry : entries) {
            this.put(entry.getRowKey(), entry.getColKey(), entry.getValue());
        }
    }

    /**
     * @return a list of {@link TableEntry} elements of this table
     */
    public List<TableEntry<S, T, V>> getEntries() {
        List<TableEntry<S, T, V>> result = new ArrayList<>();
        Set<S> rowKeys = this.data.keySet();
        for (S rowKey : rowKeys) {
            Map<T, V> rowData = this.data.get(rowKey);
            Set<T> colKeys = rowData.keySet();
            for (T colKey : colKeys) {
                TableEntry<S, T, V> entry = new TableEntry<>(rowKey, colKey, rowData.get(colKey));
                result.add(entry);
            }
        }
        return result;
    }

    /**
     * Provides the size of this table. The size is defined as the number of data elements in the table.
     *
     * @return the size of this table.
     */
    public int size() {
        return this.getEntries().size();
    }

    /**
     * Remove all data from the table.
     */
    public void clear() {
        this.data.clear();
    }

    /**
     * Checks if the table is empty.
     *
     * @return <code>true</code> if the table is empty, <code>false</code> otherwise
     */
    public boolean isEmpty() {
        return this.data.isEmpty();
    }

    @Override
    public String toString() {
        return toCsv();
    }

    public String toCsv() {
        return toCsv(Object::toString, Object::toString, Object::toString, Comparator.comparing(Object::toString), Comparator.comparing(Object::toString));
    }

    public String toCsv(RowKeyFormatter<S> rowKeyFormatter, ColKeyFormatter<T> colKeyFormatter, ValueFormatter<V> valueFormatter,
                        Comparator<? super S> rowKeyComparator, Comparator<? super T> colKeyComparator) {
        String result = ";";

        List<S> rowKeys = new ArrayList<>(this.getRowKeys());
        if (rowKeyComparator != null) {
            rowKeys.sort(rowKeyComparator);
        }

        List<T> colKeys = new ArrayList<>(this.getColKeys());
        if (colKeyComparator != null) {
            colKeys.sort(colKeyComparator);
        }

        for (T colKey : colKeys) {
            result += colKeyFormatter.format(colKey) + ";";
        }
        result += "\n";

        for (S rowKey : rowKeys) {
            result += rowKeyFormatter.format(rowKey) + ";";
            for (T colKey : colKeys) {
                if (this.get(rowKey, colKey) != null) {
                    result += valueFormatter.format(this.get(rowKey, colKey));
                }
                result += ";";
            }
            result += "\n";
        }

        return result;
    }

}
