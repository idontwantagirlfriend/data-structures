package com.idontwantagirlfriend.Tree;

public class IntBST {
    private Node root;

    public void insert(int element) {
        if (root == null) {
            root = new Node(element);
            return;
        }

        var node = new Node(element);
        var cursorNode = root;
        while (true) if (element <= cursorNode.get()) {
            if (!cursorNode.hasLeft()) {
                cursorNode.setLeft(node);
                return;
            }
            cursorNode = cursorNode.getLeft();
        } else {
            if (!cursorNode.hasRight()) {
                cursorNode.setRight(node);
                return;
            }
            cursorNode = cursorNode.getRight();
        }
    }

    public Boolean find(int element) {
        if (root == null) return false;

        var cursorNode = root;
        while (cursorNode != null) {
            if (element < cursorNode.get()) {
                cursorNode = cursorNode.getLeft();
            } else if (element > cursorNode.get()) {
                cursorNode = cursorNode.getRight();
            } else
                return true;
        }
        return false;
    }

    public int height() {
        return height(root);
    }

    private int height(Node node) {
        if (node == null) return -1;

        if (!node.hasLeft() && !node.hasRight()) return 0;

        return 1 + Math.max(
                height(node.getLeft()),
                height(node.getRight()));
    }

    @Override
    public String toString() {
        return root.toString();
    }

    private static class Node {
        private Node left;
        private Node right;
        private int value;

        public Node(int value) {
            this.value = value;
        }

        public Boolean hasLeft() {
            return left != null;
        }

        public Boolean hasRight() {
            return right != null;
        }

        public int get() {
            return this.value;
        }

        public Node getLeft() {
            return left;
        }

        public void setLeft(Node left) {
            this.left = left;
        }

        public Node getRight() {
            return right;
        }

        public void setRight(Node right) {
            this.right = right;
        }

        @Override
        public String toString() {
            if (!this.hasLeft() && !this.hasRight()) return "[" + value + "]";
//            In-order traversal
            var leftString = hasLeft() ? left.toString() : "null";
            var rightString = hasRight() ? right.toString() : "null";
            return "[" + value + ", " + leftString + ", " + rightString + "]";
        }
    }
}
