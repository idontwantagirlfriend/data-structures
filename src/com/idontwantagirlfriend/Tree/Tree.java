package com.idontwantagirlfriend.Tree;

public interface Tree<E>{

    void insert(E element);
    Boolean find(E element);
    int height();
}
