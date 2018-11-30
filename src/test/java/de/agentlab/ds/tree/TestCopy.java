package de.agentlab.ds.tree;

import de.agentlab.ds.Tree;
import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class TestCopy extends BaseTreeTest {

    @Test
    public void testCopy() {
        Assert.assertEquals(t.size(), 9);

        Tree<TestItem> copy = t.copy();

        Assert.assertEquals(copy.size(), 9);

        Assert.assertEquals(TreeTestUtils.toFlatString(copy), "0|  0.1|    0.1.0|    0.1.1|1|  1.1|    1.1.0|    1.1.1|2|");
    }

    @Test
    public void testCopyWithFilter() {
        Assert.assertEquals(t.size(), 9);

        Tree<TestItem> copy = t.copy(data -> data.getValue().length() < 3 || data.getValue().equals("1.1.1"));

        Assert.assertEquals(copy.size(), 5);

        Assert.assertEquals(TreeTestUtils.toFlatString(copy), "0|1|  1.1|    1.1.1|2|");
    }
}
