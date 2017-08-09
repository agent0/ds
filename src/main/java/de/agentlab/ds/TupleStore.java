package de.agentlab.ds;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class TupleStore<T> {

    private List<T[]> data = new ArrayList<>();

    public void put(T... tuple) {
        this.data.add(tuple);
    }

    public List<T[]> find(T... pattern) {
        List<T[]> result = new ArrayList<>();

        for (T[] entry : this.data) {
            if (this.matches(entry, pattern)) {
                result.add(entry);
            }
        }
        return result;
    }

    public Set<T[]> distinct(T... pattern) {
        return this.count(pattern).keySet();
    }

    public Map<T[], Integer> count(T... pattern) {
        Map<T[], List<T[]>> group = this.group(pattern);
        return group.entrySet().stream().collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue().size()));
    }

    public Map<T[], List<T[]>> group(T... pattern) {
        Map<List<T>, List<T[]>> result = new HashMap<>();

        for (T[] entry : this.data) {
            List<T> key = makeKey(entry, pattern);
            List<T[]> tmp = result.get(key);
            if (tmp == null) {
                tmp = new ArrayList<>();
                result.put(key, tmp);
            }
            tmp.add(entry);
        }

        Map<T[], List<T[]>> collect = result.entrySet().stream().collect(Collectors.toMap(e -> {
            T[] a = (T[]) new Object[e.getKey().size()];
            return e.getKey().toArray(a);
        }, e -> e.getValue()));
        return collect;
    }

    private List<T> makeKey(T[] entry, T[] pattern) {
        List<T> key = new ArrayList<T>();
        for (int i = 0; i < pattern.length; i++) {
            if (pattern[i] != null) {
                key.add(entry[i]);
            } else {
                key.add(null);
            }
        }
        return key;
    }

    private boolean matches(T[] entry, T[] pattern) {
        boolean result = true;
        for (int i = 0; i < pattern.length; i++) {
            result &= this.equals(entry[i], pattern[i]);
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
