package com.idontwantagirlfriend.Tree;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

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
    public void insertAFewToTheTreeAndSeeIfEqualsWork() {
        refreshTree2();
        List.of(16, 8, 10, 1, 19, 22, 17)
                .forEach(tree::insert);
        List.of(1, 10, 17, 8, 16, 19, 22)
                .forEach(tree2::insert);
        assertEquals(
                "[10, [8, [1], null], [19, [16, null, [17]], [22]]]",
                tree.toString());
        assertEquals(
                "[10, [1, null, [8]], [17, [16], [19, null, [22]]]]",
                tree2.toString());
    }
}
