package com.idontwantagirlfriend.Tree;

import java.util.function.Consumer;

public class AVLTree<E extends Comparable<E>> implements Tree<E> {
    private Node root;
    
    @Override
    public void insert(E element) {
        insertOnly(element);
        hardUpdateHeight(root);
        detectRotation();
        hardUpdateHeight(root);
    }


    @Override
    public Boolean find(E element) {
        return null;
    }

    @Override
    public int height() {
        return root.getHeight();
    }

    @Override
    public String toString() {
        return root.toString();
    }

    public Boolean equals(AVLTree<E> tree2) {
        if (root == null && tree2.getRoot() == null) return true;
        if (root == null || tree2.getRoot() == null) return false;
        return root.equals(tree2.getRoot());
    }

    public Node getRoot() {
        return root;
    }
        
    public void insertOnly(E element) {
        if (element == null) return;
        var node = new Node(element);
        if (root == null) {
            root = node;
        } else {
            insertOnly(root, node);
        }
    }

    private void insertOnly(Node baseNode, Node insertedNode) {
        var shouldBeInsertedOnTheLeft
                = insertedNode.get()
                    .compareTo(baseNode.get()) <= 0;
        if (shouldBeInsertedOnTheLeft) {
            if (!baseNode.hasLeft()) {
                baseNode.setLeft(insertedNode);
                return;
            }
            insertOnly(baseNode.getLeft(), insertedNode);
        } else {
            if (!baseNode.hasRight()) {
                baseNode.setRight(insertedNode);
                return;
            }
            insertOnly(baseNode.getRight(), insertedNode);
        }
    }

    private void hardUpdateHeight(Node node) {
//        root node can't be null after an insertion,
//        so no need to check node being null
        if (node.hasLeft()) hardUpdateHeight(node.getLeft());
        if (node.hasRight()) hardUpdateHeight(node.getRight());

        softUpdateHeight(node);
    }
    
    private void softUpdateHeight(Node node) {
        var leftHeight
                = node.hasLeft()
                ? node.getLeft().getHeight()
                : -1;
        var rightHeight
                = node.hasRight()
                ? node.getRight().getHeight()
                : -1;
        node.setHeight(
                1 + Math.max(leftHeight, rightHeight));
    }

    private void detectRotation() {
        detectRotation(root, this::setRoot);
    }

    private void detectRotation(Node node, Consumer<Node> bindToParent) {
        if (!node.hasLeft() && !node.hasRight()) return;
        if (node.hasLeft())
            detectRotation(node.getLeft(), node::setLeft);
        if (node.hasRight())
            detectRotation(node.getRight(), node::setRight);

        int leftGreaterBy = balanceFactor(node);

        if (leftGreaterBy > 1)
            bindToParent.accept(doRightRotationAndGetNewBaseNode(node));
        if (leftGreaterBy < -1)
            bindToParent.accept(doLeftRotationAndGetNewBaseNode(node));
    }

    private Node doLeftRotationAndGetNewBaseNode(Node node) {
        var rightNode = node.getRight();
        if (rightNode.hasLeft()) {
            var rightNodeLeftChild = rightNode.getLeft();
            rightNode.setLeft(null);
            rightNodeLeftChild.setRight(rightNode);
            node.setRight(rightNodeLeftChild);
            rightNode = rightNodeLeftChild;
        }
        node.setRight(null);
        rightNode.setLeft(node);
        return rightNode;
    }

    private Node doRightRotationAndGetNewBaseNode(Node node) {
        var leftNode = node.getLeft();
        if (leftNode.hasRight()) {
            var leftNodeRightChild = leftNode.getRight();
            leftNode.setRight(null);
            leftNodeRightChild.setLeft(leftNode);
            node.setLeft(leftNodeRightChild);
            leftNode = leftNodeRightChild;
        }
        node.setLeft(null);
        leftNode.setRight(node);
        return leftNode;
    }

    private int balanceFactor(Node node) {
        var leftHeight 
                = node.hasLeft()
                ? node.getLeft().getHeight()
                : -1;
        var rightHeight
                = node.hasRight()
                ? node.getRight().getHeight()
                : -1;
        return leftHeight - rightHeight;
    }

    public void setRoot(Node root) {
        this.root = root;
    }

    private class Node {
        private Node left;
        private Node right;
        private int height;
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

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
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
