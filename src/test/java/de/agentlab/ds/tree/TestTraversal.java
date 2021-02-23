package de.agentlab.ds.tree;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

@Test
public class TestTraversal extends BaseTreeTest {

    @Test
    public void testPreorder() {
        Assert.assertEquals(t.size(), 9);

        List<TestItem> expected = new ArrayList<>();
        expected.add(i_0);
        expected.add(i_01);
        expected.add(i_010);
        expected.add(i_011);
        expected.add(i_1);
        expected.add(i_11);
        expected.add(i_110);
        expected.add(i_111);
        expected.add(i_2);

        List<TestItem> l = t.getPreorderList();
        Assert.assertEquals(l.size(), 9);
        Assert.assertEquals(l, expected);
    }

    @Test
    public void testPreorderForElement() {
        Assert.assertEquals(t.size(), 9);

        List<TestItem> expected = new ArrayList<>();
        expected.add(i_1);
        expected.add(i_11);
        expected.add(i_110);
        expected.add(i_111);

        List<TestItem> l = t.getPreorderList(i_1);
        Assert.assertEquals(l.size(), 4);
        Assert.assertEquals(l, expected);
    }

    @Test
    public void testPostorder() {
        Assert.assertEquals(t.size(), 9);

        List<TestItem> expected = new ArrayList<>();
        expected.add(i_010);
        expected.add(i_011);
        expected.add(i_01);
        expected.add(i_0);
        expected.add(i_110);
        expected.add(i_111);
        expected.add(i_11);
        expected.add(i_1);
        expected.add(i_2);

        List<TestItem> l = t.getPostorderList();

        Assert.assertEquals(l.size(), 9);
        Assert.assertEquals(l, expected);
    }

    @Test
    public void testPostorderForElement() {
        Assert.assertEquals(t.size(), 9);

        List<TestItem> expected = new ArrayList<>();
        expected.add(i_110);
        expected.add(i_111);
        expected.add(i_11);
        expected.add(i_1);

        List<TestItem> l = t.getPostorderList(i_1);

        Assert.assertEquals(l.size(), 4);
        Assert.assertEquals(l, expected);
    }

    @Test
    public void testBreadthFirst() {
        Assert.assertEquals(t.size(), 9);

        List<TestItem> expected = new ArrayList<>();
        expected.add(i_0);
        expected.add(i_1);
        expected.add(i_2);
        expected.add(i_01);
        expected.add(i_11);
        expected.add(i_010);
        expected.add(i_011);
        expected.add(i_110);
        expected.add(i_111);

        List<TestItem> l = t.getBreadthFirstList();
        Assert.assertEquals(l.size(), 9);
        Assert.assertEquals(l, expected);
    }
}
