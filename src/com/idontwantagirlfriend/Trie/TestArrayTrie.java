package com.idontwantagirlfriend.Trie;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestArrayTrie {
    public ArrayTrie trie;

    public void refreshTrie() {
        trie = new ArrayTrie();
    }

    @BeforeEach
    public void setUp() {
        refreshTrie();
    }

    @Test
    public void addAWord() {
        trie.add("Hello");
    }

    @Test
    public void addASpaceCharacter_ShouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> trie.add(" "));
    }

    @Test
    public void addEmptyString_ShouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> trie.add(""));
    }

    @Test
    public void addAWordThatContainsNonAlphabeticalChars_ShouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> trie.add("Hello "));
        assertThrows(IllegalArgumentException.class, () -> trie.add("Étranger"));
    }

    @Test
    public void removeAWord_ShouldReturnTheRemovedWord() {
        trie.add("Hello");
        trie.add("world");
        trie.add("This");
        assertEquals("World", trie.remove("World"));
    }

    @Test
    public void removeAWordThatDoesNotExist_ShouldReturnNull() {
        trie.add("Hello");
        assertNull(trie.remove("Hell"));
        assertNull(trie.remove("A"));
        assertNull(trie.remove("Helmet"));
    }

    @Test
    public void removeANonExistentWordThatIsPartOfSomeWordInTheTrie_ShouldReturnNull() {
        trie.add("fellow");
        assertNull(trie.remove("fell"));
    }

    @Test
    public void removeAWordThatIsPartOfSomeWordInTheTrie_ShouldReturnTheRemovedWord() {
        trie.add("fellow");
        trie.add("fell");
        assertEquals("fell", trie.remove("fell"));
    }

    @Test
    public void removeNull_ShouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> trie.remove(null));
        trie.add("No");
        assertThrows(IllegalArgumentException.class, () -> trie.remove(null));
    }

    @Test
    public void removeEmptyString_ShouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> trie.remove(""));
        trie.add("Aufschlag");
        assertThrows(IllegalArgumentException.class, () -> trie.remove(""));
    }

    @Test
    public void findAWord_ShouldReturnTrue() {
        trie.add("ww");
        assertTrue(trie.find("ww"));
    }

    @Test
    public void findANonExistentWord_ShouldReturnFalse() {
        assertFalse(trie.find("firelight"));
        trie.add("sunken");
        assertFalse(trie.find("firelight"));
    }

    @Test
    public void findASimilarWord_ShouldReturnFalse() {
        trie.add("forward");
        assertFalse(trie.find("for"));
        assertFalse(trie.find("forwarding"));
    }

    @Test
    public void findEmptyString_ShouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> trie.find(""));
        trie.add("Abs");
        assertThrows(IllegalArgumentException.class, () -> trie.find(""));
    }

    @Test
    public void findNull_ShouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> trie.find(null));
        trie.add("Abs");
        assertThrows(IllegalArgumentException.class, () -> trie.find(null));
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
        public void findChildOfEmptyNode_ShouldReturnNull() {
            assertNull(node.findChild('a'));
        }

        @Test
        public void findNonAlphabeticalChild_ShouldThrowIllegalArgumentException() {
            assertThrows(IllegalArgumentException.class, () -> node.findChild('='));
            assertThrows(IllegalArgumentException.class, () -> node.findChild(' '));
        }

        @Test
        public void findUppercaseLetterChild_ShouldThrowIllegalArgumentException() {
            assertThrows(IllegalArgumentException.class, () -> node.findChild('A'));
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
        public void addOneFindOne_ShouldReturnChild() {
            node.addChild('m');
            assertInstanceOf(ArrayTrie.Node.class, node.findChild('m'));
            assertEquals('m', node.findChild('m').getLetter());
        }

        @Test
        public void addRepetitiveCharAndFind_ShouldReturnDifferentChildObjectEachTime() {
            node.addChild('k');
            var oldChild = node.findChild('k');
            node.addChild('k');
            var newChild = node.findChild('k');
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
            var removed = node.findChild('k');
            assertSame(removed, node.removeChild('k'));
        }

        @Test
        public void pruneChildThatHasNoChild_ShouldReturnTrue() {
            node.addChild('k');
            assertTrue(node.pruneChild('k'));
            assertNull(node.findChild('k'));
        }

        @Test
        public void pruneChildThatHasChildren_ShouldReturnFalse() {
            var kNode = node.addChild('k');
            kNode.addChild('t');
            assertFalse(node.pruneChild('k'));
            assertSame(kNode, node.findChild('k'));
        }

        @Test
        public void pruneNonExistentChild_ShouldReturnFalse() {
            assertFalse(node.pruneChild('a'));
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

        @Test
        public void addOneUniqueLetter_ShouldReturnTrue() {
            assertNull(node.findChild('a'));
            assertTrue(node.add('a'));
            assertEquals('a', node.findChild('a').getLetter());
        }

        @Test
        public void addOneDuplicateLetter_ShouldReturnFalseAndKeepTheOriginalChild() {
            var oldChild = node.addChild('k');
            assertEquals('k', oldChild.getLetter());
            assertFalse(node.add('k'));
            assertSame(oldChild, node.findChild('k'));
        }
    }
}
