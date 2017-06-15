package de.agentlab.ds.tree;

import de.agentlab.ds.Tree;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

//@formatter:off
/*

0
  0.1
    0.1.0
    0.1.1
1
  1.1
    1.1.0
    1.1.1
2

*/
//@formatter:on

@Test
public class BaseTreeTest {
    protected Tree<TestItem> t;

    protected TestItem i_0;
    protected TestItem i_01;
    protected TestItem i_010;
    protected TestItem i_011;

    protected TestItem i_1;
    protected TestItem i_11;
    protected TestItem i_110;
    protected TestItem i_111;

    protected TestItem i_2;

    protected Tree<TestItem> t2;

    protected TestItem i_3;
    protected TestItem i_30;
    protected TestItem i_300;
    protected TestItem i_301;
    protected TestItem i_31;
    protected TestItem i_310;
    protected TestItem i_311;

    @BeforeMethod
    public void setUp() {
        this.t = new Tree<TestItem>();

        this.i_0 = new TestItem("0");
        this.i_01 = new TestItem("0.1");
        this.i_010 = new TestItem("0.1.0");
        this.i_011 = new TestItem("0.1.1");

        this.i_1 = new TestItem("1");
        this.i_11 = new TestItem("1.1");
        this.i_110 = new TestItem("1.1.0");
        this.i_111 = new TestItem("1.1.1");

        this.i_2 = new TestItem("2");

        t.add(i_0);
        t.addChild(i_0, i_01);
        t.addChild(i_01, i_010);
        t.addChild(i_01, i_011);

        t.add(i_1);
        t.addChild(i_1, i_11);
        t.addChild(i_11, i_110);
        t.addChild(i_11, i_111);

        t.add(i_2);

        this.t2 = new Tree<TestItem>();
        this.i_3 = new TestItem("3");
        this.i_30 = new TestItem("3.0");
        this.i_300 = new TestItem("3.0.0");
        this.i_301 = new TestItem("3.0.1");
        this.i_31 = new TestItem("3.1");
        this.i_310 = new TestItem("3.1.0");
        this.i_311 = new TestItem("3.1.1");

        t2.add(i_3);
        t2.addChild(i_3, i_30);
        t2.addChild(i_30, i_300);
        t2.addChild(i_30, i_301);
        t2.addChild(i_3, i_31);
        t2.addChild(i_31, i_310);
        t2.addChild(i_31, i_311);

    }
}
