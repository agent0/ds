package de.agentlab.ds.table;

import de.agentlab.ds.Table;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class TestKeys extends BaseTableTest {

    public void testGetRowColKeys() {
        Table<String, String, String> t = new Table<>();

        t.put("r1", "c1", "v1");
        t.put("r1", "c2", "v2");
        t.put("r1", "c3", "v3");

        t.put("r2", "c1", "v1");
        t.put("r2", "c2", "v2");
        t.put("r2", "c3", "v3");

        Assert.assertEquals(t.size(), 6);

        Assert.assertEquals(t.getRowKeys(), new HashSet<>(Arrays.asList("r1", "r2")));
        Assert.assertEquals(t.getColKeys(), new HashSet<>(Arrays.asList("c1", "c2", "c3")));

        Set<Table.KeyPair<String, String>> keyPairs = t.getKeyPairs();

        Assert.assertEquals(t.getKeyPairs(), new HashSet<>(Arrays.asList(
                new Table.KeyPair<>("r1", "c1"),
                new Table.KeyPair<>("r1", "c2"),
                new Table.KeyPair<>("r1", "c3"),
                new Table.KeyPair<>("r2", "c1"),
                new Table.KeyPair<>("r2", "c2"),
                new Table.KeyPair<>("r2", "c3"))));
    }

    public void testContainsRowKey() {
        Table<String, String, String> t = new Table<>();

        t.put("r1", "c1", "v1");

        t.put("r2", "c1", "v2");

        Assert.assertEquals(t.containsRowKey("r1"), true);
        Assert.assertEquals(t.containsRowKey("r2"), true);

        t.remove("r1", "c1");

        Assert.assertEquals(t.containsRowKey("r1"), false);
        Assert.assertEquals(t.containsRowKey("r2"), true);
    }
}
