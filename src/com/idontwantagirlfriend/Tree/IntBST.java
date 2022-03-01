package com.idontwantagirlfriend.Tree;

public class IntBST extends AbstractIntTree{
    private Node root;

    @Override
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

    @Override
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


    @Override
    public int height() {
        return height(root);
    }

    public Boolean equals(IntBST tree) {
        if (root == null) return tree.getRoot() == null;

        return root.equals(tree.getRoot());
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

    public Node getRoot() {
        return root;
    }
}
