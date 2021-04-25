package de.agentlab.ds.common;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MapUtils {
    public static <S, T> Map<S, T> createLookupMap(Collection<T> l, Function<T, S> idFn) {
        return l.stream().collect(Collectors.toMap(idFn, i -> i));
    }
}
