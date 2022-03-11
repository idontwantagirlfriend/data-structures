package com.idontwantagirlfriend.Heap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class TestIntMinHeap {
    private IntMinHeap heap;

    @BeforeEach
    public void setUp() {
        heap = new IntMinHeap();
    }

    @Test
    public void insertOneRemoveOne() {
        heap.insert(50, 100);
        assertEquals(50, heap.remove());
    }

    @Test
    public void insertNoneAndRemove_ShouldThrowIllegalStateException() {
        assertThrows(IllegalStateException.class, () -> heap.remove());
    }

    @Test
    public void insertManyAndRemove_ShouldReturnMinimalPriorityValue() {
        heap.insert(1048576, 0);
        List.of(8,4,6,2,9,7,10,54,13)
                .forEach(n -> heap.insert(n, n));
        assertEquals(1048576, heap.remove());
    }

    @Test
    public void insertNoneAndToString_ShouldReturnEmptySquareBrackets() {
        assertEquals("[]", heap.toString());
    }

    @Test
    public void insertOneAndToString() {
        heap.insert(1, 42);
        assertEquals("[[1(42)]]", heap.toString());
    }

    @Test
    public void insertManyAndToString() {
        heap.insert(9, 1);
        heap.insert(10, 0);
        heap.insert(4, 6);
        heap.insert(3, 7);
        heap.insert(0, 10);
        heap.insert(17, 0);
        assertEquals("[[10(0)], [9(1), 17(0)], [3(7), 0(10), 4(6)]]", heap.toString());
    }


    public static class HelperMethodTest {
        @Test
        public void getTheStartingIndexOfFirstLevel_ShouldReturn0() {
            assertEquals(0, IntMinHeap.getFirstIndexOfLevel(0));
        }

        @Test
        public void getTheStartingIndexOfGivenLevel() {
            assertEquals(1023, IntMinHeap.getFirstIndexOfLevel(10));
        }

        @Test
        public void getTheParentIndexOfRootIndex_ShouldReturnMinusOne() {
            assertEquals(-1, IntMinHeap.getParent(0));
        }

        @Test
        public void getTheParentIndexOfGivenChildIndex() {
            assertEquals(512, IntMinHeap.getParent(1025));
        }

        @Test
        public void getTheFirstChildIndexOfRoot_ShouldReturn1() {
            assertEquals(1, IntMinHeap.getFirstChild(0));
        }

        @Test
        public void getTheFirstChildIndexOfGivenParentIndex() {
            assertEquals(1025, IntMinHeap.getFirstChild(512));
        }

        @Test
        public void minusValueIntoHelperMethods_ShouldAllReturnMinusOne() {
            assertEquals(-1, IntMinHeap.getFirstIndexOfLevel(-1));
            assertEquals(-1, IntMinHeap.getParent(-1));
            assertEquals(-1, IntMinHeap.getFirstChild(-1));
        }
    }
}
