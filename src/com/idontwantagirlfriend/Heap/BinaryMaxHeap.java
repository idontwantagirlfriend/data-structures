package com.idontwantagirlfriend.Heap;

public class BinaryMaxHeap {
    /**
     * Convert an array to a heap in place.
     * @param array to be converted
     */
    public static void heapify(int[] array) {
        if (bubbleDown(array)) heapify(array);
    }

    private static boolean bubbleDown(int[] array) {
        var needToHeapifyAgain = false;
        var cursor = -1;
        while (getFirstChildIndex(++cursor) < array.length) {
            var largestChild = getLargestChild(array, cursor);
            if (array[cursor] < array[largestChild]) {
                swap(array, cursor, largestChild);
                needToHeapifyAgain = true;
            }
        }
        return needToHeapifyAgain;
    }

    private static int getLargestChild(int[] array, int index) {
        var firstChild = getFirstChildIndex(index);
        return (firstChild + 1 < array.length && array[firstChild + 1] > array[firstChild])
                ? firstChild + 1
                : firstChild;
    }

    public static Boolean isBinaryMaxHeap(int[] array) {
        var cursor = -1;
        while (getFirstChildIndex(++cursor) < array.length) {
            var largestChild = getLargestChild(array, cursor);
            if (array[cursor] < array[largestChild]) {
                return false;
            }
        }
        return true;
    }

    public static int getKthLargest(int K, int[] array) {
        if (K <= 0 || K >= array.length)
            throw new IllegalArgumentException("K should be within the bounds of array.");
        var heap = new BinaryMaxHeap();
        for (int i : array) heap.insert(i);
        for (int i = 0; i < K - 1; i++) {
            heap.remove();
        }
        return heap.remove();
    }

    private static void swap(int[] array, int one, int another) {
        var temp = array[one];
        array[one] = array[another];
        array[another] = temp;
    }

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

    public int remove() {
        if (isEmpty()) throw new IllegalStateException("Heap is empty");
        var removed = items[0];
        if (!isEmpty()) items[0] = items[cursor];
        cursor--;
        if (!isEmpty()) bubbleDown(0);
        return removed;
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
        if (childIndex <= 0) return;
        var parentIndex = getParentIndex(childIndex);
        if (items[childIndex] > items[parentIndex]) {
            swap(childIndex, parentIndex);
            bubbleUp(parentIndex);
        }
    }

    private void bubbleDown(int parentIndex) {
        var firstChildIndex = getFirstChildIndex(parentIndex);
        if (cursor < firstChildIndex) return;
        var largestChildIndex = getLargestChildIndex(parentIndex);
        if (items[parentIndex] < items[largestChildIndex]) {
            swap(parentIndex, largestChildIndex);
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


    private void swap(int one, int another) {
        swap(items, one, another);
    }

    private String toString(int level) {
        var string = new StringBuilder();
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