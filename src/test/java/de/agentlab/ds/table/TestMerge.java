package de.agentlab.ds.table;

import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class TestMerge extends BaseTableTest {

    @Test
    public void testMerge() {
        Table<String, String, String> t = new Table<>();

        t.put("r1", "c1", "v");
        t.put("r1", "c2", "v");
        t.put("r1", "c3", "v");
        t.put("r2", "c2", "v");

        Table<String, String, String> t2 = new Table<>();

        t2.put("r2", "c1", "v");
        t2.put("r2", "c2", "v");
        t2.put("r2", "c3", "v");
        t2.put("r3", "c2", "v");

        t.merge(t2);

        Assert.assertEquals(t.toCsv(), ";c1;c2;c3;\nr1;v;v;v;\nr2;v;v;v;\nr3;;v;;\n");
    }
}
