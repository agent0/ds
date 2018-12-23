package de.agentlab.ds.table;

import de.agentlab.AssertUtils;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class TestGet extends BaseTableTest {

    @Test
    public void testGetRow() {
        Table<String, String, String> t = new Table<>();

        t.put("r", "c1", "v1");
        t.put("r", "c2", "v2");
        t.put("r", "c3", "v3");

        Assert.assertEquals(t.size(), 3);
        AssertUtils.assertEqualsNoOrder(t.getRow("r"), asList("v1", "v2", "v3"));
    }

    @Test
    public void testGetRowNotFound() {
        Table<String, String, String> t = new Table<>();

        t.put("r", "c1", "v1");

        Assert.assertEquals(t.size(), 1);
        Assert.assertTrue(t.getRow("X").isEmpty());
    }

    @Test
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

    @Test
    public void testGetRowMapNotFound() {
        Table<String, String, String> t = new Table<>();

        t.put("r", "c1", "v1");

        Map<String, String> colData = t.getRowMap("r1");

        Assert.assertTrue(colData.isEmpty());
    }

    @Test
    public void testGetCol() {
        Table<String, String, String> t = new Table<>();

        t.put("r", "c1", "v1");
        t.put("r", "c3", "v3");

        t.put("r2", "c1", "v2");

        Assert.assertEquals(t.size(), 3);
        AssertUtils.assertEqualsNoOrder(t.getCol("c1"), asList("v1", "v2"));
    }

    @Test
    public void testGetData() {
        Table<String, String, String> t = new Table<>();

        t.put("r", "c1", "v1");
        t.put("r", "c3", "v3");

        t.put("r2", "c1", "v2");

        Assert.assertEquals(t.size(), 3);
        Map<String, Map<String, String>> data = t.getData();

        Map<String, String> r = data.get("r");
        Assert.assertEquals(r.get("c1"), "v1");
        Assert.assertEquals(r.get("c3"), "v3");

        Map<String, String> r2 = data.get("r2");
        Assert.assertEquals(r2.get("c1"), "v2");
    }

    @Test
    public void testIsEmpty() {
        Table<String, String, String> t = new Table<>();
        Assert.assertTrue(t.isEmpty());
    }
}
