package com.idontwantagirlfriend.Queue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestQueue {
    private AbstractQueue<Integer> queue;

    @BeforeEach
    public void setUp() {
        queue = new Queue<>();
    }

    @Test
    public void enqueueOneAndDequeueOne() {
        queue.enqueue(15);
        assertEquals(15, queue.dequeue());
    }

    @Test
    public void dequeueEmptyQueue_ShouldThrowIllegalStateException() {
        assertThrows(IllegalStateException.class, () -> queue.dequeue());
    }

    @Test
    public void enqueueAndDequeueMultiple_FirstInFirstOut() {
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);

        assertEquals(1, queue.dequeue());
        assertEquals(2, queue.dequeue());
        assertEquals(3, queue.dequeue());
    }

    @Test
    public void peekEmptyQueue_ShouldReturnNull() {
        assertNull(queue.peek());
    }

    @Test
    public void peekQueue() {
        queue.enqueue(-1);
        queue.enqueue(19);
        assertEquals(-1, queue.peek());
    }

    @Test
    public void emptyQueueIsEmpty_ShouldReturnTrue() {
        assertTrue(queue.isEmpty());
    }

    @Test
    public void enqueueAndIsEmpty_ShouldReturnFalse() {
        queue.enqueue(1);
        assertFalse(queue.isEmpty());
    }
}
