package com.idontwantagirlfriend.Trie;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestArrayNode {
    private Node node;

    @BeforeEach
    public void setUpEach() {
        node = new ArrayNode('a');
    }

    @Test
    public void getPositionOfLetters_ShouldFallBetween0And26() {
        assertEquals(0, ArrayNode.getLetterPosition('a'));
        assertEquals(25, ArrayNode.getLetterPosition('z'));
    }

    @Test
    public void getLetterOfNode() {
        assertEquals('a', node.getLetter());
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
    public void addRepetitiveCharAndFind_ShouldReturnDifferentChildObjectEachTime() {
        node.addChild('k');
        var oldChild = node.getChild('k');
        node.addChild('k');
        var newChild = node.getChild('k');
        assertEquals(oldChild.getLetter(), newChild.getLetter());
        assertNotSame(oldChild, newChild);
    }

    @Test
    public void emptyNodeHasNoChild_ShouldReturnTrue() {
        assertTrue(node.hasNoChild());
    }

    @Test
    public void addOneAndHasNoChild_ShouldReturnFalse() {
        node.addChild('k');
        assertFalse(node.hasNoChild());
    }

    @Test
    public void removeANonExistentChild_ShouldReturnNull() {
        assertNull(node.removeChild('k'));
        node.addChild('k');
        assertNull(node.removeChild('f'));
    }

    @Test
    public void removeAChild_ShouldReturnTheChild() {
        node.addChild('k');
        var removed = node.getChild('k');
        assertSame(removed, node.removeChild('k'));
    }

    @Test
    public void getDefaultEOWStatus_ShouldReturnFalse() {
        assertFalse(node.getEOW());
    }

    @Test
    public void explicitlySetEOWAndGetEOWStatus_ShouldReturnTrue() {
        node.setEOW(true);
        assertTrue(node.getEOW());
    }

}
