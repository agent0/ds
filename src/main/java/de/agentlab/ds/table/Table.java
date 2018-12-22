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

    private Map<S, Map<T, V>> data = new HashMap<S, Map<T, V>>();

    public Table() {
    }

    public Table(Table<S, T, V> m) {
        for (Entry<S, Map<T, V>> e : m.data.entrySet()) {
            Map<T, V> v = m.data.get(e.getKey());
            this.data.put(e.getKey(), new HashMap<T, V>(v));
        }
    }

    public Table(Map<S, Map<T, V>> data) {
        for (Entry<S, Map<T, V>> e : data.entrySet()) {
            Map<T, V> v = data.get(e.getKey());
            this.data.put(e.getKey(), new HashMap<T, V>(v));
        }
    }

    public boolean contains(S rowKey, T colKey) {
        return this.get(rowKey, colKey) != null;
    }

    public V get(S rowKey, T colKey) {
        Map<T, V> row = this.data.get(rowKey);
        if (row == null) {
            return null;
        } else {
            return row.get(colKey);
        }
    }

    public boolean containsRowKey(S rowKey) {
        return this.data.containsKey(rowKey);
    }

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

    public List<V> getRow(S rowKey) {
        Map<T, V> row = this.data.get(rowKey);
        if (row == null) {
            return new ArrayList<>();
        } else {
            return new ArrayList<>(row.values());
        }
    }

    public Map<T, V> getRowMap(S rowKey) {
        Map<T, V> row = this.data.get(rowKey);
        if (row == null) {
            return Collections.emptyMap();
        } else {
            return row;
        }
    }

    public List<V> getCol(T colKey) {
        List<V> result = new ArrayList<V>();
        for (S rowKey : this.getRowKeys()) {
            result.add(this.get(rowKey, colKey));
        }
        return result;
    }

    public Set<S> getRowKeys() {
        return this.data.keySet();
    }

    public void moveRow(S fromRowKey, S toRowKey) {
        if (fromRowKey.equals(toRowKey)) {
            return;
        }
        Map<T, V> fromRow = this.data.get(fromRowKey);

        this.putRow(toRowKey, fromRow);
        this.data.remove(fromRowKey);
    }

    public void putRow(S rowKey, Map<T, V> row) {
        if (row != null) {
            for (Entry<T, V> e : row.entrySet()) {
                this.put(rowKey, e.getKey(), e.getValue());
            }
        }
    }

    public void put(S rowKey, T colKey, V value) {
        Map<T, V> row = this.data.get(rowKey);
        if (row == null) {
            row = new HashMap<T, V>();
            this.data.put(rowKey, row);
        }
        row.put(colKey, value);
    }

    public void removeRow(S rowKey) {
        this.data.remove(rowKey);
    }

    public Map<S, Map<T, V>> getData() {
        return data;
    }

    public void setData(Map<S, Map<T, V>> data) {
        this.data = data;
    }

    public Set<KeyPair<S, T>> getKeyPairs() {
        Set<KeyPair<S, T>> result = new HashSet<KeyPair<S, T>>();
        Set<S> rowKeys = this.data.keySet();
        for (S rowKey : rowKeys) {
            Map<T, V> rowData = this.data.get(rowKey);
            Set<T> colKeys = rowData.keySet();
            for (T colKey : colKeys) {
                result.add(new KeyPair<S, T>(rowKey, colKey));
            }
        }
        return result;
    }

    public void merge(Table<S, T, V> other) {
        List<TableEntry<S, T, V>> entries = other.getEntries();
        for (TableEntry<S, T, V> entry : entries) {
            this.put(entry.getRowKey(), entry.getColKey(), entry.getValue());
        }
    }

    public List<TableEntry<S, T, V>> getEntries() {
        List<TableEntry<S, T, V>> result = new ArrayList<TableEntry<S, T, V>>();
        Set<S> rowKeys = this.data.keySet();
        for (S rowKey : rowKeys) {
            Map<T, V> rowData = this.data.get(rowKey);
            Set<T> colKeys = rowData.keySet();
            for (T colKey : colKeys) {
                TableEntry<S, T, V> entry = new TableEntry<S, T, V>(rowKey, colKey, rowData.get(colKey));
                result.add(entry);
            }
        }
        return result;
    }

    public int size() {
        return this.getEntries().size();
    }

    public void clear() {
        this.data.clear();
    }

    public boolean isEmpty() {
        return this.data.isEmpty();
    }

    @Override
    public String toString() {
        return toCsv();
    }

    public String toCsv() {
        return toCsv(r -> r.toString(), c -> c.toString(), v -> v.toString(), Comparator.comparing(Object::toString), Comparator.comparing(Object::toString));
    }

    public String toCsv(RowKeyFormatter rowKeyFormatter, ColKeyFormatter colKeyFormatter, ValueFormatter valueFormatter,
                        Comparator<? super S> rowKeyComparator, Comparator<? super T> colKeyComparator) {
        String result = ";";

        List<S> rowKeys = new ArrayList<>(this.getRowKeys());
        if (rowKeyComparator != null) {
            Collections.sort(rowKeys, rowKeyComparator);
        }

        List<T> colKeys = new ArrayList<>(this.getColKeys());
        if (colKeyComparator != null) {
            Collections.sort(colKeys, colKeyComparator);
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

    public Set<T> getColKeys() {
        Set<T> colKeys = new HashSet<>();
        for (S row : getRowKeys()) {
            colKeys.addAll(data.get(row).keySet());
        }
        return colKeys;
    }

}
