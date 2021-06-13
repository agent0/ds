package de.agentlab.ds.pivot;

import java.util.function.Function;

public class Pivot<T> {
    private String label;
    private Function<T, String> categoryFn;
    private Function<String, String> labelFn;

    public Pivot(String label, Function<T, String> categoryFn, Function<String, String> labelFn) {
        this.label = label;
        this.categoryFn = categoryFn;
        this.labelFn = labelFn;
    }

    public String getLabel() {
        return label;
    }

    public Function<T, String> getCategoryFn() {
        return categoryFn;
    }

    public Function<String, String> getLabelFn() {
        return labelFn;
    }

    @Override
    public String toString() {
        return "Pivot{" +
            "label='" + label + '\'' +
            '}';
    }
}
