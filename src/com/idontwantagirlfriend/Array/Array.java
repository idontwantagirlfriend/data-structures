package com.idontwantagirlfriend.Array;

import java.util.Iterator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * An array implementation using the basic array
 * type in java. <br/>
 * Automatically expand the internal array capacity
 * upon full storage.<br/>
 * A {@code bound} property is used instead of length.
 * @param <T>
 */
public class Array<T> extends AbstractArray<T>{

    protected T[] array;
    protected int size;
    protected int bound;
    /**
     * Bound is the index of the last item in this array.
     * An empty array has {@code bound} of -1.
     */


    public Array() {
        this.size = 5;
        this.bound = -1;
        this.array = (T[]) new Object[size];
    }

/**
 * insert: insert element to the end of this.array
 * and increment the bound by 1.<br/>
 * O(1) time complexity.
 * @return the inserted {@code element}.
 */
    @Override
    public T insert(T element) {
        expandWhenFull();
        return set(++bound, element);
    }

    /**
     * replace: replace the member at {@code index}
     * with {@code element} in this.array.<br/>
     * O(1) time complexity.
     * @return the replaced {@code element}.
     * @throws IllegalStateException on negative index
     * @throws IndexOutOfBoundsException on excessive index
     */
    @Override
    public T replace(int index, T element) {
        handleNegativeIndex(index);
        handleOutOfBoundException(index);

        var replaced = array[index];
        set(index, element);
        return replaced;
    }

    /**
     * Insert {@code element} after {@code index} and
     * shift all elements after it by 1 position.<br/>
     * O(n) time complexity.
     * @return {@code element} inserted.
     * @throws IllegalStateException on negative index
     * @throws IndexOutOfBoundsException on excessive index
     */
    @Override
    public T insertBefore(int index, T element) {
        handleNegativeIndex(index);
        if (index == 0 && bound == -1) return insert(element);

        handleOutOfBoundException(index);

        expandWhenFull();
        for (var i = ++bound; i > index; i--)
            set(i, array[i - 1]);
        return set(index, element);
    }

    /**
     * removeAt: remove the member at {@code index}
     * and decrement {@code bound} by 1.<br/>
     * O(n) time complexity
     * @return the removed element.
     * @throws IllegalStateException on negative index
     * @throws IndexOutOfBoundsException on excessive index
     */
    @Override
    public T removeAt(int index) {
        handleNegativeIndex(index);
        handleOutOfBoundException(index);
        var removed = get(index);

        var newArray = (T[])new Object[size = bound];
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
    public int indexOf(T searchItem) {
        if (bound < 0) return -1;
        for (var i = 0; i < bound + 1; i++)
            if (array[i] == searchItem)
                return i;
        return -1;
    }

    /**
     * Get the item at the given index. If the index is
     * out of bound, throw IndexOutOfBoundsException.
     * If the index is negative, throw IllegalArgumentException.<br/>
     * O(1) time complexity.
     * @param index
     * @return the item at {@index} position
     * @throws IllegalStateException on negative index
     * @throws IndexOutOfBoundsException on excessive index
     */
    @Override
    public T get(int index) {
        handleNegativeIndex(index);
        handleOutOfBoundException(index);
        return array[index];
    }

    /**
     * join: populate the current array with items
     * from another array. Will modify the current
     * array in place.<br/>
     * O(n) complexity. Involves single traversal
     * of array.
     */
    @Override
    public void join(AbstractArray<? extends T> with) {
        for (var element : with) this.insert(element);
    }

    /**
     * concat: concatenate this array with another one.
     * This process creates a new array and
     * has no side effect.<br/>
     * Modifies the current array in-place.<br/>
     * O(n) complexity. Involves single traversal
     * of array.
     */
    public Array<T> concat(AbstractArray<? extends T> with) {
        Array<T> newArray = new Array<>();
        newArray.join(this);
        newArray.join(with);
        return newArray;
    }

    /**
     * Reverse the array in place without creating a new one.<br/>
     * O(n) time complexity.
     * @return the same array but reversed
     */
    public Array<T> reverse() {
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
     * Check if the given item exists in the array.<br/>
     * O(n) time complexity.
     * @param searchItem
     * @return if the given item exists
     */
    @Override
    public Boolean contains(T searchItem) {
        return indexOf(searchItem) != -1;
    }

    @Override
    public String toString() {
        return "["
        + Stream.iterate(0, n -> n + 1).limit(bound + 1)
            .map(this::get)
            .map(element ->
                    element == null
                        ? "null"
                        : element.toString())
            .collect(Collectors.joining(", "))
        + "]";
    }

    public T[] toArray() {
        var result =  (T[]) new Object[bound + 1];
        System.arraycopy(array, 0, result, 0, bound + 1);
        return result;
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayIterator();
    }

    protected void expand() {
        this.size *= 2;
        var newArray = (T[]) new Object[size];
        System.arraycopy(this.array, 0, newArray, 0, bound + 1);
        this.array = newArray;
    }

    private void expandWhenFull() {
        while (bound >= size - 1) expand();
    }

    private void handleOutOfBoundException(int index) {
        if (index > bound) throw new IndexOutOfBoundsException();
    }

    private void handleNegativeIndex(int index){
        if (index < 0) throw new IllegalArgumentException();
    }


    private T set(int index, T element) {
        if (index >= size) throw new IllegalArgumentException();
        array[index] = element;
        return element;
    }

    private class ArrayIterator implements Iterator<T> {

        private int cursor;

        public ArrayIterator() {
            this.cursor = -1;
        }

        @Override
        public boolean hasNext() {
            return this.cursor < bound;
        }

        @Override
        public T next() {
            return array[++this.cursor];
        }
    }
}
