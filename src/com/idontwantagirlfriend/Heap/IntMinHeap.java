package com.idontwantagirlfriend.Heap;

/**
 * A binary min heap that supports storing objects and
 * sorting by {@code int} priority .
 */
public class IntMinHeap {

    public static int getFirstIndexOfLevel(int level) {
        if (level < 0) return -1;
        return (int)Math.pow(2, level) - 1;
    }

    public static int getParent(int child) {
        if (child <= 0) return -1;
        return (child - 1) / 2;
    }

    public static int getFirstChild(int parent) {
        if (parent < 0) return -1;
        return 2 * parent + 1;
    }

    private Node[] array;
    private int size;
    private int cursor;

    public IntMinHeap() {
        this(7);
    }

    public IntMinHeap(int initialCapacity) {
        this.size = initialCapacity;
        this.array = new Node[size];
        this.cursor = -1;
    }

    public void insert(int value, int priority) {
        resizeWhenNeeded(++cursor + 1);
        var node = new Node(value, priority);
        array[cursor] = node;
        bubbleUp(cursor);
    }

    public int remove() {
        if (isEmpty())
            throw new IllegalStateException(
                    "Heap is empty, but you were attempting to remove from it.");
        var removed = array[0].getValue();
        if (cursor != 0) array[0] = array[cursor];
        cursor--;
        if (cursor != 0) bubbleDown();
        return removed;
    }

    public boolean isEmpty() {
        return cursor == -1;
    }

    @Override
    public String toString() {
        var string = new StringBuilder();
        if (!isEmpty()) {
            var currentLevel = 0;
            while (true) {
                string.append(levelToString(currentLevel));
                if (hasNextLevel(currentLevel++)) {
                    string.append(", ");
                } else break;
            }
        }
        return "[" + string + "]";
    }

    private void bubbleDown() {
        bubbleDown(0);
    }

    private void bubbleDown(int parent) {
        if (getFirstChild(parent) > cursor) return;
        var smallestChildIndex = getSmallestChildIndex(parent);
        if (array[parent].getPriority()
                > array[smallestChildIndex].getPriority()) {
            swap(parent, smallestChildIndex);
            bubbleDown(smallestChildIndex);
        }

    }

    private int getSmallestChildIndex(int parent) {
        var firstChild = getFirstChild(parent);
        return (firstChild + 1 <= cursor
                && array[firstChild + 1].getPriority()
                < array[firstChild].getPriority())
                ? firstChild + 1
                : firstChild;
    }

    private void bubbleUp(int child) {
        if (child <= 0) return;
        if (array[child].priority < array[getParent(child)].priority) {
            swap(getParent(child), child);
            bubbleUp(getParent(child));
        }
    }

    private String levelToString(int level) {
        var string = new StringBuilder();
        string.append("[");
        for (var i = getFirstIndexOfLevel(level);
             i < getFirstIndexOfLevel(level + 1);
             i++) {
            if (i > cursor) break;
            string.append(i + 1 <= cursor && i < getFirstIndexOfLevel(level + 1) - 1
                    ? array[i] + ", "
                    : array[i]);
        }
        string.append("]");
        return string.toString();
    }

    private boolean hasNextLevel(int level) {
        return getFirstIndexOfLevel(level + 1) <= cursor;
    }

    private void swap(int one, int another) {
        var temp = array[one];
        array[one] = array[another];
        array[another] = temp;
    }

    private void resizeWhenNeeded(int targetSize) {
        if (size < targetSize) {
            this.size = size * 2 - 1;
            var newArray = new Node[size];
            System.arraycopy(array, 0, newArray, 0, array.length);
            this.array = newArray;
        }
    }

    private static class Node {
        private final int priority;
        private final int value;

        public Node(int value, int priority) {
            this.value = value;
            this.priority = priority;
        }

        public int getPriority() {
            return priority;
        }

        public int getValue() {
            return value;
        }

        @Override
        public String toString() {
            return value + "(" + priority + ")";
        }
    }
}
