package de.agentlab.ds.common;

import java.util.ArrayList;
import java.util.List;

public class ListUtils {
    public static <T> List<T> chop(List<T> l) {

        ArrayList<T> result = new ArrayList<>(l);
        if (l.size() > 0) {
            result.remove(result.size() - 1);
        }
        return result;
    }
}
