package com.idontwantagirlfriend.Heap;

public class BinaryMaxHeap {
    private int[] items;
    private int size;
    private int cursor;

    public BinaryMaxHeap() {
        this(7);
    }

    public BinaryMaxHeap(int capacity) {
        this.items = new int[capacity];
        this.size = capacity;
        this.cursor = -1;
    }

    public void insert(int value) {
        resizeWhenNeeded(++cursor + 1);
        items[cursor] = value;
        bubbleUp(cursor);
    }

    public void remove() {
        if (isEmpty()) throw new IllegalStateException("Heap is empty");
        if (cursor != 0) items[0] = items[cursor];
        cursor--;
        if (!isEmpty()) bubbleDown(0);
    }


    public Boolean find(int value) {
        if (cursor == -1) return false;
        return find(value, 0);
    }

    public int size() {
        return cursor + 1;
    }

    public Boolean isEmpty() {
        return this.cursor == -1;
    }

    @Override
    public String toString() {
        return "[" + (
                cursor != -1
                ? toString(0)
                : "")
                + "]";
    }

    private void bubbleUp(int childIndex) {
        if (childIndex > 0) {
            var parentIndex = getParentIndex(childIndex);
            if (items[childIndex] > items[parentIndex]) {
                swapItems(childIndex, parentIndex);
                bubbleUp(parentIndex);
            }

        }
    }

    private void bubbleDown(int parentIndex) {
        var firstChildIndex = getFirstChildIndex(parentIndex);
        if (cursor < firstChildIndex) return;
        var largestChildIndex = getLargestChildIndex(parentIndex);
        if (items[parentIndex] < items[largestChildIndex]) {
            swapItems(parentIndex, largestChildIndex);
            bubbleDown(largestChildIndex);
        }
    }

    private int getLargestChildIndex(int parentIndex) {
        // The parent has been proven to have valid children
        var firstChildIndex = getFirstChildIndex(parentIndex);
        return (cursor > firstChildIndex
                    && items[firstChildIndex + 1] > items[firstChildIndex])
                ? firstChildIndex + 1
                : firstChildIndex;
    }

    private Boolean find(int value, int level) {
        if (getLevelStartingIndex(level) > cursor) return false;
        var foundOnThisLevel = false;
        for (var i = getLevelStartingIndex(level);
             i < getLevelStartingIndex(level + 1);
             i++) {
            if (i > cursor || items[i] == value) {
                foundOnThisLevel = (i <= cursor && items[i] == value);
                break;
            }
        }
        return foundOnThisLevel || find(value, level + 1);
    }

    private void resizeWhenNeeded(int targetSize) {
        if (targetSize > size) {
            var newSize = size * 2 + 1;
            var toMigrate = new int[newSize];
            System.arraycopy(items, 0, toMigrate, 0, size);
            this.items = toMigrate;
            this.size = newSize;
        }
    }

    public static int getParentIndex(int childIndex) {
        if (childIndex <= 0) return -1;
        return (childIndex - 1) / 2;
    }

    public static int getFirstChildIndex(int parentIndex) {
        if (parentIndex < 0) return -1;
        return 2 * parentIndex + 1;
    }

    public static int getLevel(int index) {
        if (index < 0) return -1;
        return (int)log2(index + 1);
    }

    private static double log2(double n) {
        return Math.log(n) / Math.log(2);
    }

    public static int getLevelStartingIndex(int level) {
        if (level < 0) return -1;
        return (int) Math.round(Math.pow(2, level) - 1);
    }


    private void swapItems(int childIndex, int parentIndex) {
        var childValue = items[childIndex];
        items[childIndex] = items[parentIndex];
        items[parentIndex] = childValue;
    }

    private String toString(int level) {
        StringBuilder string = new StringBuilder();
        for (var i = getLevelStartingIndex(level);
             i < getLevelStartingIndex(level + 1);
             i++) {
            if (i <= cursor)
                string.append(i + 1 <= cursor && i < getLevelStartingIndex(level + 1) - 1
                        ? items[i] + ", "
                        : items[i]);
        }
        return "[" + string + "]" + (
                getLevelStartingIndex(level + 1) <= cursor
                ? ", " + toString(level + 1)
                : "");
    }

}