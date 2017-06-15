package de.agentlab.ds.tree;

import de.agentlab.ds.Tree.RetainingFilter;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class TestFilter extends BaseTreeTest {

    @Test
    public void testFilter() {
        Assert.assertEquals(t.size(), 9);

        t.filter(data -> data.getValue().length() > 3);

        Assert.assertEquals(TreeTestUtils.toFlatString(t), "0|  0.1|    0.1.0|    0.1.1|1|  1.1|    1.1.0|    1.1.1|");
    }

    @Test
    public void testFilterRetainSubtree() {
        Assert.assertEquals(t.size(), 9);

        t.filter(data -> data.getValue().equals("1.1"), true);

        Assert.assertEquals(TreeTestUtils.toFlatString(t), "1|  1.1|    1.1.0|    1.1.1|");
    }

    @Test
    public void testFilterEager() {
        Assert.assertEquals(t.size(), 9);

        t.filterEager(data -> data.getValue().length() < 3 || data.getValue().equals("1.1.1"));
        Assert.assertEquals(TreeTestUtils.toFlatString(t), "0|1|2|");
    }

    @Test
    public void testRetainingFilter() {
        Assert.assertEquals(t.size(), 9);

        t.filter(new RetainingFilter<TestItem>() {

            @Override
            public boolean accept(TestItem data) {
                return data.getValue().equals("0.1") || data.getValue().equals("1.1");
            }

            @Override
            public boolean retain(TestItem data) {
                return data.getValue().equals("1.1");
            }
        });
        Assert.assertEquals(TreeTestUtils.toFlatString(t), "0|  0.1|1|  1.1|    1.1.0|    1.1.1|");
    }

    @Test
    public void testGetPreorderList() {
        Assert.assertEquals(t.size(), 9);

        List<TestItem> l = t.getPreorderList(data -> data.getValue().startsWith("0"));

        List<TestItem> expected = new ArrayList<>();
        expected.add(i_0);
        expected.add(i_01);
        expected.add(i_010);
        expected.add(i_011);

        Assert.assertEquals(expected, l);
    }

}
