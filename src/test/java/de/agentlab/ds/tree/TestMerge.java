package de.agentlab.ds.tree;

import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class TestMerge extends BaseTreeTest {

    @Test
    public void testMerge() {
        Assert.assertEquals(t.size(), 9);

        Tree<TestItem> toMerge = new Tree<>();
        TreeTestUtils.populate(toMerge, 2, 2);

        t.merge(toMerge);
        System.out.println(t);
    }
}
