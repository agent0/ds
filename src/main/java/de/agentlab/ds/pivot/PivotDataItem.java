package de.agentlab.ds.pivot;

public class PivotDataItem<S, T> {
    private S left;
    private T right;

    public PivotDataItem(S left, T right) {
        this.left = left;
        this.right = right;
    }

    public S getLeft() {
        return left;
    }

    public void setLeft(S left) {
        this.left = left;
    }

    public T getRight() {
        return right;
    }

    public void setRight(T right) {
        this.right = right;
    }

    @Override
    public String toString() {
        return "PivotData{" +
            "left=" + left +
            ", right=" + right +
            '}';
    }
}
