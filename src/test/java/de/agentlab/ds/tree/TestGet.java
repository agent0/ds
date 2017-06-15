package de.agentlab.ds.tree;

import java.util.Set;

import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class TestGet extends BaseTreeTest {

    @Test
    public void testGetAll() {
        Assert.assertEquals(t.size(), 9);

        Set<TestItem> all = t.getAll();

        Assert.assertEquals(all.size(), t.size());
        for (TestItem testItem : t.asList()) {
            Assert.assertTrue(all.contains(testItem));
        }

    }

}
