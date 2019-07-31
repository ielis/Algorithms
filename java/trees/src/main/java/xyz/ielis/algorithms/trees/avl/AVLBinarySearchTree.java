package xyz.ielis.algorithms.trees.avl;

import xyz.ielis.algorithms.trees.BinarySearchTree;
import xyz.ielis.algorithms.trees.Node;
import xyz.ielis.algorithms.trees.util.BinarySearchTreeUtils;

import java.util.*;

public class AVLBinarySearchTree<K, V> implements BinarySearchTree<K, V> {


    private final Comparator<K> comparator;

    private AVLNode<K, V> root;

    private AVLBinarySearchTree(Comparator<K> comparator) {
        this.comparator = comparator;
    }

    public static <K, V> AVLBinarySearchTree<K, V> make(Comparator<K> keyComparator) {
        return new AVLBinarySearchTree<>(keyComparator);
    }

    private static <K, V> Optional<AVLNode<K, V>> search(AVLNode<K, V> node, K key, Comparator<K> comparator) {
        if (node == null) {
            return Optional.empty();
        }

        if (node.getKey().equals(key)) {
            return Optional.of(node);
        }

        if (comparator.compare(key, node.getKey()) < 0) {
            return search(node.getLeft(), key, comparator);
        } else {
            return search(node.getRight(), key, comparator);
        }
    }

    @Override
    public Node<K, V> getRoot() {
        return root;
    }

    @Override
    public void insert(K key, V value) {
        root = insertNode(root, key, value);
    }

    @Override
    public void remove(K key) {
        if (root == null) {
            return;
        }
        root = removeNode(root, key);
    }

    @Override
    public boolean contains(K key) {
        return search(key).isPresent();
    }

    @Override
    public Optional<Set<V>> search(K key) {
        return search(root, key, comparator)
                .map(AVLNode::getValues);
    }

    @Override
    public Optional<Set<V>> min() {
        AVLNode<K, V> mn = (AVLNode<K, V>) BinarySearchTreeUtils.minimum(root);
        return (mn == null)
                ? Optional.empty()
                : Optional.of(mn.getValues());

    }

    @Override
    public Optional<Set<V>> max() {
        AVLNode<K, V> mn = (AVLNode<K, V>) BinarySearchTreeUtils.maximum(root);
        return (mn == null)
                ? Optional.empty()
                : Optional.of(mn.getValues());

    }

    @Override
    public Iterator<Set<V>> iterator() {
        // TODO implement
        return Collections.emptyIterator();
    }

    /**
     * @return number of levels of the tree. An empty tree has <code>0</code> levels, a tree with 1 item has
     * <code>1</code> level, etc.
     */
    public int getHeight() {
        return root == null ? 0 : root.getHeight() + 1;
    }

    public void inorderTraverse() {
        // TODO - do we need this?
        inOrderTraversal(root);
    }

    /**
     * Recursive implementation of removing data from a tree
     * Three cases:
     * 1. No child nodes.
     * 2. Single child.
     * 3. Two children.
     *
     * @return the node that is to be removed. Return null if no data is removed.
     */
    private AVLNode<K, V> removeNode(AVLNode<K, V> currentNode, K dataToRemove) {

        // Base case
        if (currentNode == null) {
            return null;
        }

        AVLNode<K, V> leftChild = currentNode.getLeft();
        AVLNode<K, V> rightChild = currentNode.getRight();
        K currentKey = currentNode.getKey();

        if (comparator.compare(dataToRemove, currentKey) == 0) {

            System.out.println("Found the data that we want to remove: " + currentKey);

            if (leftChild == null && rightChild == null) {
                System.out.println("Removing a leaf node");
                return null;
            } else if (leftChild == null) {
                System.out.println("Removing a node with a right child");
                currentNode = null;
                return rightChild;
            } else if (rightChild == null) {
                System.out.println("Removing a node with a left child");
                currentNode = null;
                return leftChild;
            } else {
                System.out.println("Removing a node with two children");
                // Find the largest node on the left sub-tree
                AVLNode<K, V> largestInLeftSubtree = (AVLNode<K, V>) BinarySearchTreeUtils.maximum(leftChild);

                // Swap the root node with the largest in left sub-tree
                // TODO - check carefully if this is OK
//                currentNode.setData(largestInLeftSubtree.getData());
                currentNode = largestInLeftSubtree;
                // Set left-child recursively. Remove the copy left of the largest left child
                currentNode.setLeft(removeNode(currentNode.getLeft(), largestInLeftSubtree.getKey()));

            }
        } else if (comparator.compare(dataToRemove, currentKey) < 0) {
            System.out.println("Traversing to the left ---");
            currentNode.setLeft(removeNode(leftChild, dataToRemove));
        } else {
            System.out.println("Traversing to the right ---");
            currentNode.setRight(removeNode(rightChild, dataToRemove));
        }

        // Update the height parameter
        currentNode.setHeight(calculateTreeHeight(currentNode));

        // Check on every delete operation whether tree has become unbalanced
        return balanceTreeAfterDeletion(currentNode);
    }

    /**
     * Check whether the tree is unbalanced after a delete operation
     *
     * @return Node The node that is deleted.
     */
    private AVLNode<K, V> balanceTreeAfterDeletion(AVLNode<K, V> currentNode) {

        int balanceValue = getBalanceValue(currentNode);

        // Left heavy situation. Can be left-left or left-right
        if (balanceValue > 1) {
            // Left-right rotation required. Left rotation on the right child of the root node.
            if (getBalanceValue(currentNode.getLeft()) < 0) {
                currentNode.setLeft(leftRotation(currentNode.getLeft()));
            }

            return rightRotation(currentNode);
        }

        // Right heavy situation. Can be right-right or right-left
        if (balanceValue < -1) {
            // right - left situation. Left rotation on the right child of the root node.
            if (getBalanceValue(currentNode.getRight()) > 0) {
                currentNode.setRight(rightRotation(currentNode.getRight()));
            }
            // left rotation on the root node
            return leftRotation(currentNode);
        }

        return currentNode;
    }


    /**
     * Implement recursively to avoid storing a node pointer to the parent.
     * Remember that in the JavaScript implementation of the Binary Search Tree,
     * we required the pointer to the parent.
     * <p>
     * This method will ALWAYS return the root.
     *
     * @param currentNode
     * @param key
     */
    private AVLNode<K, V> insertNode(AVLNode<K, V> currentNode, K key, V value) {

        // The current root node is empty. Create a new node here
        if (currentNode == null) {
            return new AVLNode<>(key, value);
        }

        // Is data to insert smaller than the current key value.
        // Go to the left.
        if (comparator.compare(key, currentNode.getKey()) < 0) {
            currentNode.setLeft(insertNode(currentNode.getLeft(), key, value));
        } else if (comparator.compare(key, currentNode.getKey()) > 0) {
            currentNode.setRight(insertNode(currentNode.getRight(), key, value));
        } else {
            currentNode.addValue(value);
        }

        currentNode = balanceTree(currentNode, key, value);

        // Finally, update the height calculateTreeHeight(rootNode)
        currentNode.setHeight(calculateTreeHeight(currentNode));

        return currentNode;
    }

    /**
     * After the insertion/removal method, check to see if tree is balanced.
     * Check for the following four cases
     * 1. Left rotation       - right heavy situation
     * 2. Right rotation      - Left heavy situation
     * 3. Left-right rotation - right heavy situation
     * 4. Right-left rotation - Left heavy situation
     */
    private AVLNode<K, V> balanceTree(AVLNode<K, V> currentNode, K key, V value) {

        int balanceValue = getBalanceValue(currentNode);

        // Right heavy situation - left rotation
        if (isRightHeavy(balanceValue)
                && comparator.compare(key, currentNode.getRight().getKey()) > 0) {
            return leftRotation(currentNode);
        }

        // Left heavy situation - Right rotation
        if (isLeftHeavy(balanceValue)
                && comparator.compare(key, currentNode.getLeft().getKey()) < 0) {
            return rightRotation(currentNode);
        }

        // Left right
        if (isLeftHeavy(balanceValue) &&
                comparator.compare(key, currentNode.getLeft().getKey()) > 0) {
            currentNode.setLeft(insertNode(currentNode.getLeft(), key, value));
            return rightRotation(currentNode);
        }

        // right-left
        if (isRightHeavy(balanceValue) &&
                comparator.compare(key, currentNode.getRight().getKey()) < 0) {
            currentNode.setRight(insertNode(currentNode.getRight(), key, value));
            return leftRotation(currentNode);
        }

        return currentNode;
    }


    /**
     * Get the balance value of the current node. If difference between the height
     * of the left and right subtree is greater than 1, that means it is skewed disproportionately
     * to the left. It is therefore left heavy situation.
     * If however, the difference is -2 or less, that means it is skewed disproportionately
     * to the right, meaning it is a right heavy situation.
     * Otherwise, it is balanced.
     */
    private int getBalanceValue(AVLNode<K, V> currentNode) {
        if (currentNode == null) {
            return 0;
        }
        return height(currentNode.getLeft()) - height(currentNode.getRight());
    }

    /**
     * Check if the current sub-tree is balanced. If difference between the height
     * of the left and right subtree is 1 or zero, then it is balanced.
     * Otherwise, it is unbalanced.
     *
     * @param currentNode
     */
    private boolean isBalanced(AVLNode<K, V> currentNode) {
        return Math.abs(getBalanceValue(currentNode)) < 2;
    }

    /**
     * Check to see if current element is balanced based on balance value
     *
     * @param balanceValue
     */
    private boolean isBalanced(int balanceValue) {
        return balanceValue < 2 && balanceValue > -2;
    }

    /**
     * Check if tree is left heavy based on balance value.
     * Left heavy trees have more items on the left sub-tree than the right.
     */
    private boolean isLeftHeavy(int balanceValue) {
        return balanceValue > 1;
    }

    /**
     * Check if tree is right heavy based on balance value\
     * Right heavy trees have more items on the right sub-tree than the left.
     */
    private boolean isRightHeavy(int balanceValue) {
        return balanceValue < -1;
    }

    /**
     * Get the height of the tree and increment it by 1 to get the actual height.
     * Get it by taking the greater value between the height of the left and right sub-tree
     * and add one to that value.
     * Null pointers return a height of zero, because -1 + 1 = 0.
     */
    private int calculateTreeHeight(AVLNode<K, V> currentNode) {
        return Math.max(height(currentNode.getLeft()), height(currentNode.getRight())) + 1;
    }

    /**
     * A recursive implementation of the right rotation.
     * We will be utilizing the pseudo code that we wrote in the post
     */
    private AVLNode<K, V> rightRotation(AVLNode<K, V> currentNode) {
//        System.out.println("Beginning right rotation ... on node: " + currentNode.getKey());
        AVLNode<K, V> newRootNode = currentNode.getLeft();
        AVLNode<K, V> rightChildOfLeft = newRootNode.getRight();

        // Step 1. Set newRootNode as the new root node.
        newRootNode.setRight(currentNode);

        // Step 2. Set the right child of the new left child of the new root node. Quite a tongue twister right?
        currentNode.setLeft(rightChildOfLeft);

        // Step 3. Update the height of the trees that were updated.
        newRootNode.setHeight(calculateTreeHeight(newRootNode));
        currentNode.setHeight(calculateTreeHeight(currentNode));

        return newRootNode;
    }

    private AVLNode<K, V> leftRotation(AVLNode<K, V> currentNode) {

//        System.out.println("Beginning left rotation ... on node: " + currentNode.getKey());

        AVLNode<K, V> newRootNode = currentNode.getRight();
        AVLNode<K, V> leftChildOfRight = newRootNode.getLeft();

        // Step 1. set the left child of the new root node
        newRootNode.setLeft(currentNode);

        // Step 2. set the right child of the new left child
        currentNode.setRight(leftChildOfRight);

        // Step 3. Update the height of the trees that were updated.
        newRootNode.setHeight(calculateTreeHeight(newRootNode));
        currentNode.setHeight(calculateTreeHeight(currentNode));

        return newRootNode;
    }

    /**
     * Used internally
     * <p>
     * Height is calculated via the following formula
     * height = max (leftSubTreeHeight, rightSubTreeHeight) + 1
     *
     * @return -1 if the node is null.  Otherwise, return the height.
     */
    private int height(AVLNode<K, V> currentNode) {
        if (currentNode == null) {
            return -1;
        }
        return currentNode.getHeight();
    }

    /**
     * In order traversal implementation
     * 1. Visit left sub-tree
     * 2. Visit root
     * 3. Visit right sub-tree
     *
     * @param currentNode the node that we are currently at
     */
    private void inOrderTraversal(AVLNode<K, V> currentNode) {

        AVLNode<K, V> leftChild = currentNode.getLeft();

        if (leftChild != null) {
            inOrderTraversal(leftChild);
        }

        System.out.print(currentNode + " --> ");

        AVLNode<K, V> rightChild = currentNode.getRight();

        if (rightChild != null) {
            inOrderTraversal(rightChild);
        }

    }
}
