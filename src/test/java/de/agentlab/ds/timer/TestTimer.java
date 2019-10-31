package de.agentlab.ds.timer;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.testng.Assert;
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

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        CSVFormatter f = new CSVFormatter(new PrintStream(baos));
        f.print(Timer.getData());

        Assert.assertEquals(
                "Depth;Name;HC;ET;EC;ES;\r\n" +
                        "0;outer;1;0;0;0;\r\n" +
                        "1;inner_1;1;0;0;0;\r\n" +
                        "1;inner_2;1;0;0;0;\r\n", baos.toString());
    }
}
