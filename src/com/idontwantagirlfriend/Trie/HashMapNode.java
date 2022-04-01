package com.idontwantagirlfriend.Trie;

import java.util.HashMap;

public class HashMapNode implements CharNode {

    private final HashMap<Character, HashMapNode> subtrees;
    private Boolean isEOW;
    private char letter;

    public HashMapNode() {
        isEOW = false;
        subtrees = new HashMap<>();
    }

    public HashMapNode(char letter) {
        this();
        this.letter = letter;
    }

    @Override
    public char getLetter() {
        return letter;
    }

    @Override
    public HashMapNode getChild(char letter) {
        handleIllegalCharacter(letter);
        return subtrees.get(letter);
    }

    @Override
    public Boolean contains(char letter) {
        handleIllegalCharacter(letter);
        return subtrees.containsKey(letter);
    }

    @Override
    public Boolean isLeaf() {
        return subtrees.isEmpty();
    }

    @Override
    public HashMapNode removeChild(char letter) {
        return subtrees.remove(letter);
    }

    @Override
    public void add(char letter) {
        handleIllegalCharacter(letter);
        subtrees.put(letter, new HashMapNode(letter));
    }

    @Override
    public HashMapNode[] getAllChildren() {
        return subtrees.values().toArray(HashMapNode[]::new);
    }

    @Override
    public Boolean getEOW() {
        return isEOW;
    }

    @Override
    public void setEOW(Boolean isEOW) {
        this.isEOW = isEOW;
    }

    private void handleIllegalCharacter(char letter) {
        var position = letter - 'a';
        if (position > 26 || position < 0)
            throw new IllegalArgumentException("A node is only supposed to store lowercase alphabetical letter.");
    }
}
