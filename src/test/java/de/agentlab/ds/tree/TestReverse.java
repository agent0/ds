package de.agentlab.ds.tree;

import de.agentlab.ds.Tree;
import de.agentlab.ds.Tree.ItemWrapper;

import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class TestReverse extends BaseTreeTest {

    @Test
    public void testReverse() {
        Assert.assertEquals(t.size(), 9);

        Tree<ItemWrapper<TestItem>> reversed = t.reverse();

        Assert.assertEquals(reversed.size(), 13);
        Assert.assertEquals(TreeTestUtils.toFlatString(reversed), "0.1.0|  0.1|    0|0.1.1|  0.1|    0|1.1.0|  1.1|    1|1.1.1|  1.1|    1|2|");

    }

}
