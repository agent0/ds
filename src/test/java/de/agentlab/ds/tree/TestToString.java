package de.agentlab.ds.tree;

import de.agentlab.ds.Tree;

import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class TestToString extends BaseTreeTest {

    @Test
    public void testToString() {
        Assert.assertEquals(t.size(), 9);

        String flat = TreeTestUtils.toFlatString(t);

        Assert.assertEquals(flat, "0|  0.1|    0.1.0|    0.1.1|1|  1.1|    1.1.0|    1.1.1|2|");
    }

    @Test
    public void testToStringWithElementFormatter() {
        Assert.assertEquals(t.size(), 9);

        String flat = TreeTestUtils.toFlatString(t, new Tree.ElementFormatter<TestItem>() {

            @Override
            public String format(TestItem data) {
                return data.getValue().replaceAll("0", "a").replaceAll("1", "b").replaceAll("2", "c");
            }
        });

        Assert.assertEquals(flat, "a|  a.b|    a.b.a|    a.b.b|b|  b.b|    b.b.a|    b.b.b|c|");
    }

}
