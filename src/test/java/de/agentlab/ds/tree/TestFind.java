package de.agentlab.ds.tree;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class TestFind extends BaseTreeTest {

    @Test
    public void testFind() {
        Assert.assertEquals(t.size(), 9);

        TestItem found = t.find(data -> data.getValue().equals("0.1"));

        Assert.assertEquals(found, i_01);
    }

    @Test
    public void testNotFound() {
        Assert.assertEquals(t.size(), 9);

        TestItem found = t.find(data -> data.getValue().equals("abc"));

        Assert.assertNull(found);
    }

    @Test
    public void testFindAll() {
        Assert.assertEquals(t.size(), 9);

        List<TestItem> found = t.findAll(data -> data.getValue().contains("0.1"));

        List<TestItem> expected = new ArrayList<>();
        expected.add(i_01);
        expected.add(i_010);
        expected.add(i_011);

        Assert.assertEquals(found, expected);
    }

    @Test
    public void testFindAllNotFound() {
        Assert.assertEquals(t.size(), 9);

        List<TestItem> found = t.findAll(data -> data.getValue().contains("abc"));

        Assert.assertEquals(found.size(), 0);
    }

    @Test
    public void testClosest() {
        Assert.assertEquals(t.size(), 9);

        TestItem found = t.closest(i_011, data -> data.getValue().equals("0"));

        Assert.assertEquals(found, i_0);
    }

    @Test
    public void testNoClosest() {
        Assert.assertEquals(t.size(), 9);

        TestItem found = t.closest(i_011, data -> data.getValue().equals("1"));

        Assert.assertNull(found);
    }

    @Test
    public void testLeastCommonSubsumer() {
        Assert.assertEquals(t.size(), 9);
        TestItem i_1111 = new TestItem("1.1.1.1");

        t.addChild(i_111, i_1111);

        TestItem lcs = t.leastCommonSubsumer(i_110, i_1111);

        Assert.assertEquals(lcs, i_11);
    }

    @Test
    public void testNoLeastCommonSubsumer() {
        Assert.assertEquals(t.size(), 9);

        TestItem lcs = t.leastCommonSubsumer(i_110, i_2);

        Assert.assertNull(lcs);
    }

}
