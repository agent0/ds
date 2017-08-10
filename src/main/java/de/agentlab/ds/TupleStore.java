package de.agentlab.ds;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class TupleStore<T> {

    private List<List<T>> data = new ArrayList<>();

    public void put(T... tuple) {
        this.data.add(Arrays.asList(tuple));
    }

    public List<List<T>> find(T... pattern) {
        List<List<T>> result = new ArrayList<>();

        for (List<T> entry : this.data) {
            if (this.matches(entry, Arrays.asList(pattern))) {
                result.add(entry);
            }
        }
        return result;
    }

    public Set<List<T>> distinct(T... pattern) {
        return this.count(pattern).keySet();
    }

    public Map<List<T>, Integer> count(T... pattern) {
        Map<List<T>, List<List<T>>> group = this.group(pattern);
        return group.entrySet().stream().collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue().size()));
    }

    public Map<List<T>, List<List<T>>> group(T... pattern) {
        Map<List<T>, List<List<T>>> result = new HashMap<>();

        for (List<T> entry : this.data) {
            List<T> key = makeKey(entry, Arrays.asList(pattern));
            List<List<T>> tmp = result.get(key);
            if (tmp == null) {
                tmp = new ArrayList<>();
                result.put(key, tmp);
            }
            tmp.add(entry);
        }

        return result;
    }

    private List<T> makeKey(List<T> entry, List<T> pattern) {
        List<T> key = new ArrayList<T>();
        for (int i = 0; i < pattern.size(); i++) {
            if (pattern.get(i) != null) {
                key.add(entry.get(i));
            } else {
                key.add(null);
            }
        }
        return key;
    }

    private boolean matches(List<T> entry, List<T> pattern) {
        boolean result = true;
        for (int i = 0; i < pattern.size(); i++) {
            result &= this.equals(entry.get(i), pattern.get(i));
        }
        return result;
    }

    private boolean equals(T t1, T t2) {
        if (t2 == null) {
            return true;
        }
        if (t1 == null) {
            return false;
        }
        return t1.equals(t2);
    }

    public String toString() {
        StringBuffer result = new StringBuffer();
        this.data.forEach(e -> result.append(Arrays.asList(e) + "\n"));
        return result.toString();
    }
}
