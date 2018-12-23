package de.agentlab.ds.common;

import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class TestColorUtils {

    @Test
    public void testPlain() {
        int[] color = ColorUtils.toRGB("ff1233");

        Assert.assertEquals(color[0], 255);
        Assert.assertEquals(color[1], 18);
        Assert.assertEquals(color[2], 51);
    }

    @Test
    public void testLeadingHash() {
        int[] color = ColorUtils.toRGB("#ff1233");

        Assert.assertEquals(color[0], 255);
        Assert.assertEquals(color[1], 18);
        Assert.assertEquals(color[2], 51);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testWrongFormat() {
        int[] color = ColorUtils.toRGB("abc");
    }
}
