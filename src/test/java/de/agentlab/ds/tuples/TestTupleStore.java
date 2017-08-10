package de.agentlab.ds.tuples;

import de.agentlab.ds.TupleStore;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.testng.annotations.Test;

@Test
public class TestTupleStore {

    @Test
    public void testFind() {
        TupleStore<String> ts = new TupleStore<>();
        ts.put("1", "2", "3");
        ts.put("1", "2", "4");
        ts.put("3", "3", "4");
        ts.put("3", "5", "4");

        System.out.println(ts);

        System.out.println("--");

        List<List<String>> result = ts.find("1", "2");
        printResult(result);

        System.out.println("--");

        result = ts.find("1", null, "4");
        printResult(result);

        System.out.println("--");

        result = ts.find(null, null, "4");
        printResult(result);

        System.out.println("--");

        Set<List<String>> distinct = ts.distinct(null, null, "");
        System.out.println(distinct);

        Map<List<String>, Integer> count = ts.count(null, null, "");
        System.out.println(count);

        Map<List<String>, List<List<String>>> group = ts.group(null, null, "");
        group.entrySet().forEach(e -> {
            System.out.println(Arrays.asList(e.getKey()) + "=" + e.getValue().stream().map(a -> Arrays.asList(a)).collect(Collectors.toList()));
        });

        group = ts.group("", "");
        group.entrySet().forEach(e -> {
            System.out.println(Arrays.asList(e.getKey()) + "=" + e.getValue().stream().map(a -> Arrays.asList(a)).collect(Collectors.toList()));
        });

        group = ts.group("", null, "");
        group.entrySet().forEach(e -> {
            System.out.println(Arrays.asList(e.getKey()) + "=" + e.getValue().stream().map(a -> Arrays.asList(a)).collect(Collectors.toList()));
        });
    }

    private void printResult(List<List<String>> result) {
        result.forEach(e -> System.out.println(Arrays.asList(e)));
    }
}
