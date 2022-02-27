package com.idontwantagirlfriend.Queue;

import com.idontwantagirlfriend.Array.Array;

import java.util.Iterator;

/**
 * An adaptor class on {@code Array}.
 */
public class ArrayQueue<T> extends AbstractQueue<T>{
    private Array<T> items;

    public ArrayQueue() {
        this.items = new Array<>();
    }

    /**
     * Add an element to the beginning of queue.<br/>
     * O(n) time complexity. Internally set the first slot
     * of an array to {@code element} and shift
     * everything forward by 1 slot.
     * @param element enqueued
     */
    @Override
    public void enqueue(T element) {
        items.insertBefore(0, element);
    }

    /**
     * Remove an element from the end of queue.<br/>
     * On empty queue, throw IllegalStateException.<br/>
     * O(1) time complexity. Involves deletion of item
     * from an array.
     * @return the dequeued element.
     */
    @Override
    public T dequeue() {
        if (isEmpty()) throw new IllegalStateException(
                "You were trying to dequeue but current queue is empty.");
        return items.removeAt(items.length() - 1);
    }

    /**
     * Peek the last element in queue
     * On empty queue, return null.<br/>
     * O(1) time complexity.
     * @return the peeked element, or null
     */
    @Override
    public T peek() {
        if (isEmpty()) return null;
        return items.get(items.length() - 1);
    }

    /**
     * O(1) time complexity.
     * @return if the current queue is empty
     */
    @Override
    public Boolean isEmpty() {
        return items.length() == 0;
    }

    @Override
    public Iterator<T> iterator() {
        return items.iterator();
    }
}
