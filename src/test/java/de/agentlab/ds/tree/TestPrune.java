package de.agentlab.ds.tree;

import de.agentlab.ds.Tree;

import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class TestPrune extends BaseTreeTest {

    @Test
    public void testPrune() {
        Assert.assertEquals(t.size(), 9);

        t.prune(i_11);

        Assert.assertEquals(t.size(), 4);

        Assert.assertEquals(TreeTestUtils.toFlatString(t), "1|  1.1|    1.1.0|    1.1.1|");
    }

    @Test
    public void testPruneCopy() {
        Assert.assertEquals(t.size(), 9);

        Tree<TestItem> t2 = t.pruneCopy(i_11);

        Assert.assertEquals(t.size(), 9);
        Assert.assertEquals(t2.size(), 4);

        Assert.assertEquals(TreeTestUtils.toFlatString(t2), "1|  1.1|    1.1.0|    1.1.1|");
    }
}
