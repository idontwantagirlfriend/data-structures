package com.idontwantagirlfriend.Trie;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class TestLegacyTrie {
    private LegacyTrie trie;

    @BeforeEach
    public void setUp() {
        trie = new LegacyTrie();
    }

    @ParameterizedTest
    @ValueSource(strings = {"hello", "asdfdgvc", "54xcv1x"})
    public void addOneAndFindIt_ShouldReturnTrue(String source) {
        trie.insert(source);
        assertTrue(trie.find(source));
    }

    @Test
    public void findWordsInDifferentCases_ShouldIgnoreCase() {
        trie.insert("oWo");
        assertTrue(trie.find("OwO"));
    }

    @Test
    public void addAndFindWordsContainingSpace_ShouldReturnTrue() {
        trie.insert("Hello world!");
        assertTrue(trie.find("Hello world!"));
    }

    @Test
    public void findNonExistentWords_ShouldReturnFalse() {
        trie.insert("hello");
        trie.insert("hi");
        trie.insert("salut");
        trie.insert("dag");
        assertFalse(trie.find("salve"));
    }

    @Test
    public void findEmptyString_ShouldReturnFalse() {
        assertFalse(trie.find(""));
        trie.insert("hello");
        trie.insert("hi");
        trie.insert("salut");
        trie.insert("dag");
        assertFalse(trie.find(""));
    }

    @Test
    public void findNullWhenNoWordsHaveBeenAdded_ShouldReturnFalse() {
        assertFalse(trie.find(null));
    }

    @Test
    public void findNullWhenWordsHaveBeenAdded_ShouldReturnFalse() {
        trie.insert("Hallo");
        trie.insert("Bonjour");
        assertFalse(trie.find(null));
    }
}
