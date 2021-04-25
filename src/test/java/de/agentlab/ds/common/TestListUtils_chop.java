package de.agentlab.ds.common;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static de.agentlab.ds.common.ListUtils.chop;

@Test
public class TestListUtils_chop {

    @Test
    public void testChop_EmptyList() {
        List<String> l = new ArrayList<>();

        List<String> result = chop(l);

        Assert.assertTrue(result.isEmpty());
    }

    @Test
    public void testChop_OneElementList() {
        List<String> l = new ArrayList<>();
        l.add("A");

        List<String> result = chop(l);

        Assert.assertTrue(result.isEmpty());
    }

    @Test
    public void testChop_MultipleElementList() {
        List<String> l = new ArrayList<>();
        l.add("A");
        l.add("B");

        List<String> result = chop(l);

        Assert.assertEquals(result.size(), 1);
        Assert.assertEquals(result.get(0), "A");
    }

}
