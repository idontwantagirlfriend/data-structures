package com.idontwantagirlfriend.LinkedList;

public abstract class AbstractIntLinkedList {
    public abstract int addFirst(int element);

    public abstract int addLast(int element);

    public abstract int indexOf(int element);

    public abstract Boolean contains(int element);

    public abstract int removeFirst();

    public abstract int removeLast();

    public abstract int size();

    public abstract int getFirst();

    public abstract int getLast();

    public abstract Boolean isEmpty();
}
