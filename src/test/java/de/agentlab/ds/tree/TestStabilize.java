package de.agentlab.ds.tree;

import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class TestStabilize extends BaseTreeTest {

    @Test
    public void testRemoveLeafs() {
        Assert.assertEquals(t.size(), 9);

        t.stabilize(new Filter<TestItem>() {

            @Override
            public boolean accept(TestItem data) {
                return !t.isLeaf(data);
            }
        });

        Assert.assertEquals(t.size(), 0);
    }

    @Test
    public void testAlwaysFalse() {
        Assert.assertEquals(t.size(), 9);

        t.stabilize(new Filter<TestItem>() {

            @Override
            public boolean accept(TestItem data) {
                return false;
            }
        });

        Assert.assertEquals(t.size(), 0);
    }

    @Test
    public void testStabilize() {
        Assert.assertEquals(t.size(), 9);

        t.stabilize(new Filter<TestItem>() {

            @Override
            public boolean accept(TestItem data) {
                return !data.getValue().startsWith("1");
            }
        });

        Assert.assertEquals(TreeTestUtils.toFlatString(t), "0|  0.1|    0.1.0|    0.1.1|2|");
    }
}
