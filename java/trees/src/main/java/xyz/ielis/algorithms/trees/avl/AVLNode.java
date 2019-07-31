package xyz.ielis.algorithms.trees.avl;

import xyz.ielis.algorithms.trees.Node;

import java.util.*;

public class AVLNode<K, V> implements Node<K, V> {

    private final K key;
    private final Set<V> values;

    private int height;
    private AVLNode<K, V> left;
    private AVLNode<K, V> right;

    AVLNode(K key, V value) {
        this(key, Collections.singleton(value));
    }

    AVLNode(K key, Collection<V> values) {
        this.key = key;
        this.values = new HashSet<>(values);
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public AVLNode<K, V> getLeft() {
        return left;
    }

    public void setLeft(AVLNode<K, V> left) {
        this.left = left;
    }

    @Override
    public AVLNode<K, V> getRight() {
        return right;
    }

    public void setRight(AVLNode<K, V> right) {
        this.right = right;
    }

    @Override
    public K getKey() {
        return key;
    }

    @Override
    public Set<V> getValues() {
        return values;
    }

    public void addValue(V value) {
        this.values.add(value);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AVLNode<?, ?> avlNode = (AVLNode<?, ?>) o;
        return height == avlNode.height &&
                Objects.equals(key, avlNode.key) &&
                values.equals(avlNode.values);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, values, height);
    }

    @Override
    public String toString() {
        return "AVLNode{" +
                "key=" + key +
                ", values=" + values +
                '}';
    }
}
