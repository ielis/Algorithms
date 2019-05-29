package xyz.ielis.algorithms.trees.binary_search_tree;

import java.util.*;

/**
 * Binary search tree implementation based on chapter 12 of Introduction to Algorithms by Cormen et al.
 *
 * @param <T> type parameter
 */
public class BinarySearchTreeImpl<T> implements BinarySearchTree<T> {

    private final Comparator<T> comparator;
    private Node<T> root;

    private BinarySearchTreeImpl(Comparator<T> comparator) {
        this.comparator = comparator;
    }


    public static <T> BinarySearchTreeImpl<T> make(Comparator<T> comparator) {
        return new BinarySearchTreeImpl<>(comparator);
    }

    private static <T> Node<T> treeSuccessor(Node<T> x) {
        if (x.getRight() != null) {
            return minimum(x.getRight());
        }
        Node<T> y = x.getParent();
        while (y != null && x.equals(y.getRight())) {
            x = y;
            y = y.getParent();
        }
        return y;
    }

    private static <T> Node<T> minimum(Node<T> x) {
        if (x == null) {
            return x;
        }
        while (x.getLeft() != null) {
            x = x.getLeft();
        }
        return x;
    }

    private static <T> Node<T> maximum(Node<T> x) {
        if (x == null) {
            return x;
        }
        while (x.getRight() != null) {
            x = x.getRight();
        }
        return x;
    }

    private static <T> void transplant(BinarySearchTreeImpl<T> tree, Node<T> u, Node<T> v) {
        if (u.getParent() == null) {
            tree.root = v;
        } else if (u.equals(u.getParent().getLeft())) {
            u.getParent().setLeft(v);
        } else {
            u.getParent().setRight(v);
        }
        if (v != null) {
            v.setParent(u.getParent());
        }
    }

    private static <T> void delete(BinarySearchTreeImpl<T> tree, Node<T> z) {
        if (z.getLeft() == null) {
            transplant(tree, z, z.getRight());
        } else if (z.getRight() == null) {
            transplant(tree, z, z.getLeft());
        } else {
            Node<T> y = minimum(z.getRight());
            if (!y.getParent().equals(z)) {
                transplant(tree, y, y.getRight());
                y.setRight(z.getRight());
                y.getRight().setParent(y);
            }
            transplant(tree, z, y);
            y.setLeft(z.getLeft());
            y.getLeft().setParent(y);
        }
    }

    private static <T> Node<T> search(Node<T> x, T k, Comparator<T> comparator) {
        if (x == null || comparator.compare(k, x.getKey()) == 0) {
            return x;
        }
        if (comparator.compare(k, x.getKey()) < 0) {
            return search(x.getLeft(), k, comparator);
        } else {
            return search(x.getRight(), k, comparator);
        }
    }

    @Override
    public void add(T entry) {
        Node<T> z = new Node<>(entry);
        Node<T> y = null;
        Node<T> x = root;

        while (x != null) {
            y = x;
            if (comparator.compare(z.getKey(), x.getKey()) < 0) {
                x = x.getLeft();
            } else {
                x = x.getRight();
            }
        }

        z.setParent(y);

        if (y == null) {
            // tree was empty
            root = new Node<>(entry);
        } else if (comparator.compare(z.getKey(), y.getKey()) < 0) {
            y.setLeft(z);
        } else {
            y.setRight(z);
        }
    }

    @Override
    public void remove(T entry) {
        delete(this, search(root, entry, comparator));
    }

    @Override
    public boolean contains(T entry) {
        return search(root, entry, comparator) != null;
    }

    @Override
    public T minimum() {
        if (root == null) {
            return null;
        }
        return minimum(root).getKey();
    }

    @Override
    public T maximum() {
        if (root == null) {
            return null;
        }
        return maximum(root).getKey();
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {

            private boolean initialized;

            private Node<T> next;


            @Override
            public boolean hasNext() {
                if (!initialized) {
                    next = minimum(root);
                    initialized = true;
                }
                return next != null;
            }

            @Override
            public T next() {
                Node<T> x = next;
                next = treeSuccessor(x);
                return x.getKey();
            }
        };
    }

    private static class Node<E> {

        private Node<E> parent;

        private Node<E> left;

        private Node<E> right;

        private E key;

        private Node() {
            // empty constructor
        }

        private Node(E key) {
            this.key = key;
        }

        private Node(Node<E> parent, Node<E> left, Node<E> right, E key) {
            this.parent = parent;
            this.left = left;
            this.right = right;
            this.key = key;
        }

        private Node<E> getParent() {
            return parent;
        }

        private void setParent(Node<E> parent) {
            this.parent = parent;
        }

        private Node<E> getLeft() {
            return left;
        }

        private void setLeft(Node<E> left) {
            this.left = left;
        }

        private Node<E> getRight() {
            return right;
        }

        private void setRight(Node<E> right) {
            this.right = right;
        }

        private E getKey() {
            return key;
        }

        private void setKey(E key) {
            this.key = key;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node<?> node = (Node<?>) o;
            return Objects.equals(parent, node.parent) &&
                    Objects.equals(left, node.left) &&
                    Objects.equals(right, node.right) &&
                    Objects.equals(key, node.key);
        }

        @Override
        public int hashCode() {
            return Objects.hash(parent, left, right, key);
        }

        @Override
        public String toString() {
            return "Node{" +
                    "parent=" + parent +
                    ", left=" + left +
                    ", right=" + right +
                    ", key=" + key +
                    '}';
        }

    }

}
