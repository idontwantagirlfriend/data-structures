package com.idontwantagirlfriend.Tree;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestBST {
    private static BST<Integer> tree;
    private static BST<Integer> tree2;

    public static void refreshTree() {
        tree = new BST<>();
    }
    public static void refreshTree2() {
        tree2 = new BST<>();
    }


    public static class BSTInterfaceTest {

        @BeforeEach
        public void setUp() {
            refreshTree();
        }

        @Test
        public void insertOneAndHeight_ShouldReturnOne() {
            tree.insert(1);
            assertEquals(0, tree.height());
        }

        @Test
        public void heightOnEmptyTree_ShouldReturnMinusOne() {
            assertEquals(-1, tree.height());
        }

        @Test
        public void heightOnSkewedBST_ShouldReturnNumberOfItems() {
            tree.insert(1);
            tree.insert(2);
            tree.insert(3);
            tree.insert(4);
            tree.insert(5);
            assertEquals(4, tree.height());
        }

        @Test
        public void insertAndHeight() {
            tree.insert(9);
            tree.insert(5);
            tree.insert(90);
            tree.insert(1);
            tree.insert(4);
            assertEquals(3, tree.height());
        }

        @Test
        public void insertOneAndFindIt_ShouldReturnTrue() {
            tree.insert(1);
            assertTrue(tree.find(1));
        }

        @Test
        public void findInEmptyTree_ShouldReturnFalse() {
            assertFalse(tree.find(1));
        }

        @Test
        public void findNonExistentValue_ShouldReturnFalse() {
            tree.insert(50);
            assertFalse(tree.find(1));
        }

        @Test
        public void insertMultipleAndFind_ShouldReturnTrue() {
            tree.insert(9);
            tree.insert(5);
            tree.insert(90);
            tree.insert(1);
            tree.insert(4);
            assertTrue(tree.find(4));
            assertTrue(tree.find(5));
            assertTrue(tree.find(1));
            assertTrue(tree.find(90));
            assertTrue(tree.find(9));
        }

        @Test
        public void insertAndToString() {
            tree.insert(9);
            tree.insert(5);
            tree.insert(90);
            tree.insert(1);
            tree.insert(4);
            assertEquals("[9, [5, [1, null, [4]], null], [90]]", tree.toString());
        }
    }

    public static class EqualsTest {

        @BeforeEach
        public void setUp() {
            refreshTree();
            refreshTree2();
        }

        @Test
        public void twoEmptyTreesEqual() {
            assertTrue(tree.equals(tree2));
        }

        @Test
        public void twoFilledTreesEqual() {
            List.of(9, 4, 6, 1, 17)
                    .forEach(tree::insert);
            List.of(9, 17, 4, 1, 6)
                    .forEach(tree2::insert);
            assertTrue(tree.equals(tree2));
        }

        @Test
        public void oneEmptyTreeNotEqualsToAnotherFilledTree() {
            List.of(9, 4, 6, 1, 17).forEach(tree2::insert);

            assertFalse(tree.equals(tree2));
        }

        @Test
        public void oneFilledTreeNotEqualsToAnotherEmptyTree() {
            List.of(9, 4, 6, 1, 17).forEach(tree2::insert);
            assertFalse(tree2.equals(tree));
        }

        @Test
        public void twoFilledTreesNotEquals() {
            List.of(2, 3, 4, 5, 6).forEach(tree::insert);
            List.of(2, 3, 4, 5, 7).forEach(tree2::insert);

            assertFalse(tree.equals(tree2));
        }
    }

    @Disabled
    public static class TraverseTest {

        @BeforeEach
        public void setUp() {
            refreshTree();
            List.of(20, 10, 6, 3, 8, 14, 30, 24, 26)
                    .forEach(tree::insert);
            System.out.println(tree);
        }

        @Test
        public void traversePreOrder() {
            System.out.println("Pre-order traversal");
            tree.traversePreOrder();
        }

        @Test
        public void traverseInOrder() {
            System.out.println("In-order traversal");
            tree.traverseInOrder();
        }

        @Test
        public void traversePostOrder() {
            System.out.println("Post-order traversal");
            tree.traversePostOrder();
        }
    }
}
