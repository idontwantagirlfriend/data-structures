package com.idontwantagirlfriend.Tree;

public class BST<E extends Comparable<E>> extends AbstractTree<E> {

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

    public Boolean equals(BST<E> tree) {
        if (root == null) return tree.getRoot() == null;

        return root.equals(tree.getRoot());
    }

    private Node getRoot() {
        return root;
    }

    public void traversePreOrder() {
        traversePreOrder(root);
    }

    private void traversePreOrder(Node node) {
        if (node == null) return;

        System.out.println(node.get());
        traversePreOrder(node.getLeft());
        traversePreOrder(node.getRight());
    }

    public void traverseInOrder() {
        traverseInOrder(root);
    }

    private void traverseInOrder(Node node) {
        if (node == null) return;

        traverseInOrder(node.getLeft());
        System.out.println(node.get());
        traverseInOrder(node.getRight());
    }

    public void traversePostOrder() {
        traversePostOrder(root);
    }

    private void traversePostOrder(Node node) {
        if (node == null) return;

        traversePostOrder(node.getLeft());
        traversePostOrder(node.getRight());
        System.out.println(node.get());
    }

    @Override
    public String toString() {
        return root.toString();
    }
}
