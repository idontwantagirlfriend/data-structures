package com.idontwantagirlfriend.LinkedList;

import org.junit.jupiter.api.*;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class TestLinkedList {

    private static AbstractLinkedList<Integer> list;

    private static void refreshList() {
        list = new ArrayLinkedList<>();
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

    public static class RemoveAtTest {
        @BeforeEach
        public void setup() {
            refreshList();
        }

        @Test
        public void removeAtRegularPositions_ShouldModifyListAndReturnRemovedItems() {
            list.addLast(0);
            list.addLast(1);
            list.addLast(2);
            list.addLast(3);

            assertEquals(1, list.removeAt(1));
            assertEquals(0, list.getFirst());
            assertEquals(3, list.getLast());
            assertEquals(3, list.size());
        }

        @Test
        public void removeAtEmptyList_ShouldThrowIndexOutOfBoundsException() {
            assertThrows(IndexOutOfBoundsException.class, () -> list.removeAt(1));
        }

        @Test
        public void removeAtOutOfBoundPosition_ShouldThrowIndexOutOfBoundsException() {
            list.addFirst(50);
            list.addLast(60);
            list.addLast(70);
            assertThrows(IndexOutOfBoundsException.class, () -> list.removeAt(3));
        }

        @Test
        public void removeAtNegativePosition_ShouldThrowIllegalArgumentException() {
            assertThrows(IllegalArgumentException.class, () -> list.removeAt(-1));
        }

        @Test
        public void removeAtFirstItem_OneItemInList_ShouldReturnFirstItem() {
            list.addLast(1);
            assertEquals(1, list.removeAt(0));
            assertTrue(list.isEmpty());
        }

        @Test
        public void removeAtFirstItem_MultipleItemsInList_ShouldReturnFirstItem() {
            list.addLast(0);
            list.addLast(1);
            list.addLast(2);
            list.addLast(3);

            assertEquals(0, list.removeAt(0));
            assertEquals(1, list.getFirst());
            assertEquals(3, list.getLast());
            assertEquals(3, list.size());
        }

        @Test
        public void removeAtLastItem_OneItemInList_ShouldReturnLastItem() {
            list.addLast(0);
            assertEquals(0, list.removeAt(0));
            assertTrue(list.isEmpty());
        }

        @Test
        public void removeAtLastItem_MultipleItems_ShouldReturnLastItem() {
            list.addLast(0);
            list.addLast(1);
            list.addLast(2);
            list.addLast(3);

            assertEquals(3, list.removeAt(3));
            assertEquals(0, list.getFirst());
            assertEquals(2, list.getLast());
            assertEquals(3, list.size());
        }
    }

    public static class GetterTest {

        @BeforeEach
        public void setUp() {
            refreshList();
        }

        @Test
        public void getFirstOnEmptyList_ShouldReturnNull() {
            assertNull(list.getFirst());
        }

        @Test
        public void getFirst_ShouldReturnItem() {
            list.addFirst(1);
            list.addFirst(2);
            assertEquals(2, list.getFirst());
        }


        @Test
        public void getLastOnEmptyList_ShouldReturnNull() {
            assertNull(list.getLast());
        }

        @Test
        public void getLast_ShouldReturnItem() {
            list.addFirst(1);
            list.addFirst(2);
            assertEquals(1, list.getLast());
        }
    }

    public static class ForLoopTest {

        @BeforeEach
        public void setUp() {
            refreshList();
        }

        @Test
        public void forLoopOnEmptyList_ShouldDoNothing() {
            for (var element : list) {
                fail();
            }
        }

        @Test
        public void forLoop_ShouldGiveElementsSequentially() {
            Stream.iterate(0, n -> n + 1).limit(5)
                    .forEach(list::addLast);

            var i = 0;
            for (var element : list) {
                assertEquals(i++, element);
            }
            assertEquals(5, i);
        }
    }
}
