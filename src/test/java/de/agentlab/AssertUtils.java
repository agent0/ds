package de.agentlab;

import java.util.List;

import org.testng.Assert;

public class AssertUtils {
    public static <S> void assertEqualsNoOrder(List<S> actual, List<S> expected) {
        actual.forEach(e -> {
            if (!expected.contains(e)) {
                Assert.fail("Actual Element '" + e + "' not expected");
            }
        });
        expected.forEach(e -> {
            if (!actual.contains(e)) {
                Assert.fail("Expected Element '" + e + "' not found");
            }
        });
    }
}
