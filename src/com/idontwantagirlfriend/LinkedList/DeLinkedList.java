package com.idontwantagirlfriend.LinkedList;

import java.util.Iterator;

/**  An implementation of double ended linked list.
 *   The items are linked in both directions
 *   unlike the {@code ArrayLinkedList} which
 *   is based on {@code Array} class.
 */
public class DeLinkedList<T> extends AbstractLinkedList<T> {
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
            first.setPrevious(node);
            node.setNext(first);
        }
        first = node;
        return node.get();
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
            node.setPrevious(last);
            last.setNext(node);
        }
        last = node;

        return node.get();
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
        return (indexOf(item) != -1);
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
            second.setPrevious(null);
            first.setNext(null);
            first = second;
        }

        return removed;
    }
    /**
     * Remove the last item of linked list.
     * On empty list, throws {@code IllegalStateException}.<br/>
     * O(1) time complexity.
     * @return The removed item
     */
    @Override
    public T removeLast() {
        if (last == null) throw new IllegalStateException();

        var removed = last.get();

        if (!last.hasPrevious()) {
            first = last = null;
        } else {
            var secondLast = last.getPrevious();
            last.setPrevious(null);
            secondLast.setNext(null);
            last = secondLast;
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

    @Override
    public String toString() {
        var string = "";

        var cursorNode = first;
        if (!(cursorNode == null)) {
            while (cursorNode.hasNext()) {
                string = string.concat(cursorNode.get().toString()).concat(", ");
                cursorNode = cursorNode.getNext();
            }
            string = string.concat(cursorNode.get().toString());
        }

        return "[" + string + "]";
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
     * @return if the linked list is empty
     */
    @Override
    public Boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public Iterator<T> iterator() {
        return new DeLinkedListIterator();
    }

    private class Node {
        private Node previous;
        private Node next;
        private T value;

        public Node(T value) {
            this.value = value;
        }

        public T get() {
            return value;
        }

        public Boolean hasPrevious() {
            return (!(previous == null));
        }
        public Boolean hasNext() {
            return (!(next == null));
        }

        @Override
        public String toString() {
            return "Node{" +
                    "previous="
                    + ((previous != null) ? previous.get(): previous)
                    + ", next="
                    + ((next != null) ? next.get(): next)
                    + ", value=" + value +
                    '}';
        }

        public Node getPrevious() {
            return previous;
        }

        public void setPrevious(Node previous) {
            this.previous = previous;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }

    }

    private class DeLinkedListIterator implements Iterator<T> {
        private Node next;

        public DeLinkedListIterator() {
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
