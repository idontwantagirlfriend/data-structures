package com.idontwantagirlfriend.Sorting;

import com.idontwantagirlfriend.Array.IntArray;
import com.idontwantagirlfriend.LinkedList.IntLinkedList;

import java.util.Arrays;

public class Sorter {
    public static void bubbleSort(int[] unsorted) {
        for (var i = 0; i < unsorted.length; i++) {
            var continueSorting = false;
            for (var j = 1; j < unsorted.length; j++) {
                if (unsorted[j] < unsorted[j - 1]) {
                    if (!continueSorting) continueSorting = true;
                    arraySwap(unsorted, j, j - 1);
                }
            }
            if (!continueSorting) return;
        }
    }

    public static void selectionSort(int[] unsorted) {
        for (var i = 0; i < unsorted.length; i++) {
            var minimumPos = i;
            for (var j = i + 1; j < unsorted.length; j++)
                if (unsorted[j] < unsorted[minimumPos]) minimumPos = j;
            arraySwap(unsorted, i, minimumPos);
        }
    }

    public static void insertionSort(int[] unsorted) {
        for (var i = 0; i < unsorted.length; i++) {
            for (var j = i - 1; j >= 0; j--) {
                if (unsorted[j + 1] < unsorted[j]) {
                    arraySwap(unsorted, j + 1, j);
                } else break;
            }
        }
    }

    public static void mergeSort(int[] unsorted) {
        if (unsorted.length == 1) return;
        var length = unsorted.length;
        var left = Arrays.copyOfRange(unsorted, 0, length / 2);
        var right = Arrays.copyOfRange(unsorted, length / 2, length);
        mergeSort(left);
        mergeSort(right);

        merge(left, right, unsorted);
    }

    private static void merge(int[] left, int[] right, int[] source) {
        var cursor = 0;
        var rightCursor = 0;
        for (int i : left) {
            while (rightCursor < right.length && i > right[rightCursor])
                source[cursor++] = right[rightCursor++];
            source[cursor++] = i;
        }
        for (var i = rightCursor; i < right.length; i++) source[cursor++] = right[i];
    }

    public static void quickSort(int[] unsorted) {
        quickSort(unsorted, 0, unsorted.length);
    }

    private static void quickSort(int[] source, int partitionStarts, int partitionEnds) {
        if (partitionEnds - partitionStarts < 2) return;
        var boundary = partitionStarts - 1;

        for (var i = partitionStarts; i < partitionEnds; i++)
            if (source[i] < source[partitionEnds - 1]
                    || i == partitionEnds - 1)
                move(source, i, ++boundary);

        quickSort(source, partitionStarts, boundary);
        quickSort(source, boundary, partitionEnds);
    }

    public static void countSort(int[] source) {
        var maximum = 0;
        for (var i : source) {
            if (i < 0) throw new IllegalArgumentException("Can't use count sort for negative values!");
            maximum = Math.max(maximum, i);
        }
        var axis = new IntLinkedList[maximum + 1];
        for (var i : source) {
            if (axis[i] == null) axis[i] = new IntLinkedList();
            axis[i].addFirst(i);
        }

        var cursor = 0;
        for (var slot : axis) for (var number : slot) source[cursor++] = number;
    }

    public static void bucketSort(int[] source) {
        if (source.length < 1) return;
        var minimum = source[0];
        var maximum = source[0];
        for (var i : source) {
            minimum = Math.min(minimum, i);
            maximum = Math.max(maximum, i);
        }
        var NUMBER_OF_BUCKETS = 5;
        int bucketSize = (int) Math.ceil((double)(maximum - minimum) / (double)NUMBER_OF_BUCKETS);
        var buckets = new IntArray[NUMBER_OF_BUCKETS];

        for (var i : source) {
            var index = (i - minimum) / bucketSize;
            if (buckets[index] == null) buckets[index] = new IntArray();
            buckets[index].insert(i);
        }

        var cursor = 0;
        for (var bucket : buckets) {
            var numbers = bucket.toArray();
            mergeSort(numbers);
            System.arraycopy(numbers, 0, source, cursor, numbers.length);
            cursor += numbers.length;
        }
    }

    public static void radixSort(int[] source) {
        var maximum = 0;
        for (var i : source) {
            if (i < 0) throw new IllegalArgumentException("Can't use radix sort for negative values!");
            maximum = Math.max(maximum, i);
        }
        var NUMBER_OF_RADIX = (int)Math.log10(maximum) + 1;

        for (var n = 0; n < NUMBER_OF_RADIX; n++) {
            var axis = new IntArray[10];
            for (var number : source) {
                var digit = getDigit(number, n);
                if (axis[digit] == null) axis[digit] = new IntArray();
                axis[digit].insert(number);
            }

            var cursor = 0;
            for (var slot : axis) for (var number : slot.toArray()) source[cursor++] = number;
        }
    }

    public static int getDigit(int number, int digitPos) {
        return (number / ((int) Math.pow(10, digitPos))) % 10;
    }

    private static void move(int[] src, int srcPos, int destPos) {
        if (srcPos == destPos) return;
        var rearranged = src[srcPos];
        if (srcPos > destPos) {
            System.arraycopy(src, destPos, src, destPos + 1, srcPos - destPos);
        } else {
            System.arraycopy(src, srcPos + 1, src, srcPos, destPos - srcPos);
        }
        src[destPos] = rearranged;
    }

    private static void arraySwap(int[] arr, int pos1, int pos2) {
        if (pos1 == pos2) return;
        var temp = arr[pos1];
        arr[pos1] = arr[pos2];
        arr[pos2] = temp;
    }
}
