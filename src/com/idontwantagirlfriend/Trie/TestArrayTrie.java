package com.idontwantagirlfriend.Trie;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestArrayTrie {
    public static ArrayTrie trie;

    public static void refreshTrie() {
        trie = new ArrayTrie();
    }

    public static class NodeTest {
        private ArrayTrie.Node node;

        @BeforeEach
        public void setUpEach() {
            node = new ArrayTrie.Node('a');
        }

        @Test
        public void getPositionOfLetters_ShouldFallBetween0And26() {
            assertEquals(0, ArrayTrie.Node.getLetterPosition('a'));
            assertEquals(25, ArrayTrie.Node.getLetterPosition('z'));
        }

        @Test
        public void getLetterOfNode() {
            assertEquals('a', node.getLetter());
        }

        @Test
        public void getChildOfEmptyNode_ShouldReturnNull() {
            assertNull(node.getChild('a'));
        }

        @Test
        public void getNonAlphabeticalChild_ShouldThrowIllegalArgumentException() {
            assertThrows(IllegalArgumentException.class, () -> node.getChild('='));
            assertThrows(IllegalArgumentException.class, () -> node.getChild(' '));
        }

        @Test
        public void getUppercaseLetterChild_ShouldThrowIllegalArgumentException() {
            assertThrows(IllegalArgumentException.class, () -> node.getChild('A'));
        }

        @Test
        public void emptyNodeHasChild_ShouldReturnFalse() {
            assertFalse(node.hasChild('b'));
        }

        @Test
        public void hasNonExistentChild_ShouldReturnFalse() {
            assertFalse(node.hasChild('b'));
        }

        @Test
        public void hasIllegalCharacterChild_ShouldThrowIllegalArgumentException() {
            assertThrows(IllegalArgumentException.class, () -> node.hasChild(' '));
        }

        @Test
        public void addOneChild() {
            node.addChild('f');
        }

        @Test
        public void addManyChildren() {
            List.of('a','m','s').forEach(node::addChild);
        }

        @Test
        public void addIllegalCharacterChild_ShouldThrowIllegalArgumentException() {
            assertThrows(IllegalArgumentException.class, () -> node.addChild(' '));
            assertThrows(IllegalArgumentException.class, () -> node.addChild('A'));
        }

        @Test
        public void addOneHasOne_ShouldReturnTrue() {
            node.addChild('m');
            assertTrue(node.hasChild('m'));
        }

        @Test
        public void addOneGetOne_ShouldReturnChild() {
            node.addChild('m');
            assertInstanceOf(ArrayTrie.Node.class, node.getChild('m'));
            assertEquals('m', node.getChild('m').getLetter());
        }

        @Test
        public void addRepetitiveCharAndGet_ShouldReturnDifferentChildObjectEachTime() {
            node.addChild('k');
            var oldChild = node.getChild('k');
            node.addChild('k');
            var newChild = node.getChild('k');
            assertEquals(oldChild.getLetter(), newChild.getLetter());
            assertNotSame(oldChild, newChild);
        }

        @Test
        public void getDefaultEOWStatus_ShouldReturnFalse() {
            assertFalse(node.getIsEOW());
        }

        @Test
        public void explicitlySetIsEOWAndGetEOWStatus_ShouldReturnTrue() {
            node.setIsEOW(true);
            assertTrue(node.getIsEOW());
        }
    }
}
