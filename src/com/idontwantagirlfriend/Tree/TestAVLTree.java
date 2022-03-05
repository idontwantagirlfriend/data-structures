package com.idontwantagirlfriend.Tree;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class TestAVLTree {
    private AVLTree<Integer> tree;
    private AVLTree<Integer> tree2;

    @BeforeEach
    public void refreshTree() {
        tree = new AVLTree<>();
    }

    public void refreshTree2() {
        tree2 = new AVLTree<>();
    }

    @Test
    public void insertAFewToTheTreeAndSeeIfTheOrderIsCorrect() {
//      This sequence of numbers is from an exercise, and
//      its insertion involves all of LL, LR, RR and RL rotations.
        Stream.of(8, 9, 10, 2, 1, 5, 3, 6, 4, 7, 11, 12)
                .forEach(tree::insert);
        assertEquals(
                "[5, [3, [2, [1], null], [4]], [8, [6, null, [7]], [10, [9], [11, null, [12]]]]]",
                tree.toString());
    }

    @Test
    public void height_ShouldReturnInt() {
        Stream.of(8, 9, 10, 2, 1, 5, 3, 6, 4, 7, 11, 12)
                .forEach(tree::insert);
        assertEquals(4, tree.height());
    }

    @Test
    public void heightOnEmptyTree_ShouldReturnMinusOne() {
        assertEquals(-1, tree.height());
    }

    @Test
    public void findExistentItems_ShouldReturnTrue() {
        Stream.of(8, 9, 10, 2, 1, 5, 3, 6, 4, 7, 11, 12)
                .forEach(tree::insert);
        Stream.of(8, 9, 10, 2, 1, 5, 3, 6, 4, 7, 11, 12)
                .forEach(n -> assertTrue(tree.find(n)));
    }
    @Test
    public void findNonExistentItems_ShouldReturnFalse() {
        Stream.of(8, 9, 10, 2, 1, 5, 3, 6, 4, 7, 11, 12)
                .forEach(tree::insert);
        assertFalse(tree.find(13));
    }

    @Test
    public void equalsTwoEquivalentTrees_ShouldReturnTrue() {
        refreshTree2();
        Stream.of(8, 9, 10, 2, 1, 5, 3, 6, 4, 7, 11, 12)
                .forEach(tree::insert);
        Stream.of(5, 3, 8, 2, 6, 10, 4, 1, 7, 9, 11, 12)
                .forEach(tree2::insert);
        assertTrue(tree.equals(tree2));
    }
}
