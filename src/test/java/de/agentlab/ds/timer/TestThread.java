package de.agentlab.ds.timer;

import org.testng.annotations.Test;

@Test
public class TestThread {
    @Test
    public void testThreading() {

        Runnable r_0 = () -> {
            Checkpoint t_0 = Timer.start("t_0");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Timer.stop(t_0);
            TextTableFormatter f = new TextTableFormatter();
            f.print(Timer.getData());
        };

        Thread t_0 = new Thread(r_0);
        t_0.start();

        Runnable r_1 = () -> {
            Checkpoint t_1 = Timer.start("t_1");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Timer.stop(t_1);

            TextTableFormatter f = new TextTableFormatter();
            f.print(Timer.getData());

            Timer.clear();
        };
        Thread t_1 = new Thread(r_1);
        t_1.start();

        try {
            t_0.join();
            t_1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
