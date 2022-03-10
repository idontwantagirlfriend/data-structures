package com.idontwantagirlfriend.Heap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestBinaryMaxHeap {
    private BinaryMaxHeap heap;

    @BeforeEach
    public void setUp() {
        heap = new BinaryMaxHeap();
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
        assertEquals("[[9], [7, 8], [5, 3, 1, 6]]", heap.toString());
        assertEquals(7, heap.size());
    }

    @Test
    public void insertManyAndRemove() {
        List.of(9, 5, 1, 7, 3, 8, 6)
                .forEach(heap::insert);
        heap.remove();
        assertEquals("[[8], [7, 6], [5, 3, 1]]", heap.toString());
        heap.remove();
        assertEquals("[[7], [5, 6], [1, 3]]", heap.toString());
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
        heap = new BinaryMaxHeap(7);
        List.of(9, 5, 1, 7, 3, 8, 6)
                .forEach(heap::insert);
        heap.insert(10);
        assertEquals("[[10], [9, 8], [7, 3, 1, 6], [5]]", heap.toString());
    }

    public static class HelperMethodTest {
        @Test
        public void getTheLevelOfRoot_ShouldReturn0() {
            assertEquals(0, BinaryMaxHeap.getLevel(0));
        }

        @Test
        public void getTheLevelOfGivenIndex() {
            assertEquals(3, BinaryMaxHeap.getLevel(7));
        }

        @Test
        public void getTheStartingIndexOfFirstLevel_ShouldReturn0() {
            assertEquals(0, BinaryMaxHeap.getLevelStartingIndex(0));
        }

        @Test
        public void getTheStartingIndexOfGivenLevel() {
            assertEquals(1023, BinaryMaxHeap.getLevelStartingIndex(10));
        }

        @Test
        public void getTheParentIndexOfRootIndex_ShouldReturnMinusOne() {
            assertEquals(-1, BinaryMaxHeap.getParentIndex(0));
        }

        @Test
        public void getTheParentIndexOfGivenChildIndex() {
            assertEquals(512, BinaryMaxHeap.getParentIndex(1025));
        }

        @Test
        public void getTheFirstChildIndexOfRoot_ShouldReturn1() {
            assertEquals(1, BinaryMaxHeap.getFirstChildIndex(0));
        }

        @Test
        public void getTheFirstChildIndexOfGivenParentIndex() {
            assertEquals(1025, BinaryMaxHeap.getFirstChildIndex(512));
        }

        @Test
        public void minusValueIntoHelperMethods_ShouldAllReturnMinusOne() {
            assertEquals(-1, BinaryMaxHeap.getLevel(-1));
            assertEquals(-1, BinaryMaxHeap.getLevelStartingIndex(-1));
            assertEquals(-1, BinaryMaxHeap.getParentIndex(-1));
            assertEquals(-1, BinaryMaxHeap.getFirstChildIndex(-1));
        }

        @Test
        public void heapifyEmpty_ShouldReturnEmpty() {
            var arr = new int[] {};
            BinaryMaxHeap.heapify(arr);
            assertArrayEquals(new int[] {}, arr);
        }

        @Test
        public void heapifyGivenArray() {
            var arr = new int[] {5, 3, 8, 4, 1, 2};
            BinaryMaxHeap.heapify(arr);
            assertArrayEquals(new int[] {8, 4, 5, 3, 1, 2}, arr);
        }

        @Test
        public void heapifyAscendingOrderArray() {
            var arr = new int[] {1, 2, 3, 4, 5, 6};
            BinaryMaxHeap.heapify(arr);
            assertArrayEquals(new int[] {6, 5, 3, 4, 2, 1}, arr);
        }

        @Test
        public void isMaxHeap() {
            assertTrue(BinaryMaxHeap.isBinaryMaxHeap(new int[] {8, 4, 7, 3, 1, 5, 6}));
            assertTrue(BinaryMaxHeap.isBinaryMaxHeap(new int[] {}));
            assertFalse(BinaryMaxHeap.isBinaryMaxHeap(new int[] {8, 4, 7, 3, 5, 1, 6}));
        }

        @Test
        public void getTheLargestValueOfAnArray() {
            var arr = new int[] {5, 3, 8, 4, 1, 2};
            assertEquals(8, BinaryMaxHeap.getKthLargest(1, arr));
        }

        @Test
        public void getThe0thLargestValue_ShouldThrowIllegalArgumentException() {
            var arr = new int[] {5, 3, 8, 4, 1, 2};
            assertThrows(
                    IllegalArgumentException.class,
                    () -> BinaryMaxHeap.getKthLargest(0, arr));
        }
    }
}