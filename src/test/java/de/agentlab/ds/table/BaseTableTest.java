package de.agentlab.ds.table;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.testng.annotations.Test;

@Test
public class BaseTableTest {

    public <T> Set<T> asSet(T... s) {
        return new HashSet<>(Arrays.asList(s));
    }
}
