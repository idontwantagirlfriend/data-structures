package com.idontwantagirlfriend.Trie;

public class ArrayTrie {
    private Node root;

    public void add(String word) {}

    public String remove(String word) {
        return null;
    }

    public Boolean find(String word) {
        return false;
    }

    public int count() {
        return 0;
    }

    public static class Node {
        private final Node[] subtrees;
        private char letter;
        private Boolean isEOW;

        public Node() {
            subtrees = new Node[26];
            isEOW = false;
        }

        public Node(char letter) {
            this();
            this.letter = letter;
        }

        public static int getLetterPosition(char letter) {
            return letter - 'a';
        }

        public char getLetter() {
            return letter;
        }

        public Node addChild(char letter) {
            handleIllegalCharacter(letter);
            var newChild = new Node(letter);
            subtrees[getLetterPosition(letter)] = newChild;
            return newChild;
        }

        public Node getChild(char letter) {
            handleIllegalCharacter(letter);
            return subtrees[getLetterPosition(letter)];
        }

        public Boolean hasChild(char letter) {
            return getChild(letter) != null;
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
}
