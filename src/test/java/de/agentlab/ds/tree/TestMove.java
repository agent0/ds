package de.agentlab.ds.tree;

import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class TestMove extends BaseTreeTest {

    @Test
    public void testMove() {
        Assert.assertEquals(t.size(), 9);

        t.move(i_01, i_2);
        Assert.assertEquals(TreeTestUtils.toFlatString(t), "0|1|  1.1|    1.1.0|    1.1.1|2|  0.1|    0.1.0|    0.1.1|");
    }

    @Test
    public void testMoveRoot() {
        Assert.assertEquals(t.size(), 9);

        t.move(i_2, i_0);

        Assert.assertEquals(TreeTestUtils.toFlatString(t), "0|  0.1|    0.1.0|    0.1.1|  2|1|  1.1|    1.1.0|    1.1.1|");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testMoveToDescendant() {
        Assert.assertEquals(t.size(), 9);

        t.move(i_0, i_01);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testMoveToSelf() {
        Assert.assertEquals(t.size(), 9);

        t.move(i_01, i_01);
    }

}
