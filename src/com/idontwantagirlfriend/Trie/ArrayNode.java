package com.idontwantagirlfriend.Trie;

import com.idontwantagirlfriend.Array.Array;

/** Deprecation warning:
 * This implementation does not take into account
 * non-alphabetical letters. It may be incompatible
 * with later implementations of Trie.
 */
public class ArrayNode implements Node {
    private final ArrayNode[] subtrees;
    private final int ALPHABET_NUMBER = 26;
    private char letter;
    private Boolean isEOW;


    public ArrayNode() {
        subtrees = new ArrayNode[ALPHABET_NUMBER];
        isEOW = false;
    }

    public ArrayNode(char letter) {
        this();
        this.letter = letter;
    }


    private static int getLetterPosition(char letter) {
        return letter - 'a';
    }

    @Override
    public char getLetter() {
        return letter;
    }

    @Override
    public ArrayNode getChild(char letter) {
        handleIllegalCharacter(letter);
        return subtrees[getLetterPosition(letter)];
    }

    private ArrayNode setChild(char letter, ArrayNode child) {
        handleIllegalCharacter(letter);
        subtrees[getLetterPosition(letter)] = child;
        return child;
    }

    @Override
    public Boolean hasChild(char letter) {
        return getChild(letter) != null;
    }

    @Override
    public Boolean hasNoChild() {
        for (var i = 0; i < ALPHABET_NUMBER; i++) {
            if (subtrees[i] != null) return false;
        }
        return true;
    }

    @Override
    public ArrayNode removeChild(char letter) {
        if (getChild(letter) != null) {
            var removed = getChild(letter);
            setChild(letter, null);
            return removed;
        }
        return null;
    }

    @Override
    public ArrayNode addChild(char letter) {
        var newChild = new ArrayNode(letter);
        return setChild(letter, newChild);
    }

    @Override
    public ArrayNode[] getAllChildren() {
        var allChildren = new ArrayNode[26];
        var cursor = -1;

        for (var i = 0; i < subtrees.length; i++) {
            if (subtrees[i] != null)
                allChildren[++cursor] = subtrees[i];
        }

        return allChildren;
    }

    @Override
    public void setEOW(Boolean isEOW) {
        this.isEOW = isEOW;
    }

    @Override
    public Boolean getEOW() {
        return isEOW;
    }

    private void handleIllegalCharacter(char letter) {
        var position = getLetterPosition(letter);
        if (position < 0 || position > 26)
            throw new IllegalArgumentException(
                    "A node is only supposed to store lowercase alphabetical letter.");
    }
}