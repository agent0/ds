package de.agentlab.ds.tree;

import java.util.Comparator;

import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class TestSort extends BaseTreeTest {

    @Test
    public void testSort() {
        Assert.assertEquals(t.size(), 9);

        t.sort(new Comparator<TestItem>() {

            @Override
            public int compare(TestItem i1, TestItem i2) {
                return i1.getValue().compareTo(i2.getValue());
            }
        });

        Assert.assertEquals(TreeTestUtils.toFlatString(t), "0|  0.1|    0.1.0|    0.1.1|1|  1.1|    1.1.0|    1.1.1|2|");

    }

    @Test
    public void testSortReversed() {
        Assert.assertEquals(t.size(), 9);

        t.sort(new Comparator<TestItem>() {

            @Override
            public int compare(TestItem i1, TestItem i2) {
                return -1 * i1.getValue().compareTo(i2.getValue());
            }
        });

        Assert.assertEquals(TreeTestUtils.toFlatString(t), "2|1|  1.1|    1.1.1|    1.1.0|0|  0.1|    0.1.1|    0.1.0|");

    }
}
