package xyz.ielis.algorithms.trees.simple;

import xyz.ielis.algorithms.trees.BinarySearchTree;
import xyz.ielis.algorithms.trees.util.BinarySearchTreeUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;


/**
 * Simple binary search tree. The tree is efficient when the items are inserted randomly.
 * <p>
 * Performance drops to the worst possible performance when the items are inserted in sequential order.
 *
 * @param <K> type of the key
 * @param <V>
 */
public class SimpleBinarySearchTree<K, V> implements BinarySearchTree<K, V> {

    private final Comparator<K> comparator;

    private SimpleNode<K, V> root;

    /**
     * @param comparator {@link Comparator} used to compare the key values
     */
    private SimpleBinarySearchTree(Comparator<K> comparator) {
        this.comparator = comparator;
    }

    public static <K, V> SimpleBinarySearchTree<K, V> make(Comparator<K> comparator) {
        return new SimpleBinarySearchTree<>(comparator);
    }

    private static <K, V> Optional<SimpleNode<K, V>> search(SimpleNode<K, V> node, K key, Comparator<K> comparator) {
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

    private static <K, V> SimpleNode<K, V> successor(SimpleNode<K, V> node) {
        if (node.getRight() != null) {
            return (SimpleNode<K, V>) BinarySearchTreeUtils.minimum(node.getRight());
        }
        SimpleNode<K, V> y = node.getParent();
        while (y != null && node == y.getRight()) {
            node = y;
            y = y.getParent();
        }
        return y;
    }

    private static <K, V> SimpleNode<K, V> predecessor(SimpleNode<K, V> node) {
        if (node.getLeft() != null) {
            return (SimpleNode<K, V>) BinarySearchTreeUtils.maximum(node.getLeft());
        }
        SimpleNode<K, V> y = node.getParent();
        while (y != null && node == y.getLeft()) {
            node = y;
            y = y.getParent();
        }
        return y;
    }

    @Override
    public SimpleNode<K, V> getRoot() {
        return root;
    }

    @Override
    public void insert(K key, V value) {
        SimpleNode<K, V> trailing = null;
        SimpleNode<K, V> x = root;
        while (x != null) {
            trailing = x;
            if (comparator.compare(key, x.getKey()) < 0) {
                x = x.getLeft();
            } else if (comparator.compare(key, x.getKey()) > 0) {
                x = x.getRight();
            } else {
                // A node with `key` already exists, hence no need to create the new node.
                // Add value to the existing node and return
                x.addValue(value);
                return;
            }
        }

        SimpleNode<K, V> z = new SimpleNode<>(key, value);

        z.setParent(trailing);
        if (trailing == null) {
            // tree was empty
            this.root = z;
        } else if (comparator.compare(z.getKey(), trailing.getKey()) < 0) {

            trailing.setLeft(z);
        } else {
            trailing.setRight(z);
        }
    }

    @Override
    public void remove(K key) {
        Optional<SimpleNode<K, V>> search = search(root, key, comparator);

        if (search.isPresent()) {
            SimpleNode<K, V> z = search.get();
            if (z.getLeft() == null) {
                transplant(z, z.getRight());
            } else if (z.getRight() == null) {
                transplant(z, z.getLeft());
            } else {
                SimpleNode<K, V> y = (SimpleNode<K, V>) BinarySearchTreeUtils.minimum(z.getRight());
                if (y.getParent() != z) {
                    transplant(y, y.getRight());
                    y.setRight(z.getRight());
                    y.getRight().setParent(y);
                }
                transplant(z, y);
                y.setLeft(z.getLeft());
                y.getLeft().setParent(y);
            }
        }
    }

    @Override
    public boolean contains(K key) {
        return search(root, key, comparator).isPresent();
    }

    @Override
    public Optional<Set<V>> search(K key) {
        return search(root, key, comparator)
                .map(SimpleNode::getValues);
    }

    @Override
    public Optional<Set<V>> min() {
        if (root == null) {
            return Optional.empty();
        } else {
            return Optional.of(BinarySearchTreeUtils.minimum(root).getValues());
        }
    }

    @Override
    public Optional<Set<V>> max() {
        if (root == null) {
            return Optional.empty();
        } else {
            return Optional.ofNullable(BinarySearchTreeUtils.maximum(root).getValues());
        }
    }

    @Override
    public Iterator<Set<V>> iterator() {
        return new Iterator<Set<V>>() {

            private boolean initialized;

            private SimpleNode<K, V> next;

            @Override
            public boolean hasNext() {
                if (!initialized) {
                    if (root == null) {
                        return false;
                    } else {
                        next = (SimpleNode<K, V>) BinarySearchTreeUtils.minimum(root);
                        initialized = true;
                    }
                }
                return next != null;
            }

            @Override
            public Set<V> next() {
                SimpleNode<K, V> x = next;
                next = successor(x);
                return x.getValues();
            }
        };
    }

    private void transplant(SimpleNode<K, V> u, SimpleNode<K, V> v) {
        if (u.getParent() == null) {
            this.root = v; // `u` was root
        } else if (u.equals(u.getParent().getLeft())) {
            // u is left
            u.getParent().setLeft(v);
        } else {
            // u is right
            u.getParent().setRight(v);
        }
        if (v != null) {
            v.setParent(u.getParent());
        }
    }

    public static class Deserializer<K, V> {
        private final Function<String, K> keyDecoder;

        private final Function<String, V> valueDecoder;

        public Deserializer(Function<String, K> keyDecoder, Function<String, V> valueDecoder) {
            this.keyDecoder = keyDecoder;
            this.valueDecoder = valueDecoder;
        }

        public SimpleBinarySearchTree<K, V> decode(BufferedReader reader, Comparator<K> comparator) {
            SimpleBinarySearchTree<K, V> tree = new SimpleBinarySearchTree<>(comparator);
            reader.lines()
                    .forEach(line -> tree.insert(keyDecoder.apply(line), valueDecoder.apply(line)));
            return tree;
        }
    }

    public static class Serializer<K, V> {
        private static final Charset CHARSET = Charset.forName("UTF-8");

        private final Function<K, String> keySerializer;

        private final Function<V, String> valueSerializer;

        private final char delimiter = '\t';


        public Serializer(Function<K, String> keySerializer,
                           Function<V, String> valueSerializer) {
            this.keySerializer = keySerializer;
            this.valueSerializer = valueSerializer;
        }

        public void serialize(SimpleNode<K, V> root, OutputStream outputStream) throws IOException {
            if (root != null) {
                for (V v : root.getValues()) {
                    outputStream.write(keySerializer.apply(root.getKey()).getBytes(CHARSET));
                    outputStream.write(delimiter);
                    outputStream.write(valueSerializer.apply(v).getBytes(CHARSET));
                }

                outputStream.write(System.lineSeparator().getBytes(CHARSET));
                serialize(root.getLeft(), outputStream);
                serialize(root.getRight(), outputStream);
            }
        }

    }

}
