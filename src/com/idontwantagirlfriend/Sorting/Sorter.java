package com.idontwantagirlfriend.Sorting;

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

    private static void arraySwap(int[] arr, int pos1, int pos2) {
        if (pos1 == pos2) return;
        var temp = arr[pos1];
        arr[pos1] = arr[pos2];
        arr[pos2] = temp;
    }
}
