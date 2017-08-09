package de.agentlab.ds;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        this.data.forEach(e -> result.append(Arrays.asList(e)));
        return result.toString();
    }

}
