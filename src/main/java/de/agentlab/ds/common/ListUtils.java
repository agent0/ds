package de.agentlab.ds.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import static de.agentlab.ds.common.CollectionsUtils.any;

public class ListUtils {

    public static <T> List<T> chop(List<T> l) {

        ArrayList<T> result = new ArrayList<>(l);
        if (l.size() > 0) {
            result.remove(result.size() - 1);
        }
        return result;
    }

    public static <T> List<Set<T>> groupBy(List<T> l, BiFunction<T, T, Boolean> equalsFn) {

        class local {
            public void insert(T t, List<Set<T>> groups, BiFunction<T, T, Boolean> equalsFn) {
                for (Set<T> group : groups) {
                    T any = any(group);
                    if (any != null && equalsFn.apply(t, any)) {
                        group.add(t);
                        return;
                    }
                }

                // no matching group found
                Set<T> newGroup = new HashSet<>();
                newGroup.add(t);
                groups.add(newGroup);
            }
        }
        local local = new local();

        List<Set<T>> groups = new ArrayList<>();

        for (T t : l) {
            local.insert(t, groups, equalsFn);
        }

        return groups;
    }

    public static boolean isEmpty(String s) {
        return s == null || s.trim().isEmpty();
    }

    public static <T> String join(Collection<T> collection, String delimiter) {
        return collection.stream().map(Objects::toString).collect(Collectors.joining(delimiter));
    }

    public static boolean notEmpty(String s) {
        return !isEmpty(s);
    }

    public static List<String> split(String s, String delimiter) {
        if (notEmpty(s)) {
            return new ArrayList<>(Arrays.asList(s.split("\\s*" + delimiter + "\\s*")));
        }
        return new ArrayList<>();
    }

}
