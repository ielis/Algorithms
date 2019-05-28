package xyz.ielis.algorithms.trees.binary_search_tree;

import java.util.*;

public class BinarySearchArray<T> implements BinarySearchTree<T> {

    private Node<T> root;

    private final Comparator<T> comparator;

    private BinarySearchArray(Comparator<T> comparator) {
        this.comparator = comparator;
    }


    public static <T> BinarySearchArray<T> make(Comparator<T> comparator) {
        return new BinarySearchArray<>(comparator);
    }



    public List<T> getAll() {
        return inorderTreeWalk(root);
    }

    private List<T> inorderTreeWalk(Node<T> node) {
        List<T> h = new ArrayList<>();
        if (node != null) {
            h.addAll(inorderTreeWalk(node.getLeft()));
            h.add(node.getKey());
            h.addAll(inorderTreeWalk(node.getRight()));
        }
        return h;
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
        // TODO - implement
    }

    @Override
    public boolean contains(T entry) {
        Node<T> x = root;
        while (x != null && !entry.equals(x.getKey())) {
            if (comparator.compare(entry, x.getKey()) < 0) {
                x = x.getLeft();
            } else {
                x = x.getRight();
            }
        }
        return x != null;
    }

    @Override
    public T minimum() {
        Node<T> x = root;
        while (x.getLeft() != null) {
            x = x.getLeft();
        }
        return x.getKey();
    }

    @Override
    public T maximum() {
        Node<T> x = root;
        while (x.getRight() != null) {
            x = x.getRight();
        }
        return x.getKey();
    }


    private class Node<E> {

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

        private void setKey(E satellite) {
            this.key = satellite;
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
