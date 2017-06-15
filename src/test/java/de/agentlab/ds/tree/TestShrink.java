package de.agentlab.ds.tree;

import de.agentlab.ds.Tree.Filter;

import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class TestShrink extends BaseTreeTest {

    @Test
    public void testShrink() {
        Assert.assertEquals(t.size(), 9);

        t.shrink(new Filter<TestItem>() {

            @Override
            public boolean accept(TestItem data) {
                return !(data.getValue().length() == 3);
            }
        });

        Assert.assertEquals(t.size(), 7);

        Assert.assertEquals(TreeTestUtils.toFlatString(t), "0|  0.1.0|  0.1.1|1|  1.1.0|  1.1.1|2|");

    }

    @Test
    public void testShrinkSubtree() {
        Assert.assertEquals(t.size(), 9);

        t.shrinkSubtree(i_1, new Filter<TestItem>() {

            @Override
            public boolean accept(TestItem data) {
                return !(data.getValue().length() == 3);
            }
        });

        Assert.assertEquals(t.size(), 8);

        Assert.assertEquals(TreeTestUtils.toFlatString(t), "0|  0.1|    0.1.0|    0.1.1|1|  1.1.0|  1.1.1|2|");

    }
}
