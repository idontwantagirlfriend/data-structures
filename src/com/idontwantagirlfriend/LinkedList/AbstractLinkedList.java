package com.idontwantagirlfriend.LinkedList;

public abstract class AbstractLinkedList<T> implements Iterable<T> {
    public abstract T addFirst(T element);

    public abstract T addLast(T element);

    public abstract int indexOf(T element);

    public abstract Boolean contains(T element);

    public abstract T removeFirst();

    public abstract T removeLast();

    public abstract T removeAt(int position);

    public abstract int size();

    public abstract T getFirst();

    public abstract T getLast();

    public abstract Boolean isEmpty();
}
