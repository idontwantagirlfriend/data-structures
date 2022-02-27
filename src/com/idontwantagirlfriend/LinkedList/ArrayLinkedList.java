package com.idontwantagirlfriend.LinkedList;

import com.idontwantagirlfriend.Array.Array;

import java.util.Iterator;

/**
 * A fake linked list implemented with {@code Array}.
 * @param <T> the type of items to put in the linked list.
 */
public class ArrayLinkedList<T> extends AbstractLinkedList<T> {

    private Array<T> items;
    private int length;

    public ArrayLinkedList() {
        this.items = new Array<>();
        this.length = 0;
    }

    /**
     * Attach a new item at the beginning of linked list.<br/>
     * Internally create a new array and copy the old one
     * onto the end of it, so that the index can be shifted
     * forward by 1.<br/>
     * O(n) time complexity. Involves single traversal of array.
     * @param item
     * @return the added item.
     */
    @Override
    public T addFirst(T item) {
        items.insertBefore(0, item);
        this.length++;

        return item;
    }

    /**
     * Attach a new item at the end of the linked list.<br/>
     * O(1) time complexity.
     * @param item
     * @return
     */

    @Override
    public T addLast(T item) {
        items.insert(item);
        this.length++;
        return item;
    }

    /**
     * Traverse the linked list to find a matching item.<br/>
     * O(n) time complexity.
     * @param item
     * @return the index of the found item, or -1
     */
    @Override
    public int indexOf(T item) {
        return items.indexOf(item);
    }

    /**
     * Traverse the linked list to find a matching item.<br/>
     * O(n) time complexity.
     * @param item
     * @return if the item exists or not
     */
    @Override
    public Boolean contains(T item) {
        return !(this.indexOf(item) == -1);
    }

    /**
     * Remove the first item in the link list.<br/>
     * Internally involve a shift of index in an array.<br/>
     * O(n) time complexity.
     * @return the removed item.
     */
    @Override
    public T removeFirst() {
        handleRemoveFromEmptyList();
//      Empty lists will throw IndexOutOfBoundException.

        T removed = items.get(0);

        items.removeAt(0);

        this.length--;

        return removed;
    }

    /**
     * Remove the last item in the link list.<br/>
     * Internally remove the last item of an array.<br/>
     * O(1) time complexity.
     * @return
     */
    @Override
    public T removeLast() {
        handleRemoveFromEmptyList();
//      Empty lists will throw IndexOutOfBoundException.

        T removed = items.get(--this.length);

        items.removeAt(length);

        return removed;
    }

    /**
     * Read the internally kept size field.<br/>
     * O(1) time complexity.
     * @return
     */
    @Override
    public int size() {
        return length;
    }

    /**
     * Return the first item. On empty list, return null.
     * @return The first item
     */
    @Override
    public T getFirst() {
        if (length == 0) return null;
        return items.get(0);
    }

    /**
     * Return the last item. On empty list, return null.
     * @return The last item
     */
    @Override
    public T getLast() {
        if (length == 0) return null;
        return items.get(length - 1);
    }

    /**
     * O(1) time complexity.
     * @return if the linked list is empty.
     */
    @Override
    public Boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayLinkedListIterator();
    }

    @Override
    public String toString() {
        return items.toString();
    }

    private void handleRemoveFromEmptyList(){
        if (length == 0) throw new IllegalStateException();
    }

    private class ArrayLinkedListIterator implements Iterator<T>{

        private int cursor;

        public ArrayLinkedListIterator() {
            this.cursor = 0;
        }

        @Override
        public boolean hasNext() {
            return (cursor < length);
        }

        @Override
        public T next() {
            return items.get(cursor++);
        }
    }
}
