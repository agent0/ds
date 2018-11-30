package de.agentlab.ds.tree;

import de.agentlab.ds.Tree;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

@Test
public class TestAdd extends BaseTreeTest {

    @Test
    public void testAddRoot() {
        Tree<TestItem> t = new Tree<>();
        Assert.assertNotNull(t);

        TestItem item = new TestItem("1");
        t.add(item);

        Assert.assertEquals(t.contains(item), true);
        Assert.assertEquals(t.size(), 1);
    }

    @Test
    public void testAddChild() {
        Tree<TestItem> t = new Tree<>();
        Assert.assertNotNull(t);

        TestItem parent = new TestItem("1");
        t.add(parent);

        TestItem child = new TestItem("1.1");
        t.addChild(parent, child);

        Assert.assertEquals(t.getChildren(parent).size(), 1);
        Assert.assertEquals(t.getChildren(parent).get(0), child);
    }

    @Test
    public void testAddParent() {
        Assert.assertEquals(t.size(), 9);

        TestItem newParent = new TestItem("1.1-p");
        t.addParent(i_11, newParent);

        Assert.assertEquals(TreeTestUtils.toFlatString(t),
                "0|  0.1|    0.1.0|    0.1.1|1|  1.1-p|    1.1|      1.1.0|      1.1.1|2|");
    }

    @Test
    public void testAddParentToRootNode() {
        Assert.assertEquals(t.size(), 9);

        TestItem newParent = new TestItem("1-p");
        t.addParent(i_1, newParent);

        Assert.assertEquals(TreeTestUtils.toFlatString(t),
                "0|  0.1|    0.1.0|    0.1.1|2|1-p|  1|    1.1|      1.1.0|      1.1.1|");
    }

    @Test
    public void testAddSubree() {
        Assert.assertEquals(t.size(), 9);
        Assert.assertEquals(t2.size(), 7);

        t.addSubtree(i_2, t2);

        Assert.assertEquals(TreeTestUtils.toFlatString(t),
                "0|  0.1|    0.1.0|    0.1.1|1|  1.1|    1.1.0|    1.1.1|2|  3|    3.0|      3.0.0|      3.0.1|    3.1|      3.1.0|      3.1.1|");
    }

    @Test
    public void testAddSubreeAsRoot() {
        Assert.assertEquals(t.size(), 9);
        Assert.assertEquals(t2.size(), 7);

        t.addSubtree(t2);
        Assert.assertEquals(TreeTestUtils.toFlatString(t),
                "0|  0.1|    0.1.0|    0.1.1|1|  1.1|    1.1.0|    1.1.1|2|3|  3.0|    3.0.0|    3.0.1|  3.1|    3.1.0|    3.1.1|");
    }

    @Test
    public void testPush() {
        Assert.assertEquals(t.size(), 9);

        TestItem i = new TestItem("i");
        t.push(i);

        Assert.assertTrue(t.isRoot(i));
        Assert.assertTrue(t.isFirstChild(i));
    }

    @Test
    public void testAddBefore() {
        Assert.assertEquals(t.size(), 9);

        TestItem i_010a = new TestItem("0.1.0a");
        t.addChildBefore(i_011, i_010a);

        List<TestItem> expected = new ArrayList<>();
        expected.add(i_010);
        expected.add(i_010a);
        expected.add(i_011);

        List<TestItem> l = t.getChildren(i_01);
        Assert.assertEquals(l.size(), 3);
        Assert.assertEquals(l, expected);
    }

    @Test
    public void testAddAfter() {
        Assert.assertEquals(t.size(), 9);

        TestItem i_010a = new TestItem("0.1.0a");
        t.addChildAfter(i_010, i_010a);

        List<TestItem> expected = new ArrayList<>();
        expected.add(i_010);
        expected.add(i_010a);
        expected.add(i_011);

        List<TestItem> l = t.getChildren(i_01);
        Assert.assertEquals(l.size(), 3);
        Assert.assertEquals(l, expected);
    }

    @Test
    public void testAddAtFirst() {
        Assert.assertEquals(t.size(), 9);

        TestItem i_010a = new TestItem("0.1.0a");
        t.addChildAt(t.asList().get(1), 0, i_010a);

        List<TestItem> expected = new ArrayList<>();
        expected.add(i_010a);
        expected.add(i_010);
        expected.add(i_011);

        List<TestItem> l = t.getChildren(i_01);
        Assert.assertEquals(l.size(), 3);
        Assert.assertEquals(l, expected);
    }

    @Test
    public void testAddAtLast() {
        Assert.assertEquals(t.size(), 9);

        TestItem i_010a = new TestItem("0.1.0a");
        t.addChildAt(t.asList().get(1), 2, i_010a);

        List<TestItem> expected = new ArrayList<>();
        expected.add(i_010);
        expected.add(i_011);
        expected.add(i_010a);

        List<TestItem> l = t.getChildren(i_01);
        Assert.assertEquals(l.size(), 3);
        Assert.assertEquals(l, expected);
    }

}
