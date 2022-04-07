package com.idontwantagirlfriend.Sorting;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestSorter {
    private int[] unsorted;

    @BeforeEach
    public void refreshUnsorted() {
        unsorted = new int[] {1,6,4,9,5,3,2,7,8,0};
    }

    @Test
    public void bubbleSort() {
        Sorter.bubbleSort(unsorted);
        assertArrayEquals(new int[] {0,1,2,3,4,5,6,7,8,9}, unsorted);
    }

    @Test
    public void selectionSort() {
        Sorter.selectionSort(unsorted);
        assertArrayEquals(new int[] {0,1,2,3,4,5,6,7,8,9}, unsorted);
    }

    @Test
    public void insertionSort() {
        Sorter.insertionSort(unsorted);
        assertArrayEquals(new int[] {0,1,2,3,4,5,6,7,8,9}, unsorted);
    }

    @Test
    public void mergeSort() {
        Sorter.mergeSort(unsorted);
        assertArrayEquals(new int[] {0,1,2,3,4,5,6,7,8,9}, unsorted);
    }
}
