package de.agentlab.ds.table;

import de.agentlab.ds.Table;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class TestPut extends BaseTableTest {

    public void testPut() {
        Table<String, String, String> t = new Table<>();

        t.put("r", "c", "v");

        Assert.assertEquals(t.size(), 1);
        Assert.assertTrue(t.contains("r", "c"));
        Assert.assertEquals(t.get("r", "c"), "v");
    }

    public void testPutOverwrite() {
        Table<String, String, String> t = new Table<>();

        t.put("r", "c", "v");
        t.put("r", "c", "v2");

        Assert.assertEquals(t.size(), 1);
        Assert.assertTrue(t.contains("r", "c"));
        Assert.assertEquals(t.get("r", "c"), "v2");
    }

    public void testPutRow() {
        Table<String, String, String> t = new Table<>();

        t.put("r", "c", "v");
        t.put("r", "c2", "v2");

        Map<String, String> rowData = new HashMap<>();
        rowData.put("c1", "v3");
        t.putRow("r2", rowData);

        Assert.assertEquals(t.size(), 3);
        Assert.assertTrue(t.contains("r2", "c1"));
    }
}
