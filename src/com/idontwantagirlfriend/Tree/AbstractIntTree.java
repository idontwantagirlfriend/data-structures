package com.idontwantagirlfriend.Tree;

public abstract class AbstractIntTree {
    public abstract void insert(int number);
    public abstract Boolean find(int number);
    public abstract int height();

    protected static class Node {
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

        public Boolean equals(Node node) {
            if (node == null) return false;
            var selfEqual = value == node.get();

            var leftEqual = (left == null)
                    ? node.getLeft() == null
                    : left.equals(node.getLeft());
            var rightEqual = (right == null)
                    ? node.getRight() == null
                    : right.equals(node.getRight());
            return selfEqual && leftEqual && rightEqual;
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
