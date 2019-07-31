package xyz.ielis.algorithms.trees.rb;

import xyz.ielis.algorithms.trees.BinarySearchTree;

import java.util.*;

/**
 * A binary tree that satisfies the following <em>red-black properties</em>:
 * <ul>
 * <li>every node is either red or black</li>
 * <li>the root is black</li>
 * <li>every leaf (<code>NIL</code>) is black</li>
 * <li>if a node is red, then both its children are black</li>
 * <li>for each node, all simple paths from the node to descendant leaves contain the same number of black nodes</li>
 * </ul>
 *
 * @param <K> - type parameter of a key
 * @param <V> - type parameter of a value
 */
public class RedBlackTree<K, V> implements BinarySearchTree<K, V> {

    public final ColoredNode<K, V> NIL = new ColoredNode<>(null, Collections.emptySet());

    private final Comparator<K> comparator;

    private ColoredNode<K, V> root = NIL;

    public RedBlackTree(Comparator<K> comparator) {
        this.comparator = comparator;
    }

    private static <K, V> Optional<ColoredNode<K, V>> search(ColoredNode<K, V> node, K key, Comparator<K> comparator, ColoredNode<K, V> NIL) {
        if (node == NIL) {
            return Optional.empty();
        }

        if (node.getKey().equals(key)) {
            return Optional.of(node);
        }

        if (comparator.compare(key, node.getKey()) < 0) {
            return search(node.getLeft(), key, comparator, NIL);
        } else {
            return search(node.getRight(), key, comparator, NIL);
        }
    }

    private static <K, V> ColoredNode<K, V> minimum(ColoredNode<K, V> node, ColoredNode<K, V> NIL) {
        while (node.getLeft() != NIL) {
            node = node.getLeft();
        }
        return node;
    }

    private static <K, V> ColoredNode<K, V> maximum(ColoredNode<K, V> node, ColoredNode<K, V> NIL) {
        while (node.getRight() != NIL) {
            node = node.getRight();
        }
        return node;
    }

    private static <K, V> ColoredNode<K, V> successor(ColoredNode<K, V> node, ColoredNode<K, V> NIL) {
        if (node.getRight() != NIL) {
            return minimum(node.getRight(), NIL);
        }
        ColoredNode<K, V> y = node.getParent();
        while (y != NIL && node == y.getRight()) {
            node = y;
            y = y.getParent();
        }
        return y;
    }

    @Override
    public ColoredNode<K, V> getRoot() {
        return root;
    }

    @Override
    public void insert(K key, V value) {
        ColoredNode<K, V> trailing = NIL;
        ColoredNode<K, V> x = root;
        while (x != NIL) {
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
        ColoredNode<K, V> z = new ColoredNode<>(key, value);
        z.setParent(trailing);
        if (trailing == NIL) {
            // the tree was empty
            this.root = z;
        } else if (comparator.compare(z.getKey(), trailing.getKey()) < 0) {
            trailing.setLeft(z);
        } else {
            trailing.setRight(z);
        }
        z.setLeft(NIL);
        z.setRight(NIL);
        z.setRed();
        rbInsertFixup(z);
    }

    @Override
    public void remove(K key) {
        Optional<ColoredNode<K, V>> search = search(root, key, comparator, NIL);
        if (search.isPresent()) {
            ColoredNode<K, V> z = search.get();
            ColoredNode<K, V> y = z;
            boolean yIsBlack = y.isBlack();
            ColoredNode<K, V> x;
            if (z.getLeft() == NIL) {
                x = z.getRight();
                rbTransplant(z, z.getRight());
            } else if (z.getRight() == NIL) {
                x = z.getLeft();
            } else {
                y = minimum(z.getRight(), NIL);
                yIsBlack = y.isBlack();
                x = y.getRight();
                if (y.getParent() == z) {
                    x.setParent(y);
                } else {
                    rbTransplant(y, y.getRight());
                    y.setRight(z.getRight());
                    y.getRight().setParent(y);
                }
                rbTransplant(z, y);
                y.setLeft(z.getLeft());
                y.getLeft().setParent(y);
                if (z.isBlack()) {
                    y.setBlack();
                } else {
                    y.setRed();
                }
            }
            if (yIsBlack) {
                rbDeleteFixup(x);
            }
        }
    }

    private void rbDeleteFixup(ColoredNode<K, V> x) {
        while (x != root && x.isBlack()) {
            if (x == x.getParent().getRight()) {
                ColoredNode<K, V> w = x.getParent().getRight();
                if (w.isRed()) {
                    w.setBlack();
                    x.getParent().setRed();
                    leftRotate(x.getParent());
                    w = x.getParent().getRight();
                }
                if (w.getLeft().isBlack() && w.getRight().isBlack()) {
                    w.setRed();
                    x = x.getParent();
                } else if (w.getRight().isBlack()) {
                    w.getLeft().setBlack();
                    w.setRed();
                    rightRotate(w);
                    w = x.getParent().getRight();
                }
                if (x.getParent().isBlack()) {
                    w.setBlack();
                } else {
                    w.setRed();
                }
                x.getParent().setBlack();
                w.getRight().setBlack();
                leftRotate(x.getParent());
                x = root;
            } else {
                ColoredNode<K, V> w = x.getParent().getLeft();
                if (w.isRed()) {
                    w.setBlack();
                    x.getParent().setRed();
                    rightRotate(x.getParent());
                    w = x.getParent().getLeft();
                }
                if (w.getRight().isBlack() && w.getLeft().isBlack()) {
                    w.setRed();
                    x = x.getParent();
                } else if (w.getLeft().isBlack()) {
                    w.getRight().setBlack();
                    w.setRed();
                    leftRotate(w);
                    w = x.getParent().getLeft();
                }
                if (x.getParent().isBlack()) {
                    w.setBlack();
                } else {
                    w.setRed();
                }
                x.getParent().setBlack();
                w.getLeft().setBlack();
                rightRotate(x.getParent());
                x = root;
            }
            x.setBlack();
        }
    }

    @Override
    public boolean contains(K key) {
        return false;
    }

    @Override
    public Optional<Set<V>> search(K key) {
        return search(root, key, comparator, NIL)
                .map(ColoredNode::getValues);
    }

    @Override
    public Optional<Set<V>> min() {
        if (root == NIL) {
            return Optional.empty();
        } else {
            return Optional.of(minimum(root, NIL).getValues());
        }
    }

    @Override
    public Optional<Set<V>> max() {
        return Optional.empty();
    }

    @Override
    public Iterator<Set<V>> iterator() {
        return new Iterator<Set<V>>() {

            private boolean initialized;

            private ColoredNode<K, V> next;

            @Override
            public boolean hasNext() {
                if (!initialized) {
                    if (root == null) {
                        return false;
                    } else {
                        next = minimum(root, NIL);
                        initialized = true;
                    }
                }
                return next != null;
            }

            @Override
            public Set<V> next() {
                ColoredNode<K, V> x = next;
                next = successor(x, NIL);
                return x.getValues();
            }
        };
    }

    private void leftRotate(ColoredNode<K, V> x) {
        ColoredNode<K, V> y = x.getRight();
        x.setRight(y.getLeft());
        if (y.getLeft() != NIL) {
            y.getLeft().setParent(x);
        }

        y.setParent(x.getParent());

        if (x.getParent() == NIL) {
            root = y;
        } else if (x == x.getParent().getLeft()) {
            x.getParent().setLeft(y);
        } else {
            x.getParent().setRight(y);
        }
        y.setLeft(x);
        x.setParent(y);
    }

    private void rightRotate(ColoredNode<K, V> x) {
        ColoredNode<K, V> y = x.getLeft();
        x.setLeft(y.getRight());
        if (y.getRight() != NIL) {
            y.getRight().setParent(x);
        }

        y.setParent(x.getParent());

        if (x.getParent() == NIL) {
            root = y;
        } else if (x == x.getParent().getRight()) {
            x.getParent().setRight(y);
        } else {
            x.getParent().setLeft(y);
        }
        y.setRight(x);
        x.setParent(y);
    }

    private void rbInsertFixup(ColoredNode<K, V> z) {
        while (z.getParent().isRed()) {
            if (z.getParent() == z.getParent().getParent().getLeft()) {
                ColoredNode<K, V> y = z.getParent().getParent().getRight();
                if (y.isRed()) {
                    z.getParent().setBlack();
                    y.setBlack();
                    z.getParent().getParent().setRed();
                    z = z.getParent().getParent();
                } else if (z == z.getParent().getRight()) {
                    z = z.getParent();
                    leftRotate(z);
                }
                z.getParent().setBlack();
                z.getParent().getParent().setRed();
                rightRotate(z.getParent().getParent());
            } else {
                ColoredNode<K, V> y = z.getParent().getParent().getLeft();
                if (y.isRed()) {
                    z.getParent().setBlack();
                    y.setBlack();
                    z.getParent().getParent().setRed();
                    z = z.getParent().getParent();
                } else if (z == z.getParent().getLeft()) {
                    z = z.getParent();
                    rightRotate(z);
                }
                z.getParent().setBlack();
                z.getParent().getParent().setRed();
                leftRotate(z.getParent().getParent());
            }
        }
        root.setBlack();
    }

    private void rbTransplant(ColoredNode<K, V> u, ColoredNode<K, V> v) {
        if (u.getParent() == NIL) {
            root = v;
        } else if (u == u.getParent().getLeft()) {
            u.getParent().setLeft(v);
        } else {
            u.getParent().setRight(v);
        }
        v.setParent(u.getParent());
    }
}
