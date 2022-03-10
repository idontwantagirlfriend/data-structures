package com.idontwantagirlfriend.Heap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestBinaryMinHeap {
    private BinaryMinHeap heap;

    @BeforeEach
    public void setUp() {
        heap = new BinaryMinHeap();
    }

    @Test
    public void toString_EmptyHeap() {
        assertEquals("[]", heap.toString());
    }

    @Test
    public void insertOneAndToString() {
        heap.insert(28);
        assertEquals("[[28]]", heap.toString());
    }

    @Test
    public void insertOneAndSize() {
        heap.insert(28);
        assertEquals(1, heap.size());
    }

    @Test
    public void insertManyAndSize() {
        List.of(9, 5, 1, 7, 3, 8, 6)
                .forEach(heap::insert);
        assertEquals("[[1], [3, 5], [9, 7, 8, 6]]", heap.toString());
        assertEquals(7, heap.size());
    }

    @Test
    public void insertManyAndRemove() {
        List.of(9, 5, 1, 7, 3, 8, 6)
                .forEach(heap::insert);
        heap.remove();
        assertEquals("[[3], [6, 5], [9, 7, 8]]", heap.toString());
        heap.remove();
        assertEquals("[[5], [6, 8], [9, 7]]", heap.toString());
    }

    @Test
    public void removeFromEmptyHeap_ShouldThrowIllegalStateException() {
        assertThrows(IllegalStateException.class, () -> heap.remove());
    }

    @Test
    public void insertManyAndFind_ShouldReturnTrue() {
        List.of(9, 5, 1, 7, 3, 8, 6)
                .forEach(heap::insert);
        List.of(1, 3, 5, 6, 7, 8, 9)
                .forEach(n -> assertTrue(heap.find(n)));
    }

    @Test
    public void insertManyAndFindNonExistent_ShouldReturnFalse() {
        List.of(9, 5, 1, 7, 3, 8, 6)
                .forEach(heap::insert);
        List.of(2, 4, 10).forEach(n -> assertFalse(heap.find(n)));
    }

    @Test
    public void insertManyAndMore_ShouldSuccessfullyResize() {
        heap = new BinaryMinHeap(7);
        List.of(9, 5, 1, 7, 3, 8, 6)
                .forEach(heap::insert);
        heap.insert(0);
        assertEquals("[[0], [1, 5], [3, 7, 8, 6], [9]]", heap.toString());
    }
}
