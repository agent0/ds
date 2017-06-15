package de.agentlab.ds.tree;

import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class TestPredicates extends BaseTreeTest {

    @Test
    public void testHasChildren() {
        Assert.assertEquals(t.size(), 9);

        Assert.assertTrue(t.hasChildren(i_0));
        Assert.assertTrue(t.hasChildren(i_01));
        Assert.assertFalse(t.hasChildren(i_010));
    }

    @Test
    public void testIsAncestor() {
        Assert.assertEquals(t.size(), 9);

        Assert.assertTrue(t.isAncestor(i_0, i_01));
        Assert.assertTrue(t.isAncestor(i_0, i_010));

        Assert.assertFalse(t.isAncestor(i_0, i_0));
        Assert.assertFalse(t.isAncestor(i_0, i_110));
    }

    @Test
    public void testIsDescendant() {
        Assert.assertEquals(t.size(), 9);

        Assert.assertTrue(t.isDescendant(i_01, i_0));
        Assert.assertTrue(t.isDescendant(i_010, i_0));

        Assert.assertFalse(t.isDescendant(i_0, i_0));
        Assert.assertFalse(t.isDescendant(i_110, i_0));
    }

    @Test
    public void testIsChild() {
        Assert.assertEquals(t.size(), 9);

        Assert.assertTrue(t.isChild(i_01, i_0));
        Assert.assertTrue(t.isChild(i_010, i_01));
        Assert.assertFalse(t.isChild(i_011, i_0));
    }

    @Test
    public void testIsFirstChild() {
        Assert.assertEquals(t.size(), 9);

        Assert.assertTrue(t.isFirstChild(i_01));
        Assert.assertTrue(t.isFirstChild(i_010));
        Assert.assertFalse(t.isFirstChild(i_011));

        Assert.assertTrue(t.isFirstChild(i_0));
    }

    @Test
    public void testIsLastChild() {
        Assert.assertEquals(t.size(), 9);

        Assert.assertTrue(t.isLastChild(i_01));
        Assert.assertFalse(t.isLastChild(i_010));
        Assert.assertTrue(t.isLastChild(i_011));

        Assert.assertTrue(t.isLastChild(i_2));
    }

    @Test
    public void testIsRoot() {
        Assert.assertEquals(t.size(), 9);

        Assert.assertTrue(t.isRoot(i_0));
        Assert.assertFalse(t.isRoot(i_01));
    }

    @Test
    public void testContains() {
        Assert.assertEquals(t.size(), 9);

        Assert.assertTrue(t.contains(i_0));
        Assert.assertFalse(t.contains(new TestItem("?")));
    }
}
