package de.agentlab.ds.table;

import de.agentlab.ds.Table;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class TestGet extends BaseTableTest {

    public void testGetRow() {
        Table<String, String, String> t = new Table<>();

        t.put("r", "c1", "v1");
        t.put("r", "c2", "v2");
        t.put("r", "c3", "v3");

        Assert.assertEquals(t.size(), 3);
        Assert.assertEquals(t.getRow("r"), asSet("v1", "v2", "v3"));
    }

    public void testGetRowMap() {
        Table<String, String, String> t = new Table<>();

        t.put("r", "c1", "v1");
        t.put("r", "c2", "v2");
        t.put("r", "c3", "v3");

        Assert.assertEquals(t.size(), 3);

        Map<String, String> rowMap = t.getRowMap("r");
        Assert.assertEquals(rowMap.size(), 3);
        Assert.assertEquals(rowMap.get("c1"), "v1");
        Assert.assertEquals(rowMap.get("c2"), "v2");
        Assert.assertEquals(rowMap.get("c3"), "v3");
    }
}
