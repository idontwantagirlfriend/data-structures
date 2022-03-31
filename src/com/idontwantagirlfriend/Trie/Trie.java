package com.idontwantagirlfriend.Trie;

import com.idontwantagirlfriend.Array.Array;

public class Trie {
    private final StringNode root;

    public Trie(StringNode root) {
        this.root = root;
    }

    public void add(String word) {
        var cursor = root;

        for (var i = 1; i <= word.length(); i++) {
            var content = word.substring(0, i);
            if (cursor.getChild(content) == null)
                cursor.add(content);
            cursor = cursor.getChild(content);
        }

        if (cursor != root && !cursor.getEOW()) cursor.setEOW(true);
    }

    public Boolean remove(String word) {
        return remove(root, word, 1);
    }

    private Boolean remove(StringNode node, String word, int upperBound) {

        if (upperBound > word.length()) {
            node.setEOW(false);
        } else {
            var targetString = word.substring(0, upperBound);
            var child = node.getChild(targetString);

            if (child == null) return false;

            remove(child, word, upperBound + 1);

            if (child.isLeaf())
                node.remove(targetString);
        }

        return true;
    }

    public Boolean find(String word) {
        var lowercase = word.toLowerCase();
        var cursor = root;
        for (var i = 1; i <= lowercase.length(); i++) {
            var targetString = word.substring(0, i);
            if (cursor.getChild(targetString) == null) return false;
            cursor = cursor.getChild(targetString);
        }
        return cursor.getEOW();
    }

    public int count() {
        return count(root, 0);
    }

    private int count(StringNode node, int accumulator) {
        var acc = accumulator;

        for (var child : node.getAllChildren()) {
            if (child.getEOW()) acc++;
        }

        for (var child : node.getAllChildren()) {
            acc = count(child, acc);
        }

        return acc;
    }

    public String[] getAllWords() {
        var accumulator = new Array<String>();
        getAllWords(root, accumulator);
        return accumulator.toArray();
    }

    private void getAllWords(StringNode node, Array<String> accumulator) {
        for (var child : node.getAllChildren()) {
            if (child.getEOW()) accumulator.insert(child.getWord());
        }

        for (var child : node.getAllChildren()) {
            getAllWords(child, accumulator);
        }
    }

    public String toString() {
        var accumulator = new Array<String>();
        getAllWords(root, accumulator);
        return accumulator.toString();
    }
}