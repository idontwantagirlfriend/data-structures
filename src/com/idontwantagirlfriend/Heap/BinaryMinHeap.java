package com.idontwantagirlfriend.Heap;

public class BinaryMinHeap {
    public static int getFirstIndexOfLevel(int level) {
        if (level < 0) return -1;
        return (int)Math.pow(2, level) - 1;
    }

    public static int getParentIndex(int parent) {
        if (parent <= 0) return -1;
        return (parent - 1) / 2;
    }

    public static int getFirstChildIndex(int parent) {
        if (parent < 0) return -1;
        return 2 * parent + 1;
    }

    private int[] array;
    private int size;
    private int cursor;

    public BinaryMinHeap() {
        this(7);
    }

    public BinaryMinHeap(int capacity) {
        array = new int[capacity];
        this.size = capacity;
        this.cursor = -1;
    }

    public void insert(int value) {
        resizeWhenNeeded(++cursor + 1);
        array[cursor] = value;
        bubbleUp(cursor);
    }

    public int remove() {
        if (isEmpty()) throw new IllegalStateException("Heap is empty, can't remove.");
        var removed = array[0];
        array[0] = array[cursor];
        cursor--;
        if (!isEmpty()) bubbleDown();
        return removed;
    }

    public Boolean find(int value) {
        for (var i : array) {
            if (i == value) return true;
        }
        return false;
    }

    public int size() {
        return cursor + 1;
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

    private Boolean hasNextLevel(int level) {
        return getFirstIndexOfLevel(level + 1) <= cursor;
    }

    private void bubbleDown() {
        var index = 0;
        while(true) {
            if (getFirstChildIndex(index) > cursor) break;
            var smallestChild = getSmallestChildIndex(index);
            if (array[index] <= array[smallestChild]) break;
            swap(index, smallestChild);
            index = smallestChild;
        }
    }

    private int getSmallestChildIndex(int index) {
        var leftChild = getFirstChildIndex(index);
        return (leftChild + 1 <= cursor && array[leftChild + 1] < array[leftChild])
                ? leftChild + 1
                : leftChild;
    }

    public boolean isEmpty() {
        return cursor == -1;
    }

    private void bubbleUp(int child) {
        if (child <= 0) return;
        if (array[child] < array[getParentIndex(child)]) {
            swap(getParentIndex(child), child);
            bubbleUp(getParentIndex(child));
        }
    }

    private void swap(int one, int another) {
        var temp = array[one];
        array[one] = array[another];
        array[another] = temp;
    }

    private void resizeWhenNeeded(int targetSize) {
        if (targetSize > size) {
            var newsize = size * 2 + 1;
            var newArray = new int[newsize];
            System.arraycopy(array, 0, newArray, 0, array.length);
            this.array = newArray;
            this.size = newsize;
        }
    }
}
