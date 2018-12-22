package de.agentlab.ds.tree;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class TestUtils extends BaseTreeTest {

    @Test
    public void testAsTree() {

        Tree<TestItem> t = Tree.asTree(i_0, i_01, i_11);

        List<TestItem> expected = new ArrayList<>();
        expected.add(i_0);
        expected.add(i_01);
        expected.add(i_11);

        Assert.assertEquals(t.size(), 3);
        Assert.assertEquals(t.getRoots(), expected);
    }

    @Test
    public void testAsDegeneratedTree() {

        Tree<TestItem> t = Tree.asDegeneratedTree(i_0, i_01, i_11);

        Assert.assertEquals(t.size(), 3);
        Assert.assertNull(t.getParent(i_0));
        Assert.assertEquals(t.getParent(i_01), i_0);
        Assert.assertEquals(t.getParent(i_11), i_01);

    }

}
