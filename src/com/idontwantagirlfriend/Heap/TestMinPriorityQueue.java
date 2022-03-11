package com.idontwantagirlfriend.Heap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestMinPriorityQueue {
    private MinPriorityQueue queue;

    @BeforeEach
    public void setUp() {
        queue = new MinPriorityQueue();
    }

    @Test
    public void emptyQueueIsEmpty_ShouldReturnTrue() {
        assertTrue(queue.isEmpty());
    }

    @Test
    public void addAndIsEmpty_ShouldReturnFalse() {
        queue.add(0);
        assertFalse(queue.isEmpty());
    }

    @Test
    public void addOne() {
        queue.add(10);
        assertEquals("[10]", queue.toString());
    }

    @Test
    public void addMany() {
        List.of(1,9,5,3,5,7,5).forEach(queue::add);
        assertEquals("[9, 7, 5, 5, 5, 3, 1]", queue.toString());
    }

    @Test
    public void addOneRemoveOne() {
        queue.add(10);
        assertEquals(10, queue.remove());
    }

    @Test
    public void addManyRemoveMany_ShouldReturnValuesInAscendingOrder() {
        List.of(9,9,0,5,1,3,8,1,5).forEach(queue::add);
        assertEquals(0, queue.remove());
        assertEquals(1, queue.remove());
        assertEquals(1, queue.remove());
        assertEquals(3, queue.remove());
        assertEquals(5, queue.remove());
        assertEquals(5, queue.remove());
        assertEquals(8, queue.remove());
        assertEquals(9, queue.remove());
        assertEquals(9, queue.remove());
        assertTrue(queue.isEmpty());
    }

    @Test
    public void removeFromEmptyQueue_ShouldThrowIllegalStateException() {
        assertThrows(IllegalStateException.class, () -> queue.remove());
    }
}
