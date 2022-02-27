package com.idontwantagirlfriend.Queue;

public abstract class AbstractQueue<T> implements Iterable<T> {
    public abstract void enqueue(T element);

    public abstract T dequeue();

    public abstract T peek();

    public abstract Boolean isEmpty();
}
