package de.agentlab.ds.table;

import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class TestRemove extends BaseTableTest {

    @Test
    public void testRemove() {
        Table<String, String, String> t = new Table<>();

        t.put("r", "c", "v");
        t.put("r", "c2", "v2");

        Assert.assertEquals(t.size(), 2);
        Assert.assertEquals(t.contains("r", "c"), true);
        Assert.assertEquals(t.get("r", "c"), "v");

        t.remove("r", "c");

        Assert.assertEquals(t.size(), 1);
        Assert.assertEquals(t.contains("r", "c"), false);
        Assert.assertEquals(t.get("r", "c"), null);
    }

    @Test
    public void testRemoveNotFound() {
        Table<String, String, String> t = new Table<>();

        t.put("r", "c", "v");
        t.put("r", "c2", "v2");

        String removed = t.remove("r1", "c1");
        Assert.assertNull(removed);
    }

    @Test
    public void testRemoveRow() {
        Table<String, String, String> t = new Table<>();

        t.put("r", "c", "v");
        t.put("r", "c2", "v2");

        t.put("r2", "c", "v");

        Assert.assertEquals(t.size(), 3);
        t.removeRow("r");
        Assert.assertEquals(t.size(), 1);
        Assert.assertEquals(t.contains("r", "c"), false);
        Assert.assertEquals(t.contains("r", "c2"), false);
    }

    @Test
    public void testClear() {
        Table<String, String, String> t = new Table<>();

        t.put("r", "c", "v");
        t.put("r", "c2", "v2");

        t.put("r2", "c", "v");

        Assert.assertEquals(t.size(), 3);
        t.clear();
        Assert.assertEquals(t.size(), 0);
    }
}
