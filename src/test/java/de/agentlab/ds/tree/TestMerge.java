package de.agentlab.ds.tree;

import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class TestMerge extends BaseTreeTest {

    @Test
    public void testMerge_IndependantTrees() {
        Assert.assertEquals(t.size(), 9);

        Tree<TestItem> toMerge = new Tree<>();
        TreeTestUtils.populate(toMerge, 2, 2);

        t.merge(toMerge);

        Assert.assertEquals(TreeTestUtils.toFlatString(t), "0|  0.1|    0.1.0|    0.1.1|1|  1.1|    1.1.0|    1.1.1|2|0|  0.0|  0.1|1|  1.0|  1.1|");
    }

    @Test
    public void testMerge_IndenticalTrees() {
        Assert.assertEquals(t.size(), 9);

        Tree<TestItem> toMerge = t.copy();

        t.merge(toMerge);

        System.out.println(TreeTestUtils.toFlatString(t));
        Assert.assertEquals(TreeTestUtils.toFlatString(t), "0|  0.1|    0.1.0|    0.1.1|1|  1.1|    1.1.0|    1.1.1|2|");
    }

    @Test
    public void testMerge_OverlappingTrees() {
        Assert.assertEquals(t.size(), 9);

        Tree<TestItem> toMerge = new Tree<>();
        toMerge.add(i_0);
        toMerge.addChild(i_0, i_01);
        toMerge.addChild(i_01, new TestItem("0.1.2"));

        toMerge.add(i_1);
        toMerge.addChild(i_1, new TestItem("1.2"));

        toMerge.add(new TestItem("3"));

        t.merge(toMerge);

        Assert.assertEquals(TreeTestUtils.toFlatString(t), "0|  0.1|    0.1.0|    0.1.1|    0.1.2|1|  1.1|    1.1.0|    1.1.1|  1.2|2|3|");
    }
}
