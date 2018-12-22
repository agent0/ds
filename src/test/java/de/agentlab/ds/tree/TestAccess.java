package de.agentlab.ds.tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class TestAccess extends BaseTreeTest {

    @Test
    public void testIndexOf() {
        Assert.assertEquals(t.size(), 9);

        Assert.assertEquals(t.indexOf(i_0), 0);
        Assert.assertEquals(t.indexOf(i_11), 5);
        Assert.assertEquals(t.indexOf(i_2), 8);
    }

    @Test
    public void testGetRoots() {
        Assert.assertEquals(t.size(), 9);

        List<TestItem> expected = new ArrayList<>();
        expected.add(i_0);
        expected.add(i_1);
        expected.add(i_2);

        List<TestItem> l = t.getRoots();
        Assert.assertEquals(l.size(), 3);
        Assert.assertEquals(l, expected);
    }

    @Test
    public void testGetLeafs() {
        Assert.assertEquals(t.size(), 9);

        List<TestItem> expected = new ArrayList<>();
        expected.add(i_010);
        expected.add(i_011);
        expected.add(i_110);
        expected.add(i_111);
        expected.add(i_2);

        List<TestItem> l = t.getLeafs();
        Assert.assertEquals(l.size(), 5);
        Assert.assertEquals(l, expected);
    }

    @Test
    public void testGetParent() {
        Assert.assertEquals(t.size(), 9);

        Assert.assertEquals(t.getParent(i_011), i_01);
    }

    @Test
    public void testNoParent() {
        Assert.assertEquals(t.size(), 9);

        Assert.assertEquals(t.getParent(i_0), null);
    }

    @Test
    public void testGetChildren() {
        Assert.assertEquals(t.size(), 9);

        List<TestItem> expected = new ArrayList<>();
        expected.add(i_010);
        expected.add(i_011);

        List<TestItem> l = t.getChildren(i_01);
        Assert.assertEquals(l.size(), 2);
        Assert.assertEquals(l, expected);
    }

    @Test
    public void testGetDescendants() {
        Assert.assertEquals(t.size(), 9);

        List<TestItem> expected = new ArrayList<>();
        expected.add(i_01);
        expected.add(i_010);
        expected.add(i_011);

        List<TestItem> l = t.getDescendants(i_0);
        Assert.assertEquals(l.size(), 3);
        Assert.assertEquals(l, expected);
    }

    @Test
    public void testGetSiblings() {
        Assert.assertEquals(t.size(), 9);

        List<TestItem> expected = new ArrayList<>();
        expected.add(i_011);

        List<TestItem> l = t.getSiblings(i_010);
        Assert.assertEquals(l.size(), 1);
        Assert.assertEquals(l, expected);
    }

    @Test
    public void testGetRootSiblings() {
        Assert.assertEquals(t.size(), 9);

        List<TestItem> expected = new ArrayList<>();
        expected.add(i_1);
        expected.add(i_2);

        List<TestItem> l = t.getSiblings(i_0);
        Assert.assertEquals(l.size(), 2);
        Assert.assertEquals(l, expected);
    }

    @Test
    public void testGetSiblingsIncludingSelf() {
        Assert.assertEquals(t.size(), 9);

        List<TestItem> expected = new ArrayList<>();
        expected.add(i_010);
        expected.add(i_011);

        List<TestItem> l = t.getSiblings(i_010, true);
        Assert.assertEquals(l.size(), 2);
        Assert.assertEquals(l, expected);
    }

    @Test
    public void testGetLevel_1() {
        Assert.assertEquals(t.size(), 9);

        List<TestItem> expected = new ArrayList<>();
        expected.add(i_01);
        expected.add(i_11);

        List<TestItem> l = t.getLevel(1);
        Assert.assertEquals(l.size(), 2);
        Assert.assertEquals(l, expected);
    }

    @Test
    public void testGetLevel_2() {
        Assert.assertEquals(t.size(), 9);

        List<TestItem> expected = new ArrayList<>();
        expected.add(i_010);
        expected.add(i_011);
        expected.add(i_110);
        expected.add(i_111);

        List<TestItem> l = t.getLevel(2);
        Assert.assertEquals(l.size(), 4);
        Assert.assertEquals(l, expected);
    }

    @Test
    public void testGetSubtreeFromRoot() {
        Assert.assertEquals(t.size(), 9);

        Tree<TestItem> t2 = t.getSubtree(i_0);
        Assert.assertEquals(t2.size(), 4);
        Assert.assertEquals(TreeTestUtils.toFlatString(t2), "0|  0.1|    0.1.0|    0.1.1|");
    }

    @Test
    public void testGetSubtreeFromIntermediate() {
        Assert.assertEquals(t.size(), 9);

        Tree<TestItem> t2 = t.getSubtree(i_01);
        Assert.assertEquals(t2.size(), 3);
        Assert.assertEquals(TreeTestUtils.toFlatString(t2), "0.1|  0.1.0|  0.1.1|");
    }

    @Test
    public void testGetBranches() {
        Assert.assertEquals(t.size(), 9);

        List<List<TestItem>> branches = t.getBranches();

        Assert.assertEquals(branches.size(), 5);

        Assert.assertEquals(branches.get(0), new ArrayList<>(Arrays.asList(new TestItem[]{i_0, i_01, i_010})));
        Assert.assertEquals(branches.get(1), new ArrayList<>(Arrays.asList(new TestItem[]{i_0, i_01, i_011})));
        Assert.assertEquals(branches.get(2), new ArrayList<>(Arrays.asList(new TestItem[]{i_1, i_11, i_110})));
        Assert.assertEquals(branches.get(3), new ArrayList<>(Arrays.asList(new TestItem[]{i_1, i_11, i_111})));
        Assert.assertEquals(branches.get(4), new ArrayList<>(Arrays.asList(new TestItem[]{i_2})));
    }

    @Test
    public void testGetBranchesIterator() {
        Assert.assertEquals(t.size(), 9);

        Iterator<List<TestItem>> branchesIterator = t.getBranchesIterator();

        Assert.assertEquals(branchesIterator.next(), new ArrayList<>(Arrays.asList(new TestItem[]{i_0, i_01, i_010})));
        Assert.assertEquals(branchesIterator.next(), new ArrayList<>(Arrays.asList(new TestItem[]{i_0, i_01, i_011})));
        Assert.assertEquals(branchesIterator.next(), new ArrayList<>(Arrays.asList(new TestItem[]{i_1, i_11, i_110})));
        Assert.assertEquals(branchesIterator.next(), new ArrayList<>(Arrays.asList(new TestItem[]{i_1, i_11, i_111})));
        Assert.assertEquals(branchesIterator.next(), new ArrayList<>(Arrays.asList(new TestItem[]{i_2})));

        try {
            branchesIterator.next();
            Assert.fail();
        } catch (IndexOutOfBoundsException e) {
            // intetionally empty
        }
    }

}
