package com.idontwantagirlfriend.Queue;

import com.idontwantagirlfriend.LinkedList.LinkedList;

import java.util.Iterator;

/**
 * An adaptor on {@code LinkedList} class.
 */
public class Queue<T> extends AbstractQueue<T> {
    private LinkedList<T> items;

    public Queue() {
        items = new LinkedList<>();
    }

    /**
     * Add an element to the beginning of queue.<br/>
     * O(1) time complexity.
     * @param element enqueued
     */
    @Override
    public void enqueue(T element) {
        items.addFirst(element);
    }

    /**
     * Remove an element from the end of queue.<br/>
     * On empty queue, throw IllegalStateException.<br/>
     * O(1) time complexity.
     * @return the dequeued element.
     */
    @Override
    public T dequeue() {
        if (isEmpty()) throw new IllegalStateException(
                "You were trying to dequeue but current queue is empty.");
        return items.removeLast();
    }

    /**
     * Peek the last element in queue
     * On empty queue, return null.<br/>
     * O(1) time complexity.
     * @return the peeked element, or null
     */
    @Override
    public T peek() {
        return items.getLast();
    }

    /**
     * O(1) time complexity.
     * @return if the current queue is empty
     */
    @Override
    public Boolean isEmpty() {
        return items.isEmpty();
    }

    @Override
    public Iterator<T> iterator() {
        return items.iterator();
    }
}
