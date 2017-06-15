package de.agentlab.ds.tree;

import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class TestExceptions extends BaseTreeTest {

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testGetParentUnknownItem() {
        Assert.assertEquals(t.size(), 9);

        t.getParent(new TestItem("?"));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testGetChildrenUnknownItem() {
        Assert.assertEquals(t.size(), 9);

        t.getChildren(new TestItem("?"));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testHasChildrenUnknownItem() {
        Assert.assertEquals(t.size(), 9);

        t.hasChildren(new TestItem("?"));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testGetSiblingsUnknownItem() {
        Assert.assertEquals(t.size(), 9);

        t.getSiblings(new TestItem("?"));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testIsFirstChildUnknownItem() {
        Assert.assertEquals(t.size(), 9);

        t.isFirstChild(new TestItem("?"));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testIsLastChildUnknownItem() {
        Assert.assertEquals(t.size(), 9);

        t.isLastChild(new TestItem("?"));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testGetDepthChildUnknownItem() {
        Assert.assertEquals(t.size(), 9);

        t.getDepth(new TestItem("?"));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testGetPathUnknownItem() {
        Assert.assertEquals(t.size(), 9);

        t.getPath(new TestItem("?"));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAddDuplicate() {
        Assert.assertEquals(t.size(), 9);

        t.add(i_0);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testPushDuplicate() {
        Assert.assertEquals(t.size(), 9);

        t.push(i_0);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAddChildSelf() {
        Assert.assertEquals(t.size(), 9);

        t.addChild(i_0, i_0);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAddChildDuplicate() {
        Assert.assertEquals(t.size(), 9);

        t.addChild(i_0, i_011);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAddChildUnknownParent() {
        Assert.assertEquals(t.size(), 9);

        t.addChild(new TestItem("?"), new TestItem("!"));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testInsertBeforeDuplicate() {
        Assert.assertEquals(t.size(), 9);

        t.addChildBefore(i_0, i_011);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testInsertBeforeUnknownSibling() {
        Assert.assertEquals(t.size(), 9);

        t.addChildBefore(new TestItem("?"), new TestItem("!"));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testInsertAfterDuplicate() {
        Assert.assertEquals(t.size(), 9);

        t.addChildAfter(i_0, i_011);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testInsertAfterUnknownSibling() {
        Assert.assertEquals(t.size(), 9);

        t.addChildAfter(new TestItem("?"), new TestItem("!"));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testMoveUnknownElement() {
        Assert.assertEquals(t.size(), 9);

        t.move(new TestItem("?"), i_011);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testMoveUnknownTarget() {
        Assert.assertEquals(t.size(), 9);

        t.move(i_01, new TestItem("!"));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testVisitUnknownItem() {
        Assert.assertEquals(t.size(), 9);

        t.visit(new TestItem("!"), null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testVisitDescendantsUnknownItem() {
        Assert.assertEquals(t.size(), 9);

        t.visitDescendants(new TestItem("!"), null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testGetPreorderListUnknownItem() {
        Assert.assertEquals(t.size(), 9);

        t.getPreorderList(new TestItem("!"));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testMapUnknownItem() {
        Assert.assertEquals(t.size(), 9);

        t.map(new TestItem("!"), null);
    }


    @Test(expectedExceptions = IndexOutOfBoundsException.class)
    public void testInsertIndexNegative() {
        Assert.assertEquals(t.size(), 9);

        t.addChildAt(t.asList().get(1), -1, new TestItem("0.1.0a"));
    }

    @Test(expectedExceptions = IndexOutOfBoundsException.class)
    public void testInsertIndexTooHigh() {
        Assert.assertEquals(t.size(), 9);

        t.addChildAt(t.asList().get(1), 3, new TestItem("0.1.0a"));
    }

}
