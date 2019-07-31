package xyz.ielis.algorithms.trees.util;

import xyz.ielis.algorithms.trees.Node;

public class BinarySearchTreeUtils {

    private BinarySearchTreeUtils() {
        // private no-op
    }

    public static <K, V> Node<K, V> minimum(Node<K, V> node) {
        while (node != null && node.getLeft() != null) {
            node = node.getLeft();
        }
        return node;
    }

    public static <K, V> Node<K, V> maximum(Node<K, V> node) {
        while (node != null && node.getRight() != null) {
            node = node.getRight();
        }
        return node;
    }
}
