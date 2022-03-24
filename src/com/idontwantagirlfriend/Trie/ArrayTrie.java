package com.idontwantagirlfriend.Trie;

public class ArrayTrie {
    private Node root;

    public ArrayTrie() {
        this.root = new Node();
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
            cursor.safeAdd(letter);
            cursor = cursor.findChild(letter);
        }
        cursor.setIsEOW(true);
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
            cursor = cursor.findChild(letter);
        }

//        word.length() > 1 has been ensured.
        var lastLetter = lowercase.charAt(word.length() - 1);
        if (!cursor.hasChild(lastLetter)
                || !cursor.findChild(lastLetter).getIsEOW())
            return null;
        if (!cursor.pruneChild(lastLetter))
            cursor.findChild(lastLetter).setIsEOW(false);
        cursor.setIsEOW(false);
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
            cursor = cursor.findChild(letter);
        }
        return cursor.getIsEOW();
    }

    public static class Node {

        public static int getLetterPosition(char letter) {
            return letter - 'a';
        }

        private final Node[] subtrees;
        private final int ALPHABET_NUMBER = 26;
        private char letter;
        private Boolean isEOW;

        public Node() {
            subtrees = new Node[ALPHABET_NUMBER];
            isEOW = false;
        }

        public Node(char letter) {
            this();
            this.letter = letter;
        }

        public char getLetter() {
            return letter;
        }

        public Boolean safeAdd(char letter) {
            var success = false;
            if (!hasChild(letter)) {
                addChild(letter);
                success = true;
            }
            return success;
        }

        public Node findChild(char letter) {
            handleIllegalCharacter(letter);
            return subtrees[getLetterPosition(letter)];
        }

        private Node setChild(int position, Node child) {
            subtrees[position] = child;
            return child;
        }

        public Boolean hasChild(char letter) {
            return findChild(letter) != null;
        }

        public Boolean hasNoChild() {
            for (var i = 0; i < ALPHABET_NUMBER; i++) {
                if (subtrees[i] != null) return false;
            }
            return true;
        }

        public Node removeChild(char letter) {
            if (hasChild(letter)) {
                var removed = findChild(letter);
                setChild(getLetterPosition(letter), null);
                return removed;
            }
            return null;
        }

        public Node addChild(char letter) {
            handleIllegalCharacter(letter);
            var newChild = new Node(letter);
            return setChild(getLetterPosition(letter), newChild);
        }

        public Boolean pruneChild(char letter) {
            if (hasChild(letter) && findChild(letter).hasNoChild()) {
                removeChild(letter);
                return true;
            }
            return false;
        }

        public void setIsEOW(Boolean isEOW) {
            this.isEOW = isEOW;
        }

        public Boolean getIsEOW() {
            return this.isEOW;
        }

        private void handleIllegalCharacter(char letter) {
            var position = getLetterPosition(letter);
            if (position < 0 || position > 26)
                throw new IllegalArgumentException(
                        "Only accept lowercase alphabetical letter.");
        }
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
}
