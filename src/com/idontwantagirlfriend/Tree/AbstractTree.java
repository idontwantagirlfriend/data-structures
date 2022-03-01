package com.idontwantagirlfriend.Tree;

public abstract class AbstractTree<E extends Comparable<E>> implements Tree<E>{
    @Override
    public abstract void insert(E element);

    @Override
    public abstract Boolean find(E element);

    @Override
    public abstract int height();

    protected class Node {
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
            if (!this.hasLeft() && !this.hasRight()) return "[" + value.toString() + "]";
//            In-order traversal
            var leftString = hasLeft() ? left.toString() : "null";
            var rightString = hasRight() ? right.toString() : "null";
            return "[" + value.toString() + ", " + leftString + ", " + rightString + "]";
        }
    }
}
