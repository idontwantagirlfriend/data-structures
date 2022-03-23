
package com.idontwantagirlfriend.Trie;

import java.util.HashMap;
import java.util.Objects;

public class Trie {
    private Node root;

    public Trie() {
        this.root = new Node(null);
    }

    public void insert(String word) {
        if (word != null) {
            root.addToSubTrees(word.toLowerCase());
        }
    }

    public Boolean find(String word) {
        if (word == null) return root.findInSubtrees(null);
        return root.findInSubtrees(word.toLowerCase());
    }

    public static class Node {
        private HashMapAdaptor subtrees;
        private String letter;

        public Node(String letter) {
            this.letter = letter;
            this.subtrees = new HashMapAdaptor();
        }

        public void addToSubTrees(String word) {
            if (Objects.equals(word, "")) return;
            var startingLetter = word == null ? null : Character.toString(word.charAt(0));
            var existingChild
                    = subtrees.ensure(startingLetter, new Node(startingLetter));
            if (word != null)
                existingChild.addToSubTrees(word.length() > 1
                    ? word.substring(1)
                    : null);
        }

        public Boolean findInSubtrees(String word) {
            var startingLetter = word == null || word.equals("") ? null : Character.toString(word.charAt(0));
            var existingChild = getChild(startingLetter);
            if (existingChild == null) return false;
            return word == null || word.equals("")
                    || existingChild.findInSubtrees(word.length() > 1 ? word.substring(1) : null);
        }

        public Node getChild(String key) {
            return subtrees.get(key);
        }

        public String getLetter() {
            return letter;
        }
    }
    public static class HashMapAdaptor {
        private HashMap<String, Node> hashMap;

        public HashMapAdaptor() {
            this.hashMap = new HashMap<>(10);
        }

        public void put(String key, Node value) {
            hashMap.put(key, value);
        }

        public Node get(String key) {
            return hashMap.get(key);
        }

        public Node remove(String key) {
            return hashMap.remove(key);
        }

        public Node ensure(String key, Node defaultValue) {
            var existingValue
                    = get(key);
            if (existingValue == null) {
                put(key, defaultValue);
                existingValue = defaultValue;
            }
            return existingValue;
        }
    }
}

