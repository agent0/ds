package de.agentlab.ds.tree;

import de.agentlab.ds.Tree;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class TestVisit extends BaseTreeTest {

    @Test
    public void testVisit() {
        Assert.assertEquals(t.size(), 9);

        final List<TestItem> visited = new ArrayList<TestItem>();
        t.visit(new Tree.Visitor<TestItem>() {

            @Override
            public boolean visit(TestItem data) {
                visited.add(data);
                return true;
            }
        });

        Assert.assertEquals(t.getPreorderList(), visited);
    }

    @Test
    public void testVisitAbort() {
        Assert.assertEquals(t.size(), 9);

        final List<TestItem> visited = new ArrayList<TestItem>();
        t.visit(new Tree.Visitor<TestItem>() {

            @Override
            public boolean visit(TestItem data) {
                visited.add(data);
                if (data.equals(i_11)) {
                    return false;
                } else {
                    return true;
                }
            }
        });
        Assert.assertEquals(t.getPreorderList().subList(0, 6), visited);
    }

    @Test
    public void testVisitFromNode() {
        Assert.assertEquals(t.size(), 9);

        final List<TestItem> visited = new ArrayList<TestItem>();
        t.visit(i_1, new Tree.Visitor<TestItem>() {

            @Override
            public boolean visit(TestItem data) {
                visited.add(data);
                return true;
            }
        });

        List<TestItem> expected = new ArrayList<TestItem>();
        expected.add(i_1);
        expected.add(i_11);
        expected.add(i_110);
        expected.add(i_111);

        Assert.assertEquals(expected, visited);
    }

    @Test
    public void testVisitDescendants() {
        Assert.assertEquals(t.size(), 9);

        final List<TestItem> visited = new ArrayList<TestItem>();
        t.visitDescendants(i_1, new Tree.Visitor<TestItem>() {

            @Override
            public boolean visit(TestItem data) {
                visited.add(data);
                return true;
            }
        });

        List<TestItem> expected = new ArrayList<TestItem>();
        expected.add(i_11);
        expected.add(i_110);
        expected.add(i_111);

        Assert.assertEquals(expected, visited);
    }

    @Test
    public void testVisitSiblings() {
        Assert.assertEquals(t.size(), 9);

        final List<TestItem> visited = new ArrayList<TestItem>();
        t.visitSiblings(i_110, new Tree.Visitor<TestItem>() {

            @Override
            public boolean visit(TestItem data) {
                visited.add(data);
                return true;
            }
        });
        List<TestItem> expected = new ArrayList<TestItem>();
        expected.add(i_111);

        Assert.assertEquals(expected, visited);
    }

    @Test
    public void testVisitPrePost() {
        Assert.assertEquals(t.size(), 9);

        final List<TestItem> visited = new ArrayList<TestItem>();
        t.visit(new Tree.PrePostVisitor<TestItem>() {

            @Override
            public boolean visitPre(TestItem data) {
                visited.add(data);
                return true;
            }

            @Override
            public boolean visitPost(TestItem data) {
                visited.add(data);
                return true;
            }
        });
        List<TestItem> expected = new ArrayList<TestItem>();
        expected.add(i_0);
        expected.add(i_01);
        expected.add(i_010);
        expected.add(i_010);
        expected.add(i_011);
        expected.add(i_011);
        expected.add(i_01);
        expected.add(i_0);
        expected.add(i_1);
        expected.add(i_11);
        expected.add(i_110);
        expected.add(i_110);
        expected.add(i_111);
        expected.add(i_111);
        expected.add(i_11);
        expected.add(i_1);
        expected.add(i_2);
        expected.add(i_2);

        Assert.assertEquals(visited.size(), 18);
        Assert.assertEquals(visited, expected);
    }

}
