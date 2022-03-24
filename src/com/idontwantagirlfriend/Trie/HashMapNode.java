package com.idontwantagirlfriend.Trie;

import java.util.HashMap;

public class HashMapNode implements Node {

    private HashMap<Character, HashMapNode> map;
    private Boolean isEOW;
    private char letter;

    public HashMapNode() {
        isEOW = false;
        map = new HashMap<>();
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
        return map.get(letter);
    }

    @Override
    public Boolean hasChild(char letter) {
        handleIllegalCharacter(letter);
        return map.containsKey(letter);
    }

    @Override
    public Boolean hasNoChild() {
        return map.isEmpty();
    }

    @Override
    public HashMapNode removeChild(char letter) {
        return map.remove(letter);
    }

    @Override
    public HashMapNode addChild(char letter) {
        handleIllegalCharacter(letter);
        var child = new HashMapNode(letter);
        map.put(letter, child);
        return child;
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
        var position = letter - 'a';
        if (position > 26 || position < 0)
            throw new IllegalArgumentException("A node is only supposed to store lowercase alphabetical letter.");
    }
}
