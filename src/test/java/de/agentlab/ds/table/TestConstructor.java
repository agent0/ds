package de.agentlab.ds.table;

import de.agentlab.AssertUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class TestConstructor extends BaseTableTest {

    @Test
    public void testMapConstructor() {
        Map<String, Map<String, String>> m = new HashMap<>();

        Map<String, String> row = new HashMap<>();
        row.put("c1", "v1");

        m.put("r1", row);
        m.put("r2", row);

        Table<String, String, String> t2 = new Table<>(m);

        Assert.assertEquals(t2.size(), 2);
        AssertUtils.assertEqualsNoOrder(new ArrayList<>(t2.getRowKeys()), Arrays.asList("r1", "r2"));
        Assert.assertEquals(t2.getColKeys(), Arrays.asList("c1"));
    }

    @Test
    public void testCopyConstructor() {
        Table<String, String, String> t = new Table<>();

        t.put("r1", "c1", "v1");
        t.put("r2", "c1", "v1");

        Table<String, String, String> t2 = new Table<>(t);

        Assert.assertEquals(t2.size(), t.size());
        Assert.assertEquals(t2.getRowKeys(), t.getRowKeys());
        Assert.assertEquals(t2.getColKeys(), t.getColKeys());
    }
}
