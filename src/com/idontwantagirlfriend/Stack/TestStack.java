package com.idontwantagirlfriend.Stack;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestStack {
    public AbstractStack<Integer> stack;

    @BeforeEach
    public void refreshStack() {
        stack = new ArrayStack<>();
    }

    @Test
    public void pushOnceAndPopOnce() {
        stack.push(50);
        assertEquals(50, stack.pop());
    }

    @Test
    public void pushTwiceAndPopTwice_LastInFirstOut() {
        stack.push(1);
        stack.push(2);
        stack.push(3);

        assertEquals(3, stack.pop());
        assertEquals(2, stack.pop());
        assertEquals(1, stack.pop());
    }

    @Test
    public void popEmptyStack_ShouldThrowIllegalStateException() {
        assertThrows(IllegalStateException.class, () -> stack.pop());
    }

    @Test
    public void peekEmptyStack_ShouldReturnNull() {
        assertNull(stack.peek());
    }

    @Test
    public void pushOnceAndPeekOnce_ShouldReturnPeekedValue() {
        stack.push(1024);
        assertEquals(1024, stack.peek());
    }

    @Test
    public void pushTwiceAndPeek_LastInFirstOut() {
        stack.push(1);
        stack.push(2);
        assertEquals(2, stack.peek());
    }

    @Test
    public void isEmptyOnEmptyStack_ShouldReturnTrue() {
        assertTrue(stack.isEmpty());
    }

    @Test
    public void isEmptyOnNonEmptyStack_ShouldReturnFalse() {
        stack.push(10);
        assertFalse(stack.isEmpty());
    }
}
