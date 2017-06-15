package de.agentlab.ds.table;

import de.agentlab.ds.Table;

import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class TestPut extends BaseTableTest {

    public void testPut() {
        Table<String, String, String> t = new Table<>();

        t.put("r", "c", "v");

        Assert.assertEquals(t.size(), 1);
        Assert.assertEquals(t.contains("r", "c"), true);
        Assert.assertEquals(t.get("r", "c"), "v");
    }

    public void testPutOverwrite() {
        Table<String, String, String> t = new Table<>();

        t.put("r", "c", "v");
        t.put("r", "c", "v2");

        Assert.assertEquals(t.size(), 1);
        Assert.assertEquals(t.contains("r", "c"), true);
        Assert.assertEquals(t.get("r", "c"), "v2");
    }
}
