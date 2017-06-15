package de.agentlab.ds.tree;

import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class TestLimit extends BaseTreeTest {

    @Test
    public void testLimit() {
        Assert.assertEquals(t.size(), 9);

        t.limit(5);
        Assert.assertEquals(t.size(), 5);
        Assert.assertEquals(TreeTestUtils.toFlatString(t), "0|  0.1|    0.1.0|    0.1.1|1|");
    }
}
