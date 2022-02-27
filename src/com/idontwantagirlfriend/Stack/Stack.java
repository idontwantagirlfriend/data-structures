package com.idontwantagirlfriend.Stack;


import com.idontwantagirlfriend.LinkedList.AbstractLinkedList;
import com.idontwantagirlfriend.LinkedList.LinkedList;

import java.util.Iterator;

public class Stack<T> extends AbstractStack<T>{
    private AbstractLinkedList<T> items;

    public Stack() {
        this.items = new LinkedList<>();
    }

    /**
     * Insert an item on the top of the stack.<br/>
     * O(1) time complexity. Involves insert operation
     * on an array.
     * @param item
     */
    @Override
    public void push(T item) {
        items.addLast(item);
    }

    /**
     * Pop an item from the top of the stack.
     * On Empty stack, throw IllegalStateException.<br/>
     * O(1) time complexity. Involves removal operation
     * on an array.
     * @return the popped item.
     * @throws IllegalStateException
     */
    @Override
    public T pop() {
        if (isEmpty()) throw new IllegalStateException();
        return items.removeLast();
    }

    /**
     * Peek the item on top of the stack without removing it.
     * Return null on empty stack.<br/>
     * O(1) time complexity.
     * @return
     */
    @Override
    public T peek() {
        return items.getLast();
    }

    /**
     * Check if the stack is empty.<br/>
     * O(1) time complexity.
     * @return
     */
    @Override
    public Boolean isEmpty() {
        return items.size() == 0;
    }

    @Override
    public Iterator<T> iterator() {
        return items.iterator();
    }
}
