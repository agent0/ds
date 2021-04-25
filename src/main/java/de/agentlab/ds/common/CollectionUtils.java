package de.agentlab.ds.common;

import java.util.Collection;

public class CollectionUtils {
    public static <T> T any(Collection<T> s) {
        if (s != null && !s.isEmpty()) {
            return s.iterator().next();
        }
        return null;
    }
}
