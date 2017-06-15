package de.agentlab.ds.tree;

import de.agentlab.ds.Tree;
import de.agentlab.ds.Tree.Mapper;

import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class TestMap extends BaseTreeTest {

    @Test
    public void testMap() {
        Assert.assertEquals(t.size(), 9);

        Tree<TestItem> mapped = t.map(new Mapper<TestItem, TestItem>() {

            @Override
            public TestItem map(TestItem data) {
                return new TestItem("c_" + data.getValue());
            }
        });
        Assert.assertEquals(TreeTestUtils.toFlatString(t), "0|  0.1|    0.1.0|    0.1.1|1|  1.1|    1.1.0|    1.1.1|2|");
        Assert.assertEquals(TreeTestUtils.toFlatString(mapped), "c_0|  c_0.1|    c_0.1.0|    c_0.1.1|c_1|  c_1.1|    c_1.1.0|    c_1.1.1|c_2|");

    }
}
