package com.idontwantagirlfriend.Trie;

public class ArrayTrie {
    private NodeMgr nodeMgr;
    private Node root;

    public ArrayTrie(Node root) {
        this.nodeMgr = new NodeMgr();
        this.root = root;
    }

    /**
     * Add new {@code word} to the current trie.<br/>
     * On null or empty value, throw IllegalArgumentException.
     * @param word The new word in string.
     */
    public void add(String word) {
        handleMeaninglessInput(word);
        var lowercase = word.toLowerCase();
        var cursor = root;
        for (var i = 0; i < lowercase.length(); i++) {
            var letter = lowercase.charAt(i);
            handleIllegalCharacter(letter);
            nodeMgr.safeAdd(cursor, letter);
            cursor = nodeMgr.findChild(cursor, letter);
        }
        nodeMgr.setIsEOW(cursor, true);
    }

    /**
     * Remove a word from the current trie. Return the removed
     * word if it's found, or null if the word can't be found.<br/>
     * On null or empty value, throw IllegalArgumentException.
     * @param word the word to be removed.
     * @return the word, or null.
     */
    public String remove(String word) {
        handleMeaninglessInput(word);
        var lowercase = word.toLowerCase();
        var cursor = root;
        for (var i = 0; i < lowercase.length() - 1; i++) {
            var letter = lowercase.charAt(i);
            if (!cursor.hasChild(letter)) return null;
            cursor = nodeMgr.findChild(cursor, letter);
        }

//        word.length() > 1 has been ensured.
        var lastLetter = lowercase.charAt(word.length() - 1);
        if (!cursor.hasChild(lastLetter)
                || !nodeMgr.getIsEOW(nodeMgr.findChild(cursor, lastLetter)))
            return null;
        if (!nodeMgr.pruneChild(cursor, lastLetter))
            nodeMgr.setIsEOW(nodeMgr.findChild(cursor, lastLetter), false);
        nodeMgr.setIsEOW(cursor, false);
        return word;
    }

    /**
     * Find a word in the trie and return a boolean based
     * on the result.<br/>
     * On null or empty value, throw IllegalArgumentException.
     * @param word the word to be found.
     * @return the result.
     */
    public Boolean find(String word) {
        handleMeaninglessInput(word);
        var lowercase = word.toLowerCase();
        var cursor = root;
        for (var i = 0; i < lowercase.length(); i++) {
            var letter = lowercase.charAt(i);
            handleIllegalCharacter(letter);
            if (!cursor.hasChild(letter)) return false;
            cursor = nodeMgr.findChild(cursor, letter);
        }
        return nodeMgr.getIsEOW(cursor);
    }

    private static void handleIllegalCharacter(char letter) {
        var alphabeticalIndex = letter - 'a';
        if (alphabeticalIndex < 0 || alphabeticalIndex > 26)
            throw new IllegalArgumentException(
                    "The word can only contains alphabetical letters.");
    }

    private void handleMeaninglessInput(String word) {
        if (word == null)
            throw new IllegalArgumentException(
                    "Trie can't operate with null arguments.");
        if (word.equals(""))
            throw new IllegalArgumentException(
                    "Trie can't operate with empty string.");
    }

    public static class NodeMgr {
        public Boolean safeAdd(Node node, char letter) {
            var success = false;
            if (!node.hasChild(letter)) {
                node.addChild(letter);
                success = true;
            }
            return success;
        }

        public Node findChild(Node node, char letter) {
            return node.getChild(letter);
        }

        public Boolean pruneChild(Node node, char letter) {
            if (node.hasChild(letter) && node.getChild(letter).hasNoChild()) {
                node.removeChild(letter);
                return true;
            }
            return false;
        }

        public void setIsEOW(Node node, Boolean isEOW) {
            node.setEOW(isEOW);
        }

        public Boolean getIsEOW(Node node) {
            return node.getEOW();
        }
    }
}
