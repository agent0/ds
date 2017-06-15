package de.agentlab.ds.tree;

import de.agentlab.ds.Tree;

import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class TestRemove extends BaseTreeTest {

    @Test
    public void testClear() {
        Assert.assertEquals(t.size(), 9);

        t.clear();

        Assert.assertEquals(t.size(), 0);
    }

    @Test
    public void testRemoveLeaf() {
        Assert.assertEquals(t.size(), 9);

        Assert.assertTrue(t.contains(i_011));

        boolean removed = t.remove(i_011);
        Assert.assertTrue(removed);

        Assert.assertEquals(t.size(), 8);
        Assert.assertFalse(t.contains(i_011));
    }

    @Test
    public void testRemoveIntermediate() {
        Assert.assertEquals(t.size(), 9);

        Assert.assertTrue(t.contains(i_01));

        boolean removed = t.remove(i_01);
        Assert.assertTrue(removed);

        Assert.assertEquals(t.size(), 6);
        Assert.assertFalse(t.contains(i_010));
        Assert.assertFalse(t.contains(i_011));
    }

    @Test
    public void testRemoveRoot() {
        Assert.assertEquals(t.size(), 9);

        Assert.assertTrue(t.contains(i_0));

        boolean removed = t.remove(i_0);
        Assert.assertTrue(removed);

        Assert.assertEquals(t.size(), 5);
        Assert.assertFalse(t.contains(i_0));
        Assert.assertFalse(t.contains(i_01));
        Assert.assertFalse(t.contains(i_010));
        Assert.assertFalse(t.contains(i_011));
    }

    @Test
    public void testRemoveNonExistent() {
        Assert.assertEquals(t.size(), 9);

        boolean removed = t.remove(new TestItem("?"));
        Assert.assertFalse(removed);
    }

    @Test
    public void testRemoveByFilter() {
        Assert.assertEquals(t.size(), 9);

        Assert.assertTrue(t.contains(i_0));

        boolean removed = t.remove(new Tree.Filter<TestItem>() {

            @Override
            public boolean accept(TestItem item) {
                return item.getValue().contains("1.0");
            }
        });
        Assert.assertTrue(removed);
        Assert.assertEquals(t.size(), 7);
        Assert.assertFalse(t.contains(i_010));
        Assert.assertFalse(t.contains(i_110));
    }

    @Test
    public void testRemoveByFilterAllMatch() {
        Assert.assertEquals(t.size(), 9);

        Assert.assertTrue(t.contains(i_0));

        boolean removed = t.remove(new Tree.Filter<TestItem>() {

            @Override
            public boolean accept(TestItem item) {
                return true;
            }
        });
        Assert.assertTrue(removed);
        Assert.assertEquals(t.size(), 0);
    }

    @Test
    public void testRemoveByFilterNoMatch() {
        Assert.assertEquals(t.size(), 9);

        Assert.assertTrue(t.contains(i_0));

        boolean removed = t.remove(new Tree.Filter<TestItem>() {

            @Override
            public boolean accept(TestItem item) {
                return false;
            }
        });
        Assert.assertFalse(removed);
        Assert.assertEquals(t.size(), 9);
    }

    @Test
    public void testPull() {
        Assert.assertEquals(t.size(), 9);

        boolean pulled = t.pull(i_11);

        Assert.assertTrue(pulled);
        Assert.assertEquals(t.size(), 8);
        Assert.assertFalse(t.contains(i_11));
        Assert.assertTrue(t.isChild(i_110, i_1));
        Assert.assertTrue(t.isChild(i_111, i_1));
    }

    @Test
    public void testPullRoot() {
        Assert.assertEquals(t.size(), 9);

        boolean pulled = t.pull(i_1);

        Assert.assertTrue(pulled);
        Assert.assertEquals(t.size(), 8);
        Assert.assertFalse(t.contains(i_1));
        Assert.assertTrue(t.isRoot(i_11));
        Assert.assertEquals(TreeTestUtils.toFlatString(t), "0|  0.1|    0.1.0|    0.1.1|1.1|  1.1.0|  1.1.1|2|");
    }

    @Test
    public void testPullLeaf() {
        Assert.assertEquals(t.size(), 9);

        boolean pulled = t.pull(i_011);

        Assert.assertTrue(pulled);
        Assert.assertEquals(t.size(), 8);
        Assert.assertFalse(t.contains(i_011));
    }

    @Test
    public void testPullNull() {
        Assert.assertEquals(t.size(), 9);

        boolean pulled = t.pull(null);

        Assert.assertFalse(pulled);
    }

}
