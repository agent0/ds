package de.agentlab.ds.common;

public class Counter {
    private int value = 0;

    public int get() {
        return value;
    }

    public void inc() {
        this.value++;
    }
}
