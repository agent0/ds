package de.agentlab.ds.tree;

import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class TestVersion extends BaseTreeTest {

    @Test
    public void testVersion() {
        Assert.assertEquals(t.size(), 9);

        Assert.assertEquals(t.getVersion(), 9);

        t.remove(i_01);
        Assert.assertEquals(t.getVersion(), 10);

        t.add(i_01);
        Assert.assertEquals(t.getVersion(), 11);
    }
}
