package com.idontwantagirlfriend.Array;

public abstract class AbstractIntArray {
    public abstract int replace(int index, int element);

    public abstract int insert(int element);

    public abstract int insertBefore(int index, int element);

    public abstract int get(int index);

    public abstract int removeAt(int index);

    public abstract int indexOf(int element);

    public abstract void join(AbstractIntArray to);

    public abstract int length();

    public abstract Boolean contains(int searchItem);
}
