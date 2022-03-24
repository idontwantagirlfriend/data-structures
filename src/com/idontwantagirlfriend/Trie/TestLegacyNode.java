package com.idontwantagirlfriend.Trie;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class TestLegacyNode {
    private LegacyTrie.Node node;

    @BeforeEach
    public void setUp() {
        node = new LegacyTrie.Node(null);
    }

    @Test
    public void addWordAndGetChildren_ShouldAllHaveCorrectLetters() {
        node.addToSubTrees("ig");
        var iNode = node.getChild("i");
        assertEquals("i", iNode.getLetter());
        var gNode = iNode.getChild("g");
        assertEquals("g", gNode.getLetter());
        var nullNode = gNode.getChild(null);
        assertNull(nullNode.getLetter());
    }

    @Test
    public void addMultipleWordsWithCommonLettersAndGetChildren_ShouldAllHaveCorrectLetters() {
        node.addToSubTrees("ing");
        node.addToSubTrees("it");
        node.addToSubTrees("ite");
        var iNode = node.getChild("i");
        var nNode = iNode.getChild("n");
        var tNode = iNode.getChild("t");
        var eNode = tNode.getChild("e");
        assertNull(tNode.getChild(null).getLetter());
        assertEquals("g", nNode.getChild("g").getLetter());
        assertNull(eNode.getChild(null).getLetter());
    }

    @Test
    public void addEmptyString_ShouldDoNothing() {
        node.addToSubTrees("");
        assertFalse(node.findInSubtrees(""));
    }

    @Test
    public void addNullAndGetNullChild_ShouldReturnNullChild() {
        node.addToSubTrees(null);
        assertNull(node.getChild(null).getLetter());
    }

    @ParameterizedTest
    @ValueSource(strings = {"hello", "asdfdgvc", "54xcv1x"})
    public void addOneAndFindIt_ShouldReturnTrue(String source) {
        node.addToSubTrees(source);
        assertTrue(node.findInSubtrees(source));
    }

    @Test
    public void findNonExistentWords_ShouldReturnFalse() {
        node.addToSubTrees("hello");
        node.addToSubTrees("hi");
        node.addToSubTrees("salut");
        node.addToSubTrees("dag");
        assertFalse(node.findInSubtrees("salve"));
    }

    @Test
    public void findEmptyString_ShouldReturnFalse() {
        assertFalse(node.findInSubtrees(""));
        node.addToSubTrees("hello");
        node.addToSubTrees("hi");
        node.addToSubTrees("salut");
        node.addToSubTrees("dag");
        assertFalse(node.findInSubtrees(""));
    }

    @Test
    public void findNullWhenNoWordHasBeenAddedYet_ShouldReturnFalse() {
        assertFalse(node.findInSubtrees(null));
    }

    @Test
    public void findNullAfterWordsAreAdded_ShouldReturnFalseOnStemAndTrueOnEnd() {
        node.addToSubTrees("mow");
        assertFalse(node.findInSubtrees(null));
        assertFalse(node.getChild("m").findInSubtrees(null));
        assertTrue(node.getChild("m").getChild("o").getChild("w").findInSubtrees(null));
    }
}
