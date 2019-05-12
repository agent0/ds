package de.agentlab.ds.table;

import org.testng.annotations.Test;

@Test
public class TestPrettyPrint {

    @Test
    public void testToPrettyTable() {
        Table<String, String, String> t = new Table<>();

        t.put("r111", "c1", "v1 v1 v1");
        t.put("r111", "c2", "v2");
        t.put("r111", "c3", "v3");

        t.put("r2", "c1", "v1");
        t.put("r2", "c2", "v2 v2 v2 v2 v2");
        t.put("r2", "c3", "v3 v3");

        t.put("r3", "c4");

        System.out.println(t.toPrettyTable());
//        Assert.assertEquals(t.toPrettyTable().length(), 245);
    }
}
