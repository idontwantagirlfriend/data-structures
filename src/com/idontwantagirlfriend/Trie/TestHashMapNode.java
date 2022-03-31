package com.idontwantagirlfriend.Trie;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestHashMapNode {
    private HashMapNode node;

    @BeforeEach
    public void setUpEach() {
        node = new HashMapNode('a');
    }

    @Test
    public void getLetterOfNode() {
        assertEquals('a', node.getLetter());
    }

    @Test
    public void emptyNodeHasChild_ShouldReturnFalse() {
        assertFalse(node.contains('b'));
    }

    @Test
    public void hasNonExistentChild_ShouldReturnFalse() {
        assertFalse(node.contains('b'));
    }

    @Test
    public void hasIllegalCharacterChild_ShouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> node.contains(' '));
    }

    @Test
    public void getNonExistentChild_ShouldReturnNull() {
        assertNull(node.getChild('w'));
    }

    @Test
    public void getIllegalCharacter_ShouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> node.getChild('A'));
        assertThrows(IllegalArgumentException.class, () -> node.getChild('?'));
    }

    @Test
    public void addOneChild() {
        node.add('f');
    }

    @Test
    public void addManyChildren() {
        List.of('a','m','s').forEach(node::add);
    }

    @Test
    public void addIllegalCharacterChild_ShouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> node.add(' '));
        assertThrows(IllegalArgumentException.class, () -> node.add('A'));
    }

    @Test
    public void addOneHasOne_ShouldReturnTrue() {
        node.add('m');
        assertTrue(node.contains('m'));
    }

    @Test
    public void addRepetitiveCharAndGet_ShouldReturnDifferentChildObjectEachTime() {
        node.add('k');
        var oldChild = node.getChild('k');
        node.add('k');
        var newChild = node.getChild('k');
        assertEquals(oldChild.getLetter(), newChild.getLetter());
        assertNotSame(oldChild, newChild);
    }

    @Test
    public void emptyNodeHasNoChild_ShouldReturnTrue() {
        assertTrue(node.isLeaf());
    }

    @Test
    public void addOneAndHasNoChild_ShouldReturnFalse() {
        node.add('k');
        assertFalse(node.isLeaf());
    }

    @Test
    public void removeANonExistentChild_ShouldReturnNull() {
        assertNull(node.removeChild('k'));
        node.add('k');
        assertNull(node.removeChild('f'));
    }

    @Test
    public void removeAChild_ShouldReturnTheChild() {
        node.add('k');
        var removed = node.getChild('k');
        assertSame(removed, node.removeChild('k'));
    }

    @Test
    public void getAllChildrenOfEmptyNode_ShouldReturnArrayWithNullStub() {
        var allChildren = node.getAllChildren();
        for (var n : allChildren) {
            assertNull(n);
        }
    }

    @Test
    public void addThreeAndGetAllChildren_ReturnedArrayMustContainsAllChildren() {
        node.add('o');
        node.add('a');
        node.add('b');

        var allChildren = node.getAllChildren();
        var destArr = new HashMapNode[] {node.getChild('o'), node.getChild('a'), node.getChild('b')};

        for (var targetNode : destArr) {
            var contains = false;
            for (var n : allChildren)
                if (n == targetNode) {
                    contains = true;
                    break;
                }
            assertTrue(contains);
        }
    }

    @Test
    public void addMoreAndGetAllChildren_ShouldReturnArrayContainingAllChildren() {
        var charList = List.of('a','b','c','e','f','g','h','i');
        charList.forEach(node::add);

        var destArr = new HashMapNode[8];
        var cursor = -1;
        for (var child : charList) {
            destArr[++cursor] = node.getChild(child);
        }

        var allChildren = node.getAllChildren();
        for (var targetNode : destArr) {
            var contains = false;
            for (var n : allChildren)
                if (n == targetNode) {
                    contains = true;
                    break;
                }
            assertTrue(contains);
        }
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
