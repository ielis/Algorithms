package xyz.ielis.algorithms.trees;

import xyz.ielis.algorithms.trees.avl.AVLBinarySearchTree;
import xyz.ielis.algorithms.trees.simple.SimpleBinarySearchTree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Comparator;
import java.util.function.Function;

/**
 * This class is an API to binary search trees implemented in this package/module.
 */
public class BinarySearchTrees {

    private BinarySearchTrees() {
        // private no-op
    }

    public static <K, V> void serializeSimpleBST(SimpleBinarySearchTree<K, V> tree,
                                                 OutputStream outputStream,
                                                 Function<K, String> keySerializer,
                                                 Function<V, String> valueSerializer) throws IOException {
        SimpleBinarySearchTree.Serializer<K, V> ser = new SimpleBinarySearchTree.Serializer<>(keySerializer, valueSerializer);
        ser.serialize(tree.getRoot(), outputStream);
    }

    public static <K, V> BinarySearchTree<K, V> deserializeSimpleBST(BufferedReader reader,
                                                                     Comparator<K> keyComparator,
                                                                     Function<String, K> keyDecoder,
                                                                     Function<String, V> valueDecoder) {
        SimpleBinarySearchTree.Deserializer<K, V> deserializer = new SimpleBinarySearchTree.Deserializer<>(keyDecoder, valueDecoder);
        return deserializer.decode(reader, keyComparator);
    }

    public static <K, V> BinarySearchTree<K, V> simpleBinarySearchTree(Comparator<K> keyComparator) {
        return SimpleBinarySearchTree.make(keyComparator);
    }

    public static <K, V> BinarySearchTree<K, V> avlBinarySearchTree(Comparator<K> keyComparator) {
        return AVLBinarySearchTree.make(keyComparator);
    }
}
