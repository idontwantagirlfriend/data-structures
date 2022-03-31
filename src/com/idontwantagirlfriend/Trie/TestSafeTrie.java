package com.idontwantagirlfriend.Trie;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestSafeTrie {
    public SafeTrie safeTrie;

    public void refreshTrie() {
        safeTrie = new SafeTrie(new ArrayNode());
    }

    @BeforeEach
    public void setUp() {
        refreshTrie();
    }

    @Test
    public void addAWord() {
        safeTrie.add("Hello");
    }

    @Test
    public void addASpaceCharacter_ShouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> safeTrie.add(" "));
    }

    @Test
    public void addEmptyString_ShouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> safeTrie.add(""));
    }

    @Test
    public void addAWordThatContainsNonAlphabeticalChars_ShouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> safeTrie.add("Hello "));
        assertThrows(IllegalArgumentException.class, () -> safeTrie.add("Étranger"));
    }

    @Test
    public void removeAWord_ShouldReturnTheRemovedWord() {
        safeTrie.add("Hello");
        safeTrie.add("world");
        safeTrie.add("This");
        assertEquals("World", safeTrie.remove("World"));
    }

    @Test
    public void removeAWordThatDoesNotExist_ShouldReturnNull() {
        safeTrie.add("Hello");
        assertNull(safeTrie.remove("Hell"));
        assertNull(safeTrie.remove("A"));
        assertNull(safeTrie.remove("Helmet"));
    }

    @Test
    public void removeANonExistentWordThatIsPartOfSomeWordInTheTrie_ShouldReturnNull() {
        safeTrie.add("fellow");
        assertNull(safeTrie.remove("fell"));
    }

    @Test
    public void removeAWordThatIsPartOfSomeWordInTheTrie_ShouldReturnTheRemovedWord() {
        safeTrie.add("Hello");
        safeTrie.add("hell");
        assertEquals("hell", safeTrie.remove("hell"));
    }

    @Test
    public void removeNull_ShouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> safeTrie.remove(null));
        safeTrie.add("No");
        assertThrows(IllegalArgumentException.class, () -> safeTrie.remove(null));
    }

    @Test
    public void removeEmptyString_ShouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> safeTrie.remove(""));
        safeTrie.add("Aufschlag");
        assertThrows(IllegalArgumentException.class, () -> safeTrie.remove(""));
    }

    @Test
    public void findAWord_ShouldReturnTrue() {
        safeTrie.add("ww");
        assertTrue(safeTrie.find("ww"));
    }

    @Test
    public void removeAWordAndFindRemainders_ShouldReturnTrue() {
        safeTrie.add("hello");
        safeTrie.add("Hell");
        safeTrie.add("held");
        safeTrie.remove("hello");
        assertTrue(safeTrie.find("hell"));
        assertTrue(safeTrie.find("held"));
        safeTrie.remove("hell");
        assertTrue(safeTrie.find("held"));
    }

    @Test
    public void findANonExistentWord_ShouldReturnFalse() {
        assertFalse(safeTrie.find("firelight"));
        safeTrie.add("sunken");
        assertFalse(safeTrie.find("firelight"));
    }

    @Test
    public void findASimilarWord_ShouldReturnFalse() {
        safeTrie.add("forward");
        assertFalse(safeTrie.find("for"));
        assertFalse(safeTrie.find("forwarding"));
    }

    @Test
    public void findEmptyString_ShouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> safeTrie.find(""));
        safeTrie.add("Abs");
        assertThrows(IllegalArgumentException.class, () -> safeTrie.find(""));
    }

    @Test
    public void findNull_ShouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> safeTrie.find(null));
        safeTrie.add("Abs");
        assertThrows(IllegalArgumentException.class, () -> safeTrie.find(null));
    }
}
