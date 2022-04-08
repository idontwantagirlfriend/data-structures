package com.idontwantagirlfriend.Sorting;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestSorter {
    private int[] unsorted;
    private int[] result = new int[] {0,1,2,3,4,5,6,7,8,9};

    @BeforeEach
    public void refreshUnsorted() {
        unsorted = new int[] {1,6,4,9,5,3,2,7,8,0};
    }

    @Test
    public void bubbleSort() {
        Sorter.bubbleSort(unsorted);
        assertArrayEquals(result, unsorted);
    }

    @Test
    public void selectionSort() {
        Sorter.selectionSort(unsorted);
        assertArrayEquals(result, unsorted);
    }

    @Test
    public void insertionSort() {
        Sorter.insertionSort(unsorted);
        assertArrayEquals(result, unsorted);
    }

    @Test
    public void mergeSort() {
        Sorter.mergeSort(unsorted);
        assertArrayEquals(result, unsorted);
    }

    @Test
    public void quickSort() {
        Sorter.quickSort(unsorted);
        assertArrayEquals(result, unsorted);
    }

    @Test
    public void countSort() {
        Sorter.countSort(unsorted);
        assertArrayEquals(result, unsorted);
    }

    @Test
    public void bucketSort() {
        Sorter.bucketSort(unsorted);
        assertArrayEquals(result, unsorted);
    }

    @Test
    public void radixSort() {
        Sorter.radixSort(unsorted);
        assertArrayEquals(result, unsorted);
    }
}
