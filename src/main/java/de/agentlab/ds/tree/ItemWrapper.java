package de.agentlab.ds.tree;

public class ItemWrapper<T> {

    private T item;

    public ItemWrapper(T item) {
        this.item = item;
    }

    public T getItem() {
        return item;
    }

    @Override
    public String toString() {
        return this.item.toString();
    }
}
