package de.agentlab.ds.table;

import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class TestKeyPair {

    @Test
    public void test() {
        KeyPair<String, Long> k = new KeyPair<>("r", 1L);

        Assert.assertEquals(k.getRowKey(), "r");
        Assert.assertEquals((long) k.getColKey(), 1L);
        Assert.assertEquals(k.toString(), "<r, 1>");

        KeyPair<String, Long> k2 = new KeyPair<>("r", 1L);

        Assert.assertEquals(k, k2);
    }
}
