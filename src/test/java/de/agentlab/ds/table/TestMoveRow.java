package de.agentlab.ds.table;

import de.agentlab.AssertUtils;

import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class TestMoveRow extends BaseTableTest {

    public void testMoveRow() {
        Table<String, String, String> t = new Table<>();

        t.put("r1", "c1", "v1");
        t.put("r1", "c2", "v2");
        t.put("r1", "c3", "v3");
        t.put("r2", "c2", "v");

        t.moveRow("r1", "r3");

        Assert.assertEquals(t.size(), 4);
        Assert.assertFalse(t.getRowKeys().contains("r1"));
        Assert.assertTrue(t.getRowKeys().contains("r3"));

        AssertUtils.assertEqualsNoOrder(t.getRow("r3"), asList("v1", "v2", "v3"));
    }

}
