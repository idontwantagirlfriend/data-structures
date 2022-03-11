package com.idontwantagirlfriend.Heap;

import java.util.Arrays;

public class MinPriorityQueue {
    private BinaryMinHeap heap;

    public MinPriorityQueue() {
        this.heap = new BinaryMinHeap();
    }

    public void add(int value) {
        heap.insert(value);
    }

    public int remove() {
        if (isEmpty())
            throw new IllegalStateException(
                    "Queue is empty but you were attempting to remove from it.");
        return heap.remove();
    }

    public Boolean isEmpty() {
        return heap.size() == 0;
    }

    @Override
    public String toString() {
        var tempArray = new int[heap.size()];
        for (var i = heap.size() - 1; i >= 0; i--) {
            tempArray[i] = heap.remove();
        }
        var string = Arrays.toString(tempArray);
        for (var i : tempArray) heap.insert(i);
        return string;
    }
}
