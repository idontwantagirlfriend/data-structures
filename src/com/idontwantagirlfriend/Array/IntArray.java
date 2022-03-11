package com.idontwantagirlfriend.Array;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * An array implementation based on {@code Array} for
 * {@code} int type. Automatic resizing.<br/>
 * Can't be iterated since it cannot implement {@code Iterable}
 * interface because of primitive type. Instead, iterator
 * pattern is implemented.
 * A {@code bound} property is used instead of length.
 */
public class IntArray extends AbstractIntArray {

    private int[] array;
    private int size;
    private int bound;
    /**
     * Bound is the index of the last item in this array.
     * An empty array has {@code bound} of -1.
     */

    public IntArray() {
        this.size = 5;
        this.bound = -1;
        this.array = new int[size];
    }

    /**
     * insert: insert an integer to the end of array
     * and increment the bound by 1.<br/>
     * O(1) time complexity.
     * @return the inserted {@code number}.
     */
    public int insert(int number) {
        expandWhenFull();
        return set(++bound, number);
    }

    /**
     * replace: replace the integer at {@code index}
     * with {@code number} in this array.<br/>
     * O(1) time complexity.
     * @return the replaced {@code number}.
     * @throws IllegalStateException on negative index
     * @throws IndexOutOfBoundsException on excessive index
     */
    @Override
    public int replace(int index, int number) {
        handleNegativeIndex(index);
        handleOutOfBoundException(index);

        var replaced = array[index];
        set(index, number);
        return replaced;
    }

    /**
     * Insert {@code number} after {@code index} and
     * shift all numbers after it by 1 position.<br/>
     * O(n) time complexity.
     * @return {@code number} inserted.
     * @throws IllegalStateException on negative index
     * @throws IndexOutOfBoundsException on excessive index
     */
    @Override
    public int insertBefore(int index, int number) {
        handleNegativeIndex(index);
        if (index == 0 && bound == -1) return insert(number);

        handleOutOfBoundException(index);

        expandWhenFull();
        for (var i = ++bound; i > index; i--)
            set(i, array[i - 1]);
        return set(index, number);
    }

    /**
     * removeAt: remove the integer at {@code index}
     * and decrement {@code bound} by 1.<br/>
     * O(n) time complexity
     * @return the removed number.
     * @throws IllegalStateException on negative index
     * @throws IndexOutOfBoundsException on excessive index
     */
    @Override
    public int removeAt(int index) {
        handleNegativeIndex(index);
        handleOutOfBoundException(index);
        var removed = get(index);

        var newArray = new int[size = bound];
        for (var i = 0; i <= bound; i++) {
            if (i < index) newArray[i] = get(i);
            if (i > index) newArray[i - 1] = get(i);
        }
        array = newArray;
        bound--;

        return removed;
    }


    /**
     * indexOf: Find the index of the first item
     * in the array that matches {@code searchItem}.
     * If no match is found, returns -1.<br/>
     * If the array is empty, returns -1.<br/>
     * O(n) time complexity. Involves single traversal
     * of array.
     * @return an index (Integer).
     */
    @Override
    public int indexOf(int searchItem) {
        if (bound < 0) return -1;
        for (var i = 0; i < bound + 1; i++)
            if (array[i] == searchItem)
                return i;
        return -1;
    }

    /**
     * join: populate the current array with numbers
     * from another array. Will modify the current
     * array in place.<br/>
     * O(n) complexity. Involves single traversal
     * of array.
     */
    @Override
    public void join(AbstractIntArray to) {
        for (var index = 0; index < to.length(); index++) this.insert(to.get(index));
    }

    /**
     * concat: concatenate this array with another one.
     * This process creates a new array and
     * has no side effect.<br/>
     * Modifies the current array in-place.<br/>
     * O(n) complexity. Involves single traversal
     * of array.
     */
    public IntArray concat(AbstractIntArray with) {
        var newArray = new IntArray();
        newArray.join(this);
        newArray.join(with);
        return newArray;
    }

    /**
     * Reverse the array in place without creating a new one.<br/>
     * O(n) time complexity.
     * @return the same array but reversed
     */
    public IntArray reverse() {
//        [0, 1, 2, ..., length() - 1]
//         i    =>
//                  <=   length() - 1 - i
        for (var i = 0; i <= (length() / 2 - 1); i++) {
            var temp = get(i);
            set(i, get(length() - 1 - i));
            set(length() - 1 - i, temp);
        }
        return this;
    }

    @Override
    public int length() {
        return bound + 1;
    }

    /**
     * Check if the given integer exists in the array.<br/>
     * O(n) time complexity.
     * @param searchItem
     * @return if the given integer exists
     */
    @Override
    public Boolean contains(int searchItem) {
        return indexOf(searchItem) != -1;
    }

    /**
     * Get the number at the given index. If the index is
     * out of bound, throw IndexOutOfBoundsException.
     * If the index is negative, throw IllegalArgumentException.<br/>
     * O(1) time complexity.
     * @param index
     * @return
     */
    @Override
    public int get(int index) {
        handleNegativeIndex(index);
        handleOutOfBoundException(index);
        return array[index];
    }

    @Override
    public String toString() {
        return "["
                + Stream.iterate(0, n -> n + 1).limit(bound + 1)
                .map(this::get)
                .map(Object::toString)
                .collect(Collectors.joining(", "))
                + "]";
    }

    /**
     * Get an iterator instance of numbers.
     * @return An {@code IntIterator} instance
     */
    public IntIterator iterator() {
        return new IntIterator();
    }

    private void expand() {
        this.size *= 2;
        var newArray = new int[size];
        copyArrayTo(newArray);
        this.array = newArray;
    }

    private void expandWhenFull() {
        while (bound >= size - 1) expand();
    }

    private void copyArrayTo(int[] array) {
        if (bound >= -1) System.arraycopy(this.array, 0, array, 0, bound + 1);
    }

    private void handleOutOfBoundException(int index) {
        if (index > bound) throw new IndexOutOfBoundsException();
    }

    private void handleNegativeIndex(int index){
        if (index < 0) throw new IllegalArgumentException();
    }


    private int set(int index, int number) {
        if (index >= size) throw new IllegalArgumentException();
        array[index] = number;
        return number;
    }

    public class IntIterator {
        private int cursor;

        public IntIterator() {
            this.cursor = -1;
        }

        public boolean hasNext() {
            return (this.cursor < bound);
        }

        public int next() {
            return array[++this.cursor];
        }
    }
}
