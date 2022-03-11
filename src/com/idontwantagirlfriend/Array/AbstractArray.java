package com.idontwantagirlfriend.Array;

public abstract class AbstractArray<T> implements Iterable<T> {
    public abstract T replace(int index, T element);

    public abstract T insert(T element);

    public abstract T insertBefore(int index, T element);

    public abstract T get(int index);

    public abstract T removeAt(int index);

    public abstract int indexOf(T element);

    public abstract void join(AbstractArray<? extends T> to);

    public abstract int length();

    public abstract Boolean contains(T searchItem);
}
