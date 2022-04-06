package com.idontwantagirlfriend.Array;

/**
 * The {@code Array} class with a linear expansion algorithm.
 * Has the same public interface as {@code Array}.
 * @param <T>
 */
public class LinearGrowingArray<T> extends Array<T>{
    private int step;

    public LinearGrowingArray(int step) {
        this.step = this.size = step;
        this.bound = -1;
        this.array = new Object[size];
    }

    @Override
    protected void expand() {
        this.size += step;
        var newArray = new Object[size];
        System.arraycopy(this.array, 0, newArray, 0, bound + 1);
        this.array = newArray;
    }
}
