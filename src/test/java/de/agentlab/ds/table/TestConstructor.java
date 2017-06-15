package de.agentlab.ds.table;

import de.agentlab.ds.Table;

import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class TestConstructor extends BaseTableTest {

    public void testConstructor() {
        Table<String, String, String> t = new Table<>();

        t.put("r1", "c1", "v1");
        t.put("r2", "c1", "v1");

        Table<String, String, String> t2 = new Table<>(t);

        Assert.assertEquals(t2.size(), t.size());
        Assert.assertEquals(t2.getRowKeys(), t.getRowKeys());
        Assert.assertEquals(t2.getColKeys(), t.getColKeys());
    }
}
