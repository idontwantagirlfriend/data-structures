package com.idontwantagirlfriend.Queue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestPriorityQueue {
    private PriorityQueue<Integer> priorityQueue;

    @BeforeEach
    public  void refreshQueue() {
        priorityQueue = new PriorityQueue<>();
    }

    @Test
    public void enqueueOneAndDequeueOne() {
        priorityQueue.enqueue(1, 15);
        assertEquals(15, priorityQueue.dequeue());
    }

    @Test
    public void dequeueOnEmptyQueue_ShouldThrowIllegalStateException() {
        assertThrows(IllegalStateException.class, () -> priorityQueue.dequeue());
    }

    @Test
    public void enqueueAndDequeueMultiple_LargestPriorityComesOutFirst() {
        priorityQueue.enqueue(5, 5);
        priorityQueue.enqueue(9, 9);
        priorityQueue.enqueue(1, 1);

        assertEquals(9, priorityQueue.dequeue());
        assertEquals(5, priorityQueue.dequeue());
        assertEquals(1, priorityQueue.dequeue());
    }

    @Test
    public void peekEmptyQueue_ShouldReturnNull() {
        assertNull(priorityQueue.peek());
    }

    @Test
    public void peekQueue() {
        priorityQueue.enqueue(5, 5);
        priorityQueue.enqueue(128, 128);
        priorityQueue.enqueue(9, 9);
        assertEquals(128, priorityQueue.peek());
    }

    @Test
    public void emptyQueueIsEmpty_ShouldReturnTrue() {
        assertTrue(priorityQueue.isEmpty());
    }

    @Test
    public void enqueueOneAndIsEmpty_ShouldReturnFalse() {
        priorityQueue.enqueue(15, 15);
        assertFalse(priorityQueue.isEmpty());
    }
}
