package de.agentlab.ds.timer;

import org.testng.annotations.Test;

@org.testng.annotations.Test
public class TestTimer {
    @Test
    public void testBasics() {
        Checkpoint outer = Timer.start("outer");

        Checkpoint inner_1 = Timer.start("inner_1");

        Timer.stop(inner_1);

        Checkpoint inner_2 = Timer.start("inner_2");

        Timer.stop(inner_2);

        Timer.stop(outer);

        TextTableFormatter f = new TextTableFormatter();
        f.print(Timer.getData());
    }
}
