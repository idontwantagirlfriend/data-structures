package com.idontwantagirlfriend.Array;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class TestArray {
    private static Array<Integer> arr;
    private static Array<Integer> arr2;

    private static void refreshArr() {
        arr = new Array<>();
    }
    private static void refreshArr2() {
        arr2 = new Array<>();
    }

    public static class InsertTest {

        @BeforeEach
        public void setUp() {
            refreshArr();
        }

        @Test
        public void insertOneElement_ShouldReturnElement() {
            var result = arr.insert(3);
            assertEquals(3, result);
        }

        @Test
        public void insertOneElementAndGetIndex0_ShouldReturnElement() {
            arr.insert(55);
            assertEquals(55, arr.get(0));
        }

        @Test
        public void insertMultipleElementsAndGetByIndex_ShouldAllReturnRightElements() {
            arr.insert(4);
            arr.insert(89);
            arr.insert(492);
            arr.insert(10);
            assertEquals(4, arr.get(0));
            assertEquals(89, arr.get(1));
            assertEquals(492, arr.get(2));
            assertEquals(10, arr.get(3));
        }
    }

    public static class GetTest {

        @BeforeEach
        public void setUp() {
            refreshArr();
        }

        @Test
        public void getFromEmptyArray_ShouldThrowIndexOutOfBoundsException() {
            assertThrows(IndexOutOfBoundsException.class, () -> arr.get(0));
        }

        @Test
        public void getOutOfBoundIndex_ShouldThrowIndexOutOfBoundsException() {
            arr.insert(1);

            assertEquals(0, arr.indexOf(1));
            assertThrows(IndexOutOfBoundsException.class, () -> arr.get(1));
        }

        @Test
        public void getNegativeIndex_ShouldThrowIllegalArgumentException() {
            assertThrows(IllegalArgumentException.class, () -> arr.get(-1));

            arr.insert(10);
            assertThrows(IllegalArgumentException.class, () -> arr.get(-1));
        }
    }

    public static class IndexOfTest {

        @BeforeEach
        public void setUp() {
            refreshArr();
        }


        @Test
        public void indexOfElement_ShouldReturnIndex() {
            arr.insert(1);
            arr.insert(12);
            arr.insert(123);
            assertEquals(1, arr.indexOf(12));
        }

        @Test
        public void indexOfDuplicateElement_ShouldReturnIndexOfFirstOccurrence() {
            arr.insert(1);
            arr.insert(2);
            arr.insert(3);
            arr.insert(3);
            arr.insert(3);
            assertEquals(2, arr.indexOf(3));
        }

        @Test
        public void indexOfNonExistentElement_ShouldReturnMinusOne() {
            arr.insert(1);
            arr.insert(5);
            arr.insert(9);
            assertEquals(-1, arr.indexOf(90));
        }
    }

    public static class InsertBeforeTest {

        @BeforeEach
        public void setUp() {
            refreshArr();
        }

        @Test
        public void insertBeforeZeroIndex_ShouldReturnInsertedElementAndChangeArrayCorrectly() {
            assertEquals(1, arr.insertBefore(0, 1));
            assertEquals(2, arr.insertBefore(0, 2));
            assertEquals(3, arr.insertBefore(0, 3));

            assertEquals(3, arr.get(0));
            assertEquals(2, arr.get(1));
            assertEquals(1, arr.get(2));
        }

        @Test
        public void insertBeforeNonZeroIndex_ShouldChangeArrayCorrectly() {
            arr.insert(10);
            arr.insert(11);

            arr.insertBefore(1, 12);
            arr.insertBefore(1, 13);

            assertEquals(10, arr.get(0));
            assertEquals(13, arr.get(1));
            assertEquals(12, arr.get(2));
            assertEquals(11, arr.get(3));
        }

        @Test
        public void insertBeforeNegativeIndex_ShouldThrowIllegalArgumentException() {
            arr.insert(12);
            assertThrows(IllegalArgumentException.class, () -> arr.insertBefore(-1, 8));
        }

        @Test
        public void insertBeforeAnIndexOutOfBound_ShouldThrowIndexOutOfBoundsException() {
            arr.insert(12);
            assertThrows(IndexOutOfBoundsException.class, () -> arr.insertBefore(1, 13));

            assertEquals(13, arr.insertBefore(0, 13));
        }
    }

    public static class RemoveAtTest {

        @BeforeEach
        public void setUp() {
            refreshArr();
        }

        @Test
        public void removeAtNegativeIndex_ShouldThrowIllegalArgumentException() {
            assertThrows(IllegalArgumentException.class, () -> arr.removeAt(-1));
            arr.insert(6);
            assertThrows(IllegalArgumentException.class, () -> arr.removeAt(-1));
        }

        @Test
        public void removeAtCorrectIndex_ShouldReturnRemovedElements() {
            arr.insert(1);
            arr.insert(2);
            arr.insert(3);
            arr.insert(4);

//        [1, 2, 3, 4]
            assertEquals(2, arr.removeAt(1));
//        [1, 3, 4]
            assertEquals(4, arr.get(2));

            assertEquals(3, arr.removeAt(1));
//        [1, 4]
            assertEquals(1, arr.get(0));
            assertEquals(4, arr.get(1));
            assertEquals(4, arr.removeAt(1));
        }

        @Test
        public void removeAtExceedingIndex_ShouldThrowIndexOutOfBoundsException() {
            arr.insert(1);
            arr.insert(2);
            arr.insert(3);
            arr.insert(4);
            assertThrows(IndexOutOfBoundsException.class, () -> arr.removeAt(4));
            assertEquals(4, arr.removeAt(3));
        }
    }

    public static class ReplaceAtTest {

        @BeforeEach
        public void setUp() {
            refreshArr();
        }

        @Test
        public void replaceAtNegativeIndex_ShouldThrowIllegalArgumentException() {
            assertThrows(IllegalArgumentException.class, () -> arr.replace(-1, 5));
        }

        @Test
        public void replace_ShouldReturnReplacedElements() {
            arr.insert(1);
            arr.insert(2);
            arr.insert(3);
            arr.insert(4);
            arr.insert(5);
            assertEquals(4, arr.replace(3, 1024));

            assertEquals(1, arr.get(0));
            assertEquals(2, arr.get(1));
            assertEquals(3, arr.get(2));
            assertEquals(1024, arr.get(3));
            assertEquals(5, arr.get(4));
        }

        @Test
        public void replaceAtExceedingIndex_ShouldThrowIndexOutOfBoundsException() {
            arr.insert(1);
            arr.insert(2);
            arr.insert(3);
            arr.insert(4);
            arr.insert(5);
            assertThrows(IndexOutOfBoundsException.class, () -> arr.replace(5, 1024));
            assertEquals(5, arr.replace(4, 1024));
        }
    }

    public static class ConcatTest {

        @BeforeEach
        public void setUp() {
            refreshArr();
            refreshArr2();
        }

        @Test
        public void concatEmptyArrayWithEmptyArray_ShouldReturnNewEmptyArray() {
            assertEquals(0, arr.length());
            var concated = arr.concat(arr2);
            assertNotEquals(arr, concated);
            assertEquals(0, concated.length());
        }

        @Test
        public void concatNonEmptyArrayWithEmptyOne_ShouldKeepTheSameArray() {
            arr.insert(1);
            assertEquals(arr.length(), arr.concat(arr2).length());
        }

        @Test
        public void concatEmptyArrayWithNonEmptyArray() {
            arr2.insert(1);
            arr2.insert(2);

            var concated = arr.concat(arr2);
            assertEquals(2, concated.length());
            assertEquals(1, concated.get(0));
            assertEquals(2, concated.get(1));
        }

        @Test
        public void concatArrays() {
            arr.insert(1);
            arr.insert(2);
            arr2.insert(3);
            arr2.insert(4);

            var concated = arr.concat(arr2);
            assertEquals(1, concated.get(0));
            assertEquals(2, concated.get(1));
            assertEquals(3, concated.get(2));
            assertEquals(4, concated.get(3));
        }
    }

    public static class JoinTest {

        @BeforeEach
        public void setUp() {
            refreshArr();
            refreshArr2();
        }

        @Test
        public void joinEmptyArrayWithEmptyArray_ShouldKeepTheSameArrayEmpty() {
            assertEquals(0, arr.length());
            var hash1 = arr.hashCode();
            arr.join(arr2);
            var hash2 = arr.hashCode();
            assertEquals(hash1, hash2);
            assertEquals(0, arr.length());
        }

        @Test
        public void joinNonEmptyArrayWithEmptyOne_ShouldKeepTheSameArray() {
            arr.insert(1);
            var length = arr.length();
            arr.join(arr2);
            assertEquals(length, arr.length());
        }

        @Test
        public void joinEmptyArrayWithNonEmptyArray() {
            arr2.insert(1);
            arr2.insert(2);

            arr.join(arr2);
            assertEquals(2, arr.length());
            assertEquals(1, arr.get(0));
            assertEquals(2, arr.get(1));
        }

        @Test
        public void joinArrays() {
            arr.insert(1);
            arr.insert(2);
            arr2.insert(3);
            arr2.insert(4);

            arr.join(arr2);
            assertEquals(1, arr.get(0));
            assertEquals(2, arr.get(1));
            assertEquals(3, arr.get(2));
            assertEquals(4, arr.get(3));
        }
    }

    public static class ReverseTest {
        @BeforeEach
        public void setUp() {
            refreshArr();
        }

        @Test
        public void reverseEmptyArray_ShouldReturnTheSameEmptyArray() {
            assertSame(arr, arr.reverse());
        }

        @Test
        public void reverseArray_ShouldChangeTheOrder() {
            arr.insert(1);
            arr.insert(2);
            arr.insert(3);
            arr.reverse();
            assertEquals(3, arr.get(0));
            assertEquals(2, arr.get(1));
            assertEquals(1, arr.get(2));
        }
    }

    public static class ForLoopTest {

        @BeforeEach
        public void setUp() {
            refreshArr();
        }

        @Test
        public void forLoopOnEmptyArray_ShouldDoNothing() {
            for (var element : arr) {
                fail();
            }
        }

        @Test
        public void forLoop_ShouldGiveElementsSequentially() {
            Stream.iterate(0, n -> n + 1).limit(5)
                    .forEach(arr::insert);

            var i = 0;
            for (var element : arr) {
                assertEquals(i++, element);
            }
            assertEquals(5, i);
        }
    }

}
