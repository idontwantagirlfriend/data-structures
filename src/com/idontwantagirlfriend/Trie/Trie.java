package com.idontwantagirlfriend.Trie;

import com.idontwantagirlfriend.Array.Array;

public record Trie(StringNode root) {

    public void add(String word) {
        handleNullInput(word);

        var cursor = root;
        var lowercase = word.toLowerCase();

        for (var i = 1; i <= lowercase.length(); i++) {
            var content = lowercase.substring(0, i);
            if (cursor.getChild(content) == null)
                cursor.add(content);
            cursor = cursor.getChild(content);
        }

        if (cursor != root && !cursor.getEOW()) cursor.setEOW(true);
    }

    public String remove(String word) {
        handleNullInput(word);

        return remove(root, word.toLowerCase(), 1);
    }

    private String remove(StringNode node, String word, int upperBound) {

        if (upperBound > word.length() && !word.equals("")) {
            node.setEOW(false);
            return node.getWord();
        }
        var targetString = word.length() > 0
                ? word.substring(0, upperBound)
                : word;
        var child = node.getChild(targetString);

        if (child == null) return null;

        var removed = remove(child, word, upperBound + 1);

        if (child.isLeaf())
            node.remove(targetString);

        return removed;
    }

    public Boolean find(String word) {
        handleNullInput(word);

        var lowercase = word.toLowerCase();
        var cursor = root;
        for (var i = 1; i <= lowercase.length(); i++) {
            var targetString = lowercase.substring(0, i);
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

    public String[] toArray() {
        var accumulator = new Array<String>();
        toArray(root, accumulator);
        return accumulator.toArray(new String[0]);
    }

    private void toArray(StringNode node, Array<String> accumulator) {
        for (var child : node.getAllChildren()) {
            if (child.getEOW()) accumulator.insert(child.getWord());
        }

        for (var child : node.getAllChildren()) {
            toArray(child, accumulator);
        }
    }

    public String toString() {
        var accumulator = new Array<String>();
        toArray(root, accumulator);
        return accumulator.toString();
    }

    private void handleNullInput(String input) {
        if (input == null)
            throw new IllegalArgumentException(
                    "You can't input a null into the trie.");
    }
}