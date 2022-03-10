package com.idontwantagirlfriend.Heap;

public class MinPriorityQueue {
    private int[] array;
    private final int SIZE;
    private int cursor;

    public MinPriorityQueue() {
        this.SIZE = 10;
        this.array = new int[SIZE];
    }

    public void add(int value) {
        if (isFull()) throw new FullQueueError();
        var heap = new BinaryMinHeap( 10);
        for (var i : array) heap.insert(i);
        array[++cursor] = heap.remove();
    }

    public int remove() {
        if (isEmpty())
            throw new IllegalStateException(
                    "Queue is empty and you were attempting to remove from it.");
        return array[cursor--];
    }
    public Boolean isEmpty() {
        return cursor == -1;
    }


    private boolean isFull() {
        return (cursor >= SIZE - 1);
    }

    private static class FullQueueError extends RuntimeException {}
}
