package com.idontwantagirlfriend.LinkedList;

import java.util.Iterator;

/**
 *  An implementation of one-way linked list.
 */
public class LinkedList<T> extends AbstractLinkedList<T> {
    private Node first;
    private Node last;

    /**
     * Add an item to the beginning of linked list.<br/>
     * O(1) time complexity.
     * @param item The added {@code item};
     * @return {@code item}
     */
    @Override
    public T addFirst(T item) {
        var node = new Node(item);

        if (first == null) {
            last = node;
        } else {
            node.setNext(first);
        }
        first = node;

        return item;
    }

    /**
     * Add an item to the end of linked list.<br/>
     * O(1) time complexity.
     * @param item The added {@code item};
     * @return {@code item}
     */
    @Override
    public T addLast(T item) {
        var node = new Node(item);

        if (last == null) {
            first = node;
        } else {
            last.setNext(node);
        }
        last = node;

        return item;
    }

    /**
     * Traverse the linked list and fetch index
     * of the item (if there is any).<br/>
     * Returns -1 if no match can be found.<br/>
     * O(n) time complexity.
     * @param item
     * @return {@code index}
     */
    @Override
    public int indexOf(T item) {
        var i = -1;
        for (var nodeValue : this) {
            i++;
            if (nodeValue == item) return i;
        }
        return -1;
    }

    /**
     * Check if linked list contains the given item.<br/>
     * O(n) time complexity.
     * @param item
     * @return {@code Boolean}
     */
    @Override
    public Boolean contains(T item) {
        return !(indexOf(item) == -1);
    }

    /**
     * Remove the first item of linked list.
     * On empty list, throws {@code IllegalStateException}.<br/>
     * O(1) time complexity.
     * @return The removed item
     */
    @Override
    public T removeFirst() {
        if (first == null) throw new IllegalStateException();

        var removed = first.get();

        if (!first.hasNext()) {
            first = last = null;
        } else {
            var second = first.getNext();
            first.setNext(null);
            first = second;
        }

        return removed;
    }

    /**
     * Traverse the list and remove the last item.
     * On empty list, throws {@code IllegalStateException}.<br/>
     * O(n) time complexity.
     * @return The removed item
     */
    @Override
    public T removeLast() {
        if (first == null) throw new IllegalStateException();

        var removed = last.get();

        if (!first.hasNext()) {
            first = last = null;
        } else {
            var cursorNode = first;
            while (cursorNode.getNext().hasNext()) cursorNode = cursorNode.getNext();
            cursorNode.setNext(null);
            last = cursorNode;
        }

        return removed;
    }

    @Override
    public int size() {
        var i = 0;
        for (var item : this) {
            i++;
        }
        return i;
    }

    /**
     * Return the first item. On empty list, return null.
     * @return The first item
     */
    @Override
    public T getFirst() {
        if (first == null) return null;
        return first.get();
    }

    /**
     * Return the last item. On empty list, return null.
     * @return The last item
     */
    @Override
    public T getLast() {
        if (last == null) return null;
        return last.get();
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
        return new LinkedListIterator();
    }

    @Override
    public String toString() {
        var string = "";

        if (first != null) {
            var cursorNode = first;
            while (cursorNode.hasNext()) {
                string = string.concat(cursorNode.get().toString()).concat(", ");
                cursorNode = cursorNode.getNext();
            }
            string = string.concat(cursorNode.get().toString());
        }

        return "[" + string + "]";
    }

    private class Node{
        private Node next;
        private T value;

        public Node(T value) {
            this.value = value;
        }

        public T get() {
            return this.value;
        }

        public Boolean hasNext() {
            return (next != null);
        }

        @Override
        public String toString() {
            return "Node{" +
                    "next="
                    + ((next != null) ? next.get(): next)
                    + ", value=" + value +
                    '}';
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }
    }

    private class LinkedListIterator implements Iterator<T> {
        private Node next;

        public LinkedListIterator() {
            this.next = first;
        }

        @Override
        public boolean hasNext() {
            return !(next == null);
        }

        @Override
        public T next() {
            if (!hasNext()) throw new IndexOutOfBoundsException();
            var val = this.next.get();
            this.next = this.next.getNext();
            return val;
        }
    }
}
