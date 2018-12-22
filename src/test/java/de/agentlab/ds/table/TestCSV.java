package de.agentlab.ds.table;

import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class TestCSV extends BaseTableTest {

    public void testToCsv() {
        Table<String, String, String> t = new Table<>();

        t.put("r1", "c1", "v1");
        t.put("r1", "c2", "v2");
        t.put("r1", "c3", "v3");

        t.put("r2", "c1", "v1");
        t.put("r2", "c2", "v2");
        t.put("r2", "c3", "v3");

        Assert.assertEquals(";c1;c2;c3;\nr1;v1;v2;v3;\nr2;v1;v2;v3;\n", t.toCsv());
    }
}
