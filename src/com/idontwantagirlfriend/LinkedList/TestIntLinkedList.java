package com.idontwantagirlfriend.LinkedList;

import org.junit.jupiter.api.*;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class TestIntLinkedList {

    private static IntLinkedList list;

    private static void refreshList() {
        list = new IntLinkedList();
    }

    public static class AddTest {

        @BeforeEach
        public void setUp() {
            refreshList();
        }

        @Test
        public void addFirst_ShouldReturnAddedElement() {
            assertEquals(1, list.addFirst(1));
            assertEquals(2, list.addFirst(2));
        }

        @Test
        public void addLast_ShouldReturnAddedElement() {
            assertEquals(1, list.addLast(1));
            assertEquals(2, list.addLast(2));
        }
    }

    public static class IndexOfTest {

        @BeforeEach
        public void setUp() {
            refreshList();
        }

        @Test
        public void indexOfOnEmptyList_ShouldReturnMinusOne() {
            assertEquals(-1, list.indexOf(100));
        }

        @Test
        public void IndexOfNonExistentElement_ShouldReturnMinusOne() {
            list.addFirst(1);
            list.addLast(2);
            list.addLast(3);
            assertEquals(-1, list.indexOf(4));
        }

        @Test
        public void IndexOf_ShouldReturnIndex() {
            list.addFirst(1);
            list.addLast(2);
            list.addLast(3);
            assertEquals(0, list.indexOf(1));
            assertEquals(1, list.indexOf(2));
            assertEquals(2, list.indexOf(3));
        }

        @Test
        public void IndexOfDuplicateElements_ShouldReturnIndexOfFirstFoundElement() {
            list.addFirst(1);
            list.addLast(2);
            list.addLast(2);
            list.addLast(2);
            assertEquals(1, list.indexOf(2));
        }
    }

    public static class ContainsTest {

        @BeforeEach
        public void setUp() {
            refreshList();
        }

        @Test
        public void containsEmptyList_ShouldReturnFalse() {
            assertFalse(list.contains(5));
        }

        @Test
        public void containsNonExistentElement_ShouldReturnFalse() {
            list.addLast(50);
            assertFalse(list.contains(5));
        }

        @Test
        public void containsExistingElement_ShouldReturnTrue() {
            list.addLast(100);
            assertTrue(list.contains(100));
        }
    }

    public static class RemoveTest {

        @BeforeEach
        public void setUp() {
            refreshList();
        }

        @Test
        public void RemoveFirstOfEmptyList_ShouldThrowIllegalStateException() {
            assertThrows(IllegalStateException.class, () -> list.removeFirst());
        }

        @Test
        public void RemoveFirstAndOnlyElement_ShouldReturnRemovedElementAndEmptyList() {
            list.addFirst(5);
            assertEquals(5, list.removeFirst());
            assertEquals(0, list.size());
        }

        @Test
        public void RemoveFirstElement_ShouldReturnRemovedElement(){
            list.addLast(5);
            list.addLast(10);
            list.addLast(15);
            list.addLast(20);

            assertEquals(5, list.removeFirst());
            assertEquals(3, list.size());
            assertEquals(0, list.indexOf(10));
            assertEquals(1, list.indexOf(15));
            assertEquals(2, list.indexOf(20));
        }

        @Test
        public void RemoveLastOfEmptyList_ShouldThrowIllegalStateException() {
            assertThrows(IllegalStateException.class, () -> list.removeLast());
        }

        @Test
        public void RemoveLastAndOnlyElement_ShouldReturnRemovedElementAndEmptyList() {
            list.addLast(15);
            assertEquals(15, list.removeLast());
            assertEquals(0, list.size());
        }

        @Test
        public void RemoveLastElement_ShouldReturnRemovedElement(){
            list.addLast(5);
            list.addLast(10);
            list.addLast(15);
            list.addLast(20);

            assertEquals(20, list.removeLast());
            assertEquals(3, list.size());
            assertEquals(0, list.indexOf(5));
            assertEquals(1, list.indexOf(10));
            assertEquals(2, list.indexOf(15));
        }
    }

    public static class GetterTest {

        @BeforeEach
        public void setUp() {
            refreshList();
        }

        @Test
        public void getFirstOnEmptyList_ShouldThrowIllegalStateException() {
            assertThrows(IllegalStateException.class, () -> list.getFirst());
        }

        @Test
        public void getFirst_ShouldReturnItem() {
            list.addFirst(1);
            list.addFirst(2);
            assertEquals(2, list.getFirst());
        }


        @Test
        public void getLastOnEmptyList_ShouldThrowIllegalStateException() {
            assertThrows(IllegalStateException.class, () -> list.getLast());
        }

        @Test
        public void getLast_ShouldReturnItem() {
            list.addFirst(1);
            list.addFirst(2);
            assertEquals(1, list.getLast());
        }
    }

    public static class IteratorTest {

        @BeforeEach
        public void setUp() {
            refreshList();
        }

        @Test
        public void iterateOnEmptyArray_ShouldDoNothing() {
            var iterator = list.iterator();
            while(iterator.hasNext()) {
                iterator.next();
                fail();
            }
        }

        @Test
        public void iterate_ShouldGiveElementsSequentially() {
            Stream.iterate(0, n -> n + 1).limit(5)
                    .forEach(list::addLast);

            var i = 0;
            var iterator = list.iterator();
            while(iterator.hasNext()) {
                assertEquals(i++, iterator.next());
            }
            assertEquals(5, i);
        }
    }
}
