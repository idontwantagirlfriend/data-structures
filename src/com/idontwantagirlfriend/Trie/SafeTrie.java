package com.idontwantagirlfriend.Trie;

public class SafeTrie {
    private final CharNode root;

    public SafeTrie(CharNode root) {
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
            if (!cursor.contains(letter)) {
                cursor.add(letter);
            }
            cursor = cursor.getChild(letter);
        }
        cursor.setEOW(true);
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
            if (!cursor.contains(letter)) return null;
            cursor = cursor.getChild(letter);
        }

//        word.length() > 1 has been ensured.
        var lastLetter = lowercase.charAt(word.length() - 1);
        if (!cursor.contains(lastLetter)
                || !cursor.getChild(lastLetter).getEOW())
            return null;

        cursor.getChild(lastLetter).setEOW(false);
        if (cursor.contains(lastLetter) && cursor.getChild(lastLetter).isLeaf()) {
            cursor.removeChild(lastLetter);
        }
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
            if (!cursor.contains(letter)) return false;
            cursor = cursor.getChild(letter);
        }
        return cursor.getEOW();
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
