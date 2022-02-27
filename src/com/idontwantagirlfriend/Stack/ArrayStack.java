package com.idontwantagirlfriend.Stack;

import com.idontwantagirlfriend.Array.Array;

import java.util.Iterator;

/**A stack implementation based on {@code Array}.
 * @param <T>
 */
public class ArrayStack<T> extends AbstractStack<T>{
    private Array<T> items;

    public ArrayStack() {
        this.items = new Array<>();
    }

    /**
     * Insert an item on the top of the stack.<br/>
     * O(1) time complexity. Involves insert operation
     * on an array.
     * @param item pushed
     */
    @Override
    public void push(T item) {
        items.insert(item);
    }

    /**
     * Pop an item from the top of the stack.
     * On empty stack, throw IllegalStateException.<br/>
     * O(1) time complexity. Involves removal operation
     * on an array.
     * @return the popped item.
     * @throws IllegalStateException
     */
    @Override
    public T pop() {
        if (isEmpty()) throw new IllegalStateException();
        return items.removeAt(items.length() - 1);
    }

    /**
     * Peek the item on the top of the stack
     * without removing it.<br/>
     * Return null on empty stack.
     * O(1) time complexity.
     * @return the peeked item
     */
    @Override
    public T peek() {
        if (isEmpty()) return null;
        return items.get(items.length() - 1);
    }

    /**
     * Check if the stack is empty.<br/>
     * O(1) time complexity.
     * @return if the stack is empty
     */
    @Override
    public Boolean isEmpty() {
        return (items.length() == 0);
    }

    @Override
    public Iterator<T> iterator() {
        return items.iterator();
    }
}
