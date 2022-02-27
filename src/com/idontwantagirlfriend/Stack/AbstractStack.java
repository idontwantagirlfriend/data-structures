package com.idontwantagirlfriend.Stack;

public abstract class AbstractStack<T> implements Iterable<T> {
    public abstract void push(T element);

    public abstract T pop();

    public abstract T peek();

    public abstract Boolean isEmpty();
}
