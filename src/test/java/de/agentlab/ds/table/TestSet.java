package de.agentlab.ds.table;

import de.agentlab.AssertUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class TestSet extends BaseTableTest {

    @Test
    public void testSetData() {
        ConcurrentMap<String, ConcurrentMap<String, String>> m = new ConcurrentHashMap<>();

        ConcurrentMap<String, String> row = new ConcurrentHashMap<>();
        row.put("c1", "v1");

        m.put("r1", row);
        m.put("r2", row);

        Table<String, String, String> t2 = new Table<>();

        t2.setData(m);

        Assert.assertEquals(t2.size(), 2);
        AssertUtils.assertEqualsNoOrder(new ArrayList<>(t2.getRowKeys()), Arrays.asList("r1", "r2"));
        Assert.assertEquals(t2.getColKeys(), Arrays.asList("c1"));
    }

}
