package com.idontwantagirlfriend.Tree;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class AVLTree<E extends Comparable<E>> implements Tree<E> {
    private Node root;

    @Override
    public void insert(E element) {

        BiConsumer<Node, Consumer<Node>> adaptedUpdateNodeHeight
                = (node, parentRebind) -> softUpdateNodeHeight(node);

        if (root == null) {
            root = new Node(element);
        } else {
            var node = traverseTheTreeAndGetTheRelevantNode(
                    element);
            if (element.compareTo(node.get()) <= 0) {
                node.setLeft(new Node(element));
            } else if (element.compareTo(node.get()) > 0) {
                node.setRight(new Node(element));
            }

            traverseTheTreeAndGetTheRelevantNode(
                    element, adaptedUpdateNodeHeight.andThen(this::rotateWhenNeeded));
        }
    }

    @Override
    public int height() {
        if (root == null) return -1;
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


    public void setRoot(Node root) {
        this.root = root;
    }


    private void softUpdateNodeHeight(Node node) {
        var leftHeight
                = (!node.hasLeft())
                ? -1
                : node.getLeft().getHeight();
        var rightHeight
                = (!node.hasRight())
                ? -1
                : node.getRight().getHeight();
        node.setHeight(
                Math.max(leftHeight, rightHeight) + 1
        );
    }

    /**
     * Depending on the node position and the rebind method provided
     * by traversal methods, fire the actual rotation methods.
     * @param node
     * @param parentRebind A callback function that can update the parent
     *                    node's left/right child after each rotation.
     */
    private void rotateWhenNeeded(Node node, Consumer<Node> parentRebind) {
        if (balanceFactor(node) > 1) {
            if (balanceFactor(node.getLeft()) > 0) {
                performLLOn(node, parentRebind);
            } else {
                performLROn(node, parentRebind);
            }
        } else if (balanceFactor(node) < -1) {
            if (balanceFactor(node.getRight()) > 0) {
                performRLOn(node, parentRebind);
            } else {
                performRROn(node, parentRebind);
            }
        }
    }


    private Node traverseTheTreeAndGetTheRelevantNode(E element, BiConsumer<Node, Consumer<Node>> hookedCallback) {
        return traverseTheSubtreeAndGetTheRelevantNode(root, element, null, hookedCallback);
    }

    /**
     * Traverse the subtree recursively.
     * @param node
     * @param element
     * @param lastNode the parent node of this subtree
     * @param hookedCallback  A callback function on each node,
     *                        but involves information
     *                        about {@code lastNode}. In this case,
     *                        We need to update the parent node's
     *                        left/right child after each rotation.
     * @return The "relevant" node upon each recursion.
     */
    private Node traverseTheSubtreeAndGetTheRelevantNode(Node node, E element, Node lastNode, BiConsumer<Node, Consumer<Node>> hookedCallback) {
        var shouldBeInsertedOnTheLeft
                = element.compareTo(node.get()) <= 0;
        if (shouldBeInsertedOnTheLeft) {
            return traverseTheLeftSubtreeAndGetTheRelevantNode(node, element, lastNode, hookedCallback);
        } else {
            return traverseTheRightSubtreeAndGetTheRelevantNode(node, element, lastNode, hookedCallback);
        }
    }


    /**
     * The recursion from {@code traverseTheSubtreeAndGetTheRelevantNode}
     * continues here and practically ends here, and the callback
     * function is fired based on the lastNode provided from the last layer
     * of recursion.
     * @param node
     * @param element
     * @param lastNode
     * @param hookedCallback a callback function on the current node and
     *                       the last node.
     * @return
     */
    private Node traverseTheLeftSubtreeAndGetTheRelevantNode(Node node, E element, Node lastNode, BiConsumer<Node, Consumer<Node>> hookedCallback) {
        var relevantNode = (!node.hasLeft())
                ? node
                : traverseTheSubtreeAndGetTheRelevantNode(node.getLeft(), element, node, hookedCallback);
        Consumer<Node> hook = createHookedCallback(node, lastNode);
        hookedCallback.accept(node, hook);
        return relevantNode;
    }

    /**
     * The recursion from {@code traverseTheSubtreeAndGetTheRelevantNode}
     * continues here and practically ends here, and the callback
     * function is fired based on the lastNode provided from the last layer
     * of recursion.
     * @param node
     * @param element
     * @param lastNode
     * @param hookedCallback a callback function on the current node and
     *                       the last node.
     * @return
     */
    private Node traverseTheRightSubtreeAndGetTheRelevantNode(Node node, E element, Node lastNode, BiConsumer<Node, Consumer<Node>> hookedCallback) {
        var relevantNode = (!node.hasRight())
                ? node
                : traverseTheSubtreeAndGetTheRelevantNode(node.getRight(), element, node, hookedCallback);
        Consumer<Node> hook = createHookedCallback(node, lastNode);
        hookedCallback.accept(node, hook);
        return relevantNode;
    }

    /**
     * Create a consumer that will bind a node to the
     * left / right position of lastNode depending on the position
     * of the current node. <br/>
     * Without this rebinding process, the rotated nodes will run into chaotic
     * relationships after each rotation.
     * @param node
     * @param lastNode
     * @return a binding function for each node to rotate
     */
    private Consumer<Node> createHookedCallback(Node node, Node lastNode) {
        if (lastNode == null) return this::setRoot;
        return node == lastNode.getLeft()
        ? lastNode::setLeft
        : lastNode::setRight;
    }

    /**
     * Traverse the tree and execute a function on each node.
     * Similar to {@code Stream.peek}.
     * @param element
     * @param callback
     * @return the most "relevant" node to insert the element onto
     */
    private Node traverseTheTreeAndGetTheRelevantNode(E element, Consumer<Node> callback) {
        if (root == null) return null;
        return traverseTheSubtreeAndGetTheRelevantNode(root, element, callback);
    }

    private Node traverseTheSubtreeAndGetTheRelevantNode(Node node, E element, Consumer<Node> callback) {
        var shouldBeInsertedOnTheLeft
                = element.compareTo(node.get()) <= 0;
        if (shouldBeInsertedOnTheLeft) {
            return traverseTheLeftSubtreeAndGetTheRelevantNode(node, element, callback);
        } else {
            return traverseTheRightSubtreeAndGetTheRelevantNode(node, element, callback);
        }
    }


    private Node traverseTheLeftSubtreeAndGetTheRelevantNode(Node node, E element, Consumer<Node> callback) {
        var relevantNode = (!node.hasLeft())
                ? node
                : traverseTheSubtreeAndGetTheRelevantNode(node.getLeft(), element, callback);
        callback.accept(node);
        return relevantNode;
    }

    private Node traverseTheRightSubtreeAndGetTheRelevantNode(Node node, E element, Consumer<Node> callback) {
        var relevantNode = (!node.hasRight())
                ? node
                : traverseTheSubtreeAndGetTheRelevantNode(node.getRight(), element, callback);
        callback.accept(node);
        return relevantNode;
    }

    private Node traverseTheTreeAndGetTheRelevantNode(E element) {
        return traverseTheSubtreeAndGetTheRelevantNode(root, element);
    }

    private Node traverseTheSubtreeAndGetTheRelevantNode(Node node, E element) {
        var shouldBeInsertedOnTheLeft
                = element.compareTo(node.get()) <= 0;
        if (shouldBeInsertedOnTheLeft) {
            return traverseTheLeftSubtreeAndGetTheRelevantNode(node, element);
        } else {
            return traverseTheRightSubtreeAndGetTheRelevantNode(node, element);
        }
    }


    private Node traverseTheLeftSubtreeAndGetTheRelevantNode(Node node, E element) {
        var relevantNode = (!node.hasLeft())
                ? node
                : traverseTheSubtreeAndGetTheRelevantNode(node.getLeft(), element);
        return relevantNode;
    }

    private Node traverseTheRightSubtreeAndGetTheRelevantNode(Node node, E element) {
        var relevantNode = (!node.hasRight())
                ? node
                : traverseTheSubtreeAndGetTheRelevantNode(node.getRight(), element);
        return relevantNode;
    }


    /**
     * Perform a LL rotation (single right rotation) on the given node,
     * and afterward, fire a callback function {@code parentRebind}
     * provided by traversal methods, as the left node is taking
     * the position of {@code node} after each rotation, and we need to
     * update its relationship with the parent node.
     * @param node
     * @param parentRebind
     */
    private void performLLOn(Node node, Consumer<Node> parentRebind) {
        parentRebind.accept(null);

        var leftNode = node.getLeft();
        node.setLeft(leftNode.getRight());
        leftNode.setRight(node);

        parentRebind.accept(leftNode);

        // the height of leftNode will not change before and after
        // this rotation. Mathematically provable.
        softUpdateNodeHeight(node);
    }

    /**
     * Perform a RR rotation (single left rotation) on the given node,
     * and afterward, fire a callback function {@code parentRebind}
     * provided by traversal methods, as the right node is taking
     * the position of {@code node} after each rotation, and we need to
     * update its relationship with the parent node.
     * @param node
     * @param parentRebind
     */
    private void performRROn(Node node, Consumer<Node> parentRebind) {
        parentRebind.accept(null);

        var rightNode = node.getRight();
        node.setRight(rightNode.getLeft());
        rightNode.setLeft(node);

        parentRebind.accept(rightNode);

        // the height of rightNode will not change before and after
        // this rotation. Mathematically provable.
        softUpdateNodeHeight(node);
    }

    /**
     * Double rotation on {@code node}. A RR rotation on the left child
     * then an LL rotation on itself. For the usage of parentRebind,
     * check up the LL and RR rotation methods.
     * @param node
     * @param parentRebind
     */
    private void performLROn(Node node, Consumer<Node> parentRebind) {
        var leftNode = node.getLeft();
        performRROn(leftNode, node::setLeft);
        performLLOn(node, parentRebind);
    }

    /**
     * Double rotation on {@code node}. An LL rotation on the left child
     * then a RR rotation on itself. For the usage of parentRebind,
     * check up the LL and RR rotation methods.
     * @param node
     * @param parentRebind
     */
    private void performRLOn(Node node, Consumer<Node> parentRebind) {
        var rightNode = node.getRight();
        performLLOn(rightNode, node::setRight);
        performRROn(node, parentRebind);
    }

    /**
     * Find if an element exist in the tree.
     * @param element
     * @return Boolean
     */
    @Override
    public Boolean find(E element) {
        return find(root, element) != null;
    }

    private Node find(Node node, E element) {
        if (node == null || element.compareTo(node.get()) == 0) {
            return node;
        } else if (element.compareTo(node.get()) < 0) {
            return find(node.getLeft(), element);
        } else return find(node.getRight(), element);
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
                    ? null == node.getLeft()
                    : left.equals(node.getLeft());
            var rightEqual = (right == null)
                    ? null == node.getRight()
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
