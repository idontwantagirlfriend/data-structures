package com.idontwantagirlfriend.Trie;

import com.idontwantagirlfriend.Array.Array;
import java.util.Arrays;

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

    private String remove(StringNode node, String word, int level) {

        if (word.equals("")) return null;

        if (level > word.length()) {
            node.setEOW(false);
            return node.getWord();
        }

        var targetString = word.substring(0, level);
        var child = node.getChild(targetString);

        if (child == null) return null;

        var removed = remove(child, word, level + 1);

        if (child.isLeaf())
            node.remove(targetString);

        return removed;
    }

    public Boolean find(String word) {
        handleNullInput(word);

        var foundNode = goToNode(word.toLowerCase());
        return foundNode != null && foundNode.getEOW();
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

    public String[] doPrediction(String prefix) {
        handleNullInput(prefix);

        var foundNode = goToNode(prefix.toLowerCase());
        if (foundNode == null) return new String[0];

        var predictions = new Array<String>();
        if (foundNode.getEOW()) predictions.insert(foundNode.getWord());
        getAllChildren(foundNode, predictions);
        return predictions.toArray(new String[0]);
    }

    public String[] toArray() {
        var accumulator = new Array<String>();
        getAllChildren(root, accumulator);
        return accumulator.toArray(new String[0]);
    }

    public String toString() {
        return Arrays.toString(toArray());
    }

    private void getAllChildren(StringNode node, Array<String> accumulator) {
        for (var child : node.getAllChildren()) {
            if (child.getEOW()) accumulator.insert(child.getWord());
        }

        for (var child : node.getAllChildren()) {
            getAllChildren(child, accumulator);
        }
    }

    private StringNode goToNode(String word) {
        var cursor = root;
        for (var i = 1; i <= word.length(); i++) {
            var targetString = word.substring(0, i);
            if (cursor.getChild(targetString) == null) return null;
            cursor = cursor.getChild(targetString);
        }
        return cursor;
    }

    private void handleNullInput(String input) {
        if (input == null)
            throw new IllegalArgumentException(
                    "You can't input a null into the trie.");
    }
}