package de.agentlab.ds.tree;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class TestPath extends BaseTreeTest {

    @Test
    public void testGetPathLeaf() {
        Assert.assertEquals(t.size(), 9);

        List<TestItem> expected = new ArrayList<>();
        expected.add(i_0);
        expected.add(i_01);
        expected.add(i_010);

        List<TestItem> l = t.getPath(i_010);
        Assert.assertEquals(l.size(), 3);
        Assert.assertEquals(l, expected);
    }

    @Test
    public void testGetPathIntermediate() {
        Assert.assertEquals(t.size(), 9);

        List<TestItem> expected = new ArrayList<>();
        expected.add(i_0);
        expected.add(i_01);

        List<TestItem> l = t.getPath(i_01);
        Assert.assertEquals(l.size(), 2);
        Assert.assertEquals(l, expected);
    }

    @Test
    public void testGetPathRoot() {
        Assert.assertEquals(t.size(), 9);

        List<TestItem> expected = new ArrayList<>();
        expected.add(i_0);

        List<TestItem> l = t.getPath(i_0);
        Assert.assertEquals(l.size(), 1);
        Assert.assertEquals(l, expected);
    }

    @Test
    public void testGetPathTree() {
        Assert.assertEquals(t.size(), 9);

        List<TestItem> expected = new ArrayList<>();
        expected.add(i_011);

        Tree<TestItem> t2 = t.getPathTree(i_011);

        Assert.assertEquals(t2.size(), 3);
        Assert.assertTrue(t2.isRoot(i_0));
        Assert.assertTrue(t2.isChild(i_01, i_0));
        Assert.assertTrue(t2.isChild(i_011, i_01));
    }

}
