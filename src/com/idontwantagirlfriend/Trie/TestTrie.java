package com.idontwantagirlfriend.Trie;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestTrie {
    private Trie trie;

    @BeforeEach
    public void refreshTrie() {
        trie = new Trie(new ConcreteStringNode(""));
    }

    @Test
    public void addOneAndFind_ShouldReturnTrue() {
        trie.add("Hello");
        assertTrue(trie.find("Hello"));
    }

    @Test
    public void addOneAndRemove_ShouldReturnLowercaseForm() {
        trie.add("Hello");
        assertEquals("hello", trie.remove("Hello"));
    }

    @Test
    public void notCaseSensitive() {
        trie.add("hello");
        assertTrue(trie.find("Hello"));
        assertEquals("hello", trie.remove("hElLo"));
    }

    @Test
    public void findNonExistentWord_ShouldReturnFalse() {
        trie.add("hello");
        assertFalse(trie.find("Halo"));
    }

    @Test
    public void removeNonExistentWord_ShouldReturnNull() {
        trie.add("Hello");
        assertNull(trie.remove("halo"));
    }

    @Test
    public void findOnEmptyTrie_ShouldReturnFalse() {
        assertFalse(trie.find("hello"));
    }

    @Test
    public void removeOnEmptyTrie_ShouldReturnNull() {
        assertNull(trie.remove("what"));
    }

    @Test
    public void findEmptyString_ShouldReturnFalse() {
        trie.add("hello");
        assertFalse(trie.find(""));
    }

    @Test
    public void removeEmptyString_ShouldReturnNull() {
        trie.add("Hello");
        assertNull(trie.remove(""));
    }

    @Test
    public void findEmptyStringOnEmptyTrie_ShouldReturnFalse() {
        assertFalse(trie.find(""));
    }

    @Test
    public void removeEmptyStringOnEmptyTrie_ShouldReturnNull() {
        assertNull(trie.remove(""));
    }

    @Test
    public void nullInput_ShouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> trie.add(null));
        assertThrows(IllegalArgumentException.class, () -> trie.find(null));
        assertThrows(IllegalArgumentException.class, () -> trie.remove(null));
    }

    @Test
    public void addOneAndCount_ShouldReturn1() {
        trie.add("hello");
        assertEquals(1, trie.count());
    }

    @Test
    public void addManyAndCount_ShouldReturnTheNumber() {
        trie.add("Hello");
        trie.add("world");
        trie.add("This");
        trie.add("Is");
        assertEquals(4, trie.count());
    }

    @Test
    public void countEmptyTrie_shouldReturn0() {
        assertEquals(0, trie.count());
    }

    @Test
    public void toArray() {
        trie.add("hello");
        trie.add("world");
        trie.add("this");
        var allWords = trie.toArray();
        assertEquals(3, allWords.length);
        assertTrue(arrayContains(allWords, "hello"));
        assertTrue(arrayContains(allWords, "world"));
        assertTrue(arrayContains(allWords, "this"));
    }

    @Test
    public void emptyTrieToArray_ShouldReturnEmptyArray() {
        assertArrayEquals(new String[0], trie.toArray());
    }

    @Test
    public void addOne_RemoveAndToArray_ShouldReturnEmptyArray() {
        trie.add("Hello");
        assertEquals(1, trie.toArray().length);
        assertTrue(arrayContains(trie.toArray(), "hello"));
        trie.remove("hello");
        assertArrayEquals(new String[0], trie.toArray());
    }

    private Boolean arrayContains(String[] wordList, String word) {
        for (var each : wordList) {
            if (each.equals(word)) return true;
        }
        return false;
    }
}
