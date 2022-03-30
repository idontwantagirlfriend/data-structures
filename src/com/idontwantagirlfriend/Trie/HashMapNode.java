package com.idontwantagirlfriend.Trie;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class HashMapNode implements Node {

    private HashMap<Character, HashMapNode> subtrees;
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
    public Boolean hasChild(char letter) {
        handleIllegalCharacter(letter);
        return subtrees.containsKey(letter);
    }

    @Override
    public Boolean hasNoChild() {
        return subtrees.isEmpty();
    }

    @Override
    public HashMapNode removeChild(char letter) {
        return subtrees.remove(letter);
    }

    @Override
    public HashMapNode addChild(char letter) {
        handleIllegalCharacter(letter);
        var child = new HashMapNode(letter);
        subtrees.put(letter, child);
        return child;
    }

    @Override
    public HashMapNode[] getAllChildren() {
        var allChildren = new HashMapNode[5];
        var cursor = -1;

        for (var entry : subtrees.entrySet()) {
            if (cursor >= allChildren.length - 1) {
                var expanded = new HashMapNode[allChildren.length * 2];
                for (var i = 0; i < allChildren.length; i++)
                    expanded[i] = allChildren[i];
                allChildren = expanded;
            }
            allChildren[++cursor] = entry.getValue();
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
        var position = letter - 'a';
        if (position > 26 || position < 0)
            throw new IllegalArgumentException("A node is only supposed to store lowercase alphabetical letter.");
    }
}
