package de.agentlab.ds.common;

import de.agentlab.ds.tree.TestItem;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static de.agentlab.ds.common.ListUtils.groupBy;

@Test
public class TestListUtils_groupBy {

    @Test
    public void testChop_EmptyList() {
        List<TestItem> l = new ArrayList<>();

        l.add(new TestItem("1"));
        l.add(new TestItem("22"));
        l.add(new TestItem("3"));
        l.add(new TestItem("44"));

        List<Set<TestItem>> groupBy = groupBy(l, (i1, i2) -> i1.getValue().length() == i2.getValue().length());

        Assert.assertEquals(groupBy.size(), 2);

        Assert.assertEquals(groupBy.get(0).size(), 2);
        Assert.assertTrue(groupBy.get(0).contains(l.get(0)));
        Assert.assertTrue(groupBy.get(0).contains(l.get(2)));

        Assert.assertEquals(groupBy.get(1).size(), 2);
        Assert.assertTrue(groupBy.get(1).contains(l.get(1)));
        Assert.assertTrue(groupBy.get(1).contains(l.get(3)));

    }

}
