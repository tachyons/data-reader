package org.metastringfoundation.healthheatmap;

public abstract class DataPoint<T> {
    private T value;

    public DataPoint(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }
}
