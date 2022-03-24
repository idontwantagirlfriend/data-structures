package com.idontwantagirlfriend.Trie;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestArrayTrie {
    public ArrayTrie trie;

    public void refreshTrie() {
        trie = new ArrayTrie(new ArrayNode());
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

    public static class NodeMgrTest {
        private final ArrayTrie.NodeMgr nodeMgr = new ArrayTrie.NodeMgr();
        private Node node;

        @BeforeEach
        public void setUpEach() {
            node = new ArrayNode('a');
        }

        @Test
        public void findChildOfEmptyNode_ShouldReturnNull() {
            assertNull(nodeMgr.findChild(node, 'a'));
        }

        @Test
        public void findNonAlphabeticalChild_ShouldThrowIllegalArgumentException() {
            assertThrows(
                    IllegalArgumentException.class,
                    () -> nodeMgr.findChild(node, '='));
            assertThrows(
                    IllegalArgumentException.class,
                    () -> nodeMgr.findChild(node, ' '));
        }

        @Test
        public void findUppercaseLetterChild_ShouldThrowIllegalArgumentException() {
            assertThrows(
                    IllegalArgumentException.class,
                    () -> nodeMgr.findChild(node, 'A'));
        }

        @Test
        public void safeAddOneUniqueLetter_ShouldReturnTrue() {
            assertNull(nodeMgr.findChild(node, 'a'));
            assertTrue(nodeMgr.safeAdd(node, 'a'));
            assertInstanceOf(Node.class, nodeMgr.findChild(node, 'a'));
        }

        @Test
        public void safeAddOneDuplicateLetter_ShouldReturnFalseAndKeepTheOriginalChild() {
            var firstAddSuccess = nodeMgr.safeAdd(node, 'k');
            assertTrue(firstAddSuccess);
            var oldChild = nodeMgr.findChild(node, 'k');
            assertInstanceOf(Node.class, oldChild);
            assertFalse(nodeMgr.safeAdd(node, 'k'));
            assertSame(oldChild, nodeMgr.findChild(node, 'k'));
        }

        @Test
        public void safeAddOneFindOne_ShouldReturnChild() {
            nodeMgr.safeAdd(node, 'm');
            assertInstanceOf(Node.class, nodeMgr.findChild(node, 'm'));
        }

        @Test
        public void safeAddNonAlphabeticalLowercaseChar_ShouldThrowIllegalArgumentException() {
            assertThrows(IllegalArgumentException.class, () -> nodeMgr.safeAdd(node, '?'));
            assertThrows(IllegalArgumentException.class, () -> nodeMgr.safeAdd(node, 'A'));
        }

        @Test
        public void pruneChildThatHasNoChild_ShouldReturnTrue() {
            nodeMgr.safeAdd(node, 'k');
            assertInstanceOf(Node.class, nodeMgr.findChild(node, 'k'));
            assertTrue(nodeMgr.pruneChild(node, 'k'));
            assertNull(nodeMgr.findChild(node, 'k'));
        }

        @Test
        public void pruneChildThatHasChildren_ShouldReturnFalse() {
            nodeMgr.safeAdd(node, 'k');
            var kNode = nodeMgr.findChild(node, 'k');
            nodeMgr.safeAdd(kNode, 't');
            assertFalse(nodeMgr.pruneChild(node, 'k'));
            assertSame(kNode, nodeMgr.findChild(node, 'k'));
        }

        @Test
        public void pruneNonExistentChild_ShouldReturnFalse() {
            assertFalse(nodeMgr.pruneChild(node, 'a'));
        }
    }
}
