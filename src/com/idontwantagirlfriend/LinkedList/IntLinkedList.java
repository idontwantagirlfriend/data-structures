package com.idontwantagirlfriend.LinkedList;

import java.util.Iterator;
import java.util.function.Function;

/**
 * An implementation of linked list for int type.
 */
public class IntLinkedList extends AbstractIntLinkedList implements Iterable<Integer> {
    private Node first;
    private Node last;

    /**
     * Add a number to the beginning of linked list.<br/>
     * O(1) time complexity.
     * @param number The added {@code number};
     * @return {@code item}
     */
    @Override
    public int addFirst(int number) {
        var node = new Node(number);
        if (first == null) {
            last = node;
        } else {
            node.setNext(first);
        }
        first = node;

        return first.get();
    }

    /**
     * Add a number to the end of linked list.<br/>
     * O(1) time complexity.
     * @param number The added {@code number};
     * @return {@code item}
     */
    @Override
    public int addLast(int number) {
        var node = new Node(number);
        if (last == null) {
            first = node;
        } else {
            last.setNext(node);
        }
        last = node;

        return last.get();
    }

    /**
     * Traverse the linked list and fetch index
     * of the number (if there is any).<br/>
     * Returns -1 if no match can be found.<br/>
     * O(n) time complexity.
     * @param number
     * @return {@code index}
     */
    @Override
    public int indexOf(int number) {
        var i = -1;
        var iterator = this.iterator();
        while (iterator.hasNext()) {
            i++;
            var item = iterator.next();
            if (item == number) return i;
        }

        return -1;
    }

    /**
     * Check if linked list contains the number.<br/>
     * O(n) time complexity.
     * @param number
     * @return {@code Boolean}
     */
    @Override
    public Boolean contains(int number) {
        return indexOf(number) != -1;
    }

    /**
     * Remove the first number of the linked list.
     * On empty list, throws {@code IllegalStateException}.<br/>
     * O(1) time complexity.
     * @return The removed number
     */
    @Override
    public int removeFirst() {
        if (first == null) throw new IllegalStateException();

        var removed = first.get();
        if (first.hasNext()) {
            var second = first.getNext();
            first.setNext(null);
            first = second;
        } else {
            first = last = null;
        }
        return removed;
    }

    /**
     * Traverse the list and remove the last number.
     * On empty list, throws {@code IllegalStateException}.<br/>
     * O(n) time complexity.
     * @return The removed number
     */
    @Override
    public int removeLast() {
        if (last == null) throw new IllegalStateException();

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

    /**
     * Remove number at a given position in the list.<br/>
     * O(n) time complexity.
     * @param position
     * @return the removed number.
     * @throws IllegalStateException on negative position
     * @throws IndexOutOfBoundsException on excessive position
     */
    @Override
    public int removeAt(int position) {
        if (position < 0)
            throw new IllegalArgumentException(
                    "Removal position must be non-negative.");
        var reportIndexOutOfBoundsException = (Function<Integer, Integer>) index -> {
            throw new IndexOutOfBoundsException(
                    "You were trying to remove a number at position"
                            + index
                            + "while the maximum index is"
                            + size());
        };

        if (isEmpty()) reportIndexOutOfBoundsException.apply(position);

        var cursorNode = first;

        for (var i = 0; i < position - 1; i++) {
            if (!cursorNode.hasNext()) {
                return reportIndexOutOfBoundsException.apply(position);
            }
            cursorNode = cursorNode.getNext();
        }

//        Cursor arrives at the node right before the removed one.
//        It shifts forward [position - 1] times. When (position = 0),
//        the cursor won't shift.
//        [cursor]  =>  removed  => next

        if (position == 0) {
            first = cursorNode.getNext();
            var removedNode = cursorNode;
//            Case 1: remove the first node, no need to care about
//            unbinding the previous node.
            if (!cursorNode.hasNext()) {
                last = null;
            } else {
                cursorNode.setNext(null);
            }
            return removedNode.get();
        }

//        Case 2: remove a node in the middle, we should be arriving
//        right before the removed one.
//                    [position]
//                        ||
//        [cursor]  =>  removed  => next
        if (!cursorNode.hasNext()) {
//            Case 2.1: the node at [position] doesn't exist,
//            throw new IndexOutOfBoundException.
            return reportIndexOutOfBoundsException.apply(position);
        }

        var removedNode = cursorNode.getNext();

        if (!removedNode.hasNext()) {
//            Case 2.2: the removed node being the last node,
//            overwrite [last] with [cursor]
//                  [position], [last]
//                            ||
//            [cursor]  <=>  removed
            cursorNode.setNext(null);
            last = cursorNode;
        } else {
//            Case 2.3: the removed node isn't the last node
//            (default situation), then unset previous and next for
//            the surrounding nodes.
            var afterNode = removedNode.getNext();
            removedNode.setNext(null);
            cursorNode.setNext(afterNode);
        }

        return removedNode.get();
    }

    @Override
    public int size() {
        var i = 0;
        var iterator = this.iterator();
        while (iterator.hasNext()) {
            iterator.next();
            i++;
        }
        return i;
    }

    /**
     * Return the first number. On empty list, throw
     * IllegalStateException.
     * @return The first number
     */
    @Override
    public int getFirst() {
        if (first == null)
            throw new IllegalStateException(
                    "You were trying to fetch the first item, but the list is empty.");
        return first.get();
    }

    /**
     * Return the last number. On empty list, throw
     * IllegalStateException.
     * @return The last number
     */
    @Override
    public int getLast() {
        if (last == null)
            throw new IllegalStateException(
                    "You were trying to fetch the last item, but the list is empty."
            );
        return last.get();
    }

    @Override
    public Boolean isEmpty() {
        return first == null;
    }

    public IntIterator iterator() {
        return new IntIterator();
    }

    @Override
    public String toString() {
        var string = "";

        if (first != null) {
            var cursorNode = first;
            while (cursorNode.hasNext()) {
                string = string.concat(Integer.toString(cursorNode.get())).concat(", ");
                cursorNode = cursorNode.getNext();
            }
            string = string.concat(Integer.toString(cursorNode.get()));

        }

        return "[" + string + "]";
    }

    private class Node {
        private int value;
        private Node next;

        public Node(int value) {
            this.value = value;
        }

        public Boolean hasNext() {
            return (next != null);
        }

        public int get() {
            return value;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "value=" + value +
                    ", next=" + next +
                    '}';
        }
    }

    public class IntIterator implements Iterator<Integer> {
        private Node current;

        public boolean hasNext() {
            return current == null ? first != null : current.hasNext();
        }

        public Integer next() {
            current = (current == null) ? first : current.getNext();

            return current.get();
        }
    }
}
