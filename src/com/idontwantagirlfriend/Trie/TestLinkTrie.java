package com.idontwantagirlfriend.Trie;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestLinkTrie {
    public LinkTrie linkTrie;

    public void refreshTrie() {
        linkTrie = new LinkTrie(new ArrayNode());
    }

    @BeforeEach
    public void setUp() {
        refreshTrie();
    }

    @Test
    public void addAWord() {
        linkTrie.add("Hello");
    }

    @Test
    public void addASpaceCharacter_ShouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> linkTrie.add(" "));
    }

    @Test
    public void addEmptyString_ShouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> linkTrie.add(""));
    }

    @Test
    public void addAWordThatContainsNonAlphabeticalChars_ShouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> linkTrie.add("Hello "));
        assertThrows(IllegalArgumentException.class, () -> linkTrie.add("Étranger"));
    }

    @Test
    public void removeAWord_ShouldReturnTheRemovedWord() {
        linkTrie.add("Hello");
        linkTrie.add("world");
        linkTrie.add("This");
        assertEquals("World", linkTrie.remove("World"));
    }

    @Test
    public void removeAWordThatDoesNotExist_ShouldReturnNull() {
        linkTrie.add("Hello");
        assertNull(linkTrie.remove("Hell"));
        assertNull(linkTrie.remove("A"));
        assertNull(linkTrie.remove("Helmet"));
    }

    @Test
    public void removeANonExistentWordThatIsPartOfSomeWordInTheTrie_ShouldReturnNull() {
        linkTrie.add("fellow");
        assertNull(linkTrie.remove("fell"));
    }

    @Test
    public void removeAWordThatIsPartOfSomeWordInTheTrie_ShouldReturnTheRemovedWord() {
        linkTrie.add("fellow");
        linkTrie.add("fell");
        assertEquals("fell", linkTrie.remove("fell"));
    }

    @Test
    public void removeNull_ShouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> linkTrie.remove(null));
        linkTrie.add("No");
        assertThrows(IllegalArgumentException.class, () -> linkTrie.remove(null));
    }

    @Test
    public void removeEmptyString_ShouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> linkTrie.remove(""));
        linkTrie.add("Aufschlag");
        assertThrows(IllegalArgumentException.class, () -> linkTrie.remove(""));
    }

    @Test
    public void findAWord_ShouldReturnTrue() {
        linkTrie.add("ww");
        assertTrue(linkTrie.find("ww"));
    }

    @Test
    public void findANonExistentWord_ShouldReturnFalse() {
        assertFalse(linkTrie.find("firelight"));
        linkTrie.add("sunken");
        assertFalse(linkTrie.find("firelight"));
    }

    @Test
    public void findASimilarWord_ShouldReturnFalse() {
        linkTrie.add("forward");
        assertFalse(linkTrie.find("for"));
        assertFalse(linkTrie.find("forwarding"));
    }

    @Test
    public void findEmptyString_ShouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> linkTrie.find(""));
        linkTrie.add("Abs");
        assertThrows(IllegalArgumentException.class, () -> linkTrie.find(""));
    }

    @Test
    public void findNull_ShouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> linkTrie.find(null));
        linkTrie.add("Abs");
        assertThrows(IllegalArgumentException.class, () -> linkTrie.find(null));
    }

    public static class NodeMgrTest {
        private final LinkTrie.NodeMgr nodeMgr = new LinkTrie.NodeMgr();
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
