package de.agentlab.ds.tree;

import java.io.Serializable;

public class TestItem implements Serializable {

    private String value;

    public TestItem(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public String toString() {
        return this.value;
    }

}
