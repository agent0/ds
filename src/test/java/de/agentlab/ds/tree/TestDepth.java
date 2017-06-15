package de.agentlab.ds.tree;

import de.agentlab.ds.Tree;

import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class TestDepth extends BaseTreeTest {

    @Test
    public void testGetNodeDepth() {
        Assert.assertEquals(t.size(), 9);

        Assert.assertEquals(t.getDepth(i_0), 0);
        Assert.assertEquals(t.getDepth(i_01), 1);
        Assert.assertEquals(t.getDepth(i_010), 2);
    }

    @Test
    public void testGetDepth() {
        Assert.assertEquals(t.size(), 9);
        Assert.assertEquals(t.getDepth(), 2);
    }

    @Test
    public void testGetDepthEmptyTree() {
        Assert.assertEquals(new Tree<TestItem>().getDepth(), 0);
    }

    @Test
    public void testGetDepthEmptySingle() {
        Tree<TestItem> t = new Tree<>();
        t.add(new TestItem("1"));

        Assert.assertEquals(t.getDepth(), 0);
    }
}
