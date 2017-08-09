package de.agentlab.ds.tuples;

import de.agentlab.ds.TupleStore;

import java.util.Arrays;
import java.util.List;

import org.testng.annotations.Test;

@Test
public class TestTupleStore {

    @Test
    public void testFind() {
        TupleStore<String> ts = new TupleStore<>();
        ts.put("1", "2", "3");
        ts.put("1", "2", "4");
        ts.put("3", "3", "4");

        System.out.println(ts);

        List<String[]> result = ts.find("1", "2");
        printResult(result);

        result = ts.find("1", null, "4");
        printResult(result);

        result = ts.find(null, null, "4");
        printResult(result);
    }

    private void printResult(List<String[]> result) {
        result.forEach(e -> System.out.println(Arrays.asList(e)));
    }
}
