package com.idontwantagirlfriend.Trie;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestArrayNode {
    private ArrayNode arrayNode;

    @BeforeEach
    public void setUpEach() {
        arrayNode = new ArrayNode('a');
    }

    @Test
    public void getPositionOfLetters_ShouldFallBetween0And26() {
        assertEquals(0, ArrayNode.getLetterPosition('a'));
        assertEquals(25, ArrayNode.getLetterPosition('z'));
    }

    @Test
    public void getLetterOfNode() {
        assertEquals('a', arrayNode.getLetter());
    }

    @Test
    public void emptyNodeHasChild_ShouldReturnFalse() {
        assertFalse(arrayNode.hasChild('b'));
    }

    @Test
    public void hasNonExistentChild_ShouldReturnFalse() {
        assertFalse(arrayNode.hasChild('b'));
    }

    @Test
    public void hasIllegalCharacterChild_ShouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> arrayNode.hasChild(' '));
    }

    @Test
    public void addOneChild() {
        arrayNode.addChild('f');
    }

    @Test
    public void addManyChildren() {
        List.of('a','m','s').forEach(arrayNode::addChild);
    }

    @Test
    public void addIllegalCharacterChild_ShouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> arrayNode.addChild(' '));
        assertThrows(IllegalArgumentException.class, () -> arrayNode.addChild('A'));
    }

    @Test
    public void addOneHasOne_ShouldReturnTrue() {
        arrayNode.addChild('m');
        assertTrue(arrayNode.hasChild('m'));
    }

    @Test
    public void addRepetitiveCharAndFind_ShouldReturnDifferentChildObjectEachTime() {
        arrayNode.addChild('k');
        var oldChild = arrayNode.getChild('k');
        arrayNode.addChild('k');
        var newChild = arrayNode.getChild('k');
        assertEquals(oldChild.getLetter(), newChild.getLetter());
        assertNotSame(oldChild, newChild);
    }

    @Test
    public void emptyNodeHasNoChild_ShouldReturnTrue() {
        assertTrue(arrayNode.hasNoChild());
    }

    @Test
    public void addOneAndHasNoChild_ShouldReturnFalse() {
        arrayNode.addChild('k');
        assertFalse(arrayNode.hasNoChild());
    }

    @Test
    public void removeANonExistentChild_ShouldReturnNull() {
        assertNull(arrayNode.removeChild('k'));
        arrayNode.addChild('k');
        assertNull(arrayNode.removeChild('f'));
    }

    @Test
    public void removeAChild_ShouldReturnTheChild() {
        arrayNode.addChild('k');
        var removed = arrayNode.getChild('k');
        assertSame(removed, arrayNode.removeChild('k'));
    }

    @Test
    public void getDefaultEOWStatus_ShouldReturnFalse() {
        assertFalse(arrayNode.getEOW());
    }

    @Test
    public void explicitlySetEOWAndGetEOWStatus_ShouldReturnTrue() {
        arrayNode.setEOW(true);
        assertTrue(arrayNode.getEOW());
    }

}
