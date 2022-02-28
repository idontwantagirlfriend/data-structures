package com.idontwantagirlfriend.Tree;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestBST {
    private BST<Integer> tree;

    @BeforeEach
    public void refreshTree() {
        tree = new BST<>();
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
    public void insertMultipleAndFind_ShouldReturnTrue() {
        tree.insert(9);
        tree.insert(5);
        tree.insert(90);
        tree.insert(1);
        tree.insert(4);
        assertTrue(tree.find(4));
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
