package com.idontwantagirlfriend.Tree;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestIntBST {
    private IntBST tree;
    private IntBST tree2;

    @BeforeEach
    public void refreshTree() {
        tree = new IntBST();
    }

    public void refreshTree2() {
        tree2 = new IntBST();
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

    @Test
    public void twoEmptyTreesEqual() {
        refreshTree2();
        assertTrue(tree.equals(tree2));
    }

    @Test
    public void twoFilledTreesEqual() {
        refreshTree2();
        List.of(9, 4, 6, 1, 17)
                .forEach(tree::insert);
        List.of(9, 17, 4, 1, 6)
                .forEach(tree2::insert);
        assertTrue(tree.equals(tree2));
    }

    @Test
    public void oneEmptyTreeNotEqualsToAnotherFilledTree() {
        refreshTree2();
        List.of(9, 4, 6, 1, 17).forEach(tree2::insert);

        assertFalse(tree.equals(tree2));
    }

    @Test
    public void oneFilledTreeNotEqualsToAnotherEmptyTree() {
        refreshTree2();
        List.of(9, 4, 6, 1, 17).forEach(tree2::insert);
        assertFalse(tree2.equals(tree));
    }

    @Test
    public void twoFilledTreesNotEquals() {
        refreshTree2();
        List.of(2, 3, 4, 5, 6).forEach(tree::insert);
        List.of(2, 3, 4, 5, 7).forEach(tree2::insert);

        assertFalse(tree.equals(tree2));
    }
}
