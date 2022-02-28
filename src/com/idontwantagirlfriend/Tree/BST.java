package com.idontwantagirlfriend.Tree;

public class BST<E extends Comparable<E>> implements Tree<E> {
    private Node root;

    @Override
    public void insert(E element) {
        if (root == null) {
            root = new Node(element);
            return;
        }

        var node = new Node(element);
        var cursorNode = root;
        while (true) if (element.compareTo(cursorNode.get()) <= 0) {
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

    @Override
    public Boolean find(E element) {
        if (root == null) return false;

        var cursorNode = root;
        while (cursorNode != null) {
            if (element.compareTo(cursorNode.get()) < 0) {
                cursorNode = cursorNode.getLeft();
            } else if (element.compareTo(cursorNode.get()) > 0) {
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

    private class Node {
        private Node left;
        private Node right;
        private E value;

        public Node(E value) {
            this.value = value;
        }

        public Boolean hasLeft() {
            return left != null;
        }

        public Boolean hasRight() {
            return right != null;
        }

        public E get() {
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
            if (!this.hasLeft() && !this.hasRight()) return "[" + value.toString() + "]";
//            In-order traversal
            var leftString = hasLeft() ? left.toString() : "null";
            var rightString = hasRight() ? right.toString() : "null";
            return "[" + value.toString() + ", " + leftString + ", " + rightString + "]";
        }
    }
}
