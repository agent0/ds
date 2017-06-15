package de.agentlab.ds.tree;

import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class TestReplace extends BaseTreeTest {

    @Test
    public void testReplace() {
        Assert.assertEquals(t.size(), 9);

        TestItem replacement = new TestItem("0.1'");

        t.replace(i_01, replacement);

        Assert.assertTrue(t.contains(replacement));
        Assert.assertFalse(t.contains(i_01));

        Assert.assertEquals(TreeTestUtils.toFlatString(t), "0|  0.1'|    0.1.0|    0.1.1|1|  1.1|    1.1.0|    1.1.1|2|");

    }
}
