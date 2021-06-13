package de.agentlab.ds.table;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

@Test
public class TestFind extends BaseTableTest {
    @Test
    public void testFindRows_single() {
        Table<String, String, String> t = new Table<>();

        t.put("r1", "c1", "v1");
        t.put("r1", "c2", "v2");
        t.put("r2", "c2", "v2");
        t.put("r3", "c3", "v3");

        System.out.println(t.toPrettyTable());
        List<String> rowKeys = t.findRows(Arrays.asList("v1", "v2"));
        Assert.assertEquals(rowKeys.size(), 1);
        Assert.assertTrue(rowKeys.contains("r1"));
    }

    @Test
    public void testFindRows_multi() {
        Table<String, String, String> t = new Table<>();

        t.put("r1", "c1", "v1");
        t.put("r1", "c2", "v2");
        t.put("r2", "c2", "v2");
        t.put("r3", "c3", "v3");

        System.out.println(t.toPrettyTable());
        List<String> rowKeys = t.findRows(Arrays.asList("v2"));
        Assert.assertEquals(rowKeys.size(), 2);
        Assert.assertTrue(rowKeys.contains("r1"));
        Assert.assertTrue(rowKeys.contains("r2"));
    }
}
