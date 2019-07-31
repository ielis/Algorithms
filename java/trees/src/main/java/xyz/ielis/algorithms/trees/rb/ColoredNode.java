package xyz.ielis.algorithms.trees.rb;

import xyz.ielis.algorithms.trees.Node;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ColoredNode<K, V> implements Node<K, V> {

    private final K key;

    private final Set<V> values;

    private ColoredNode<K, V> parent;

    private ColoredNode<K, V> left, right;

    private boolean isBlack;

    public ColoredNode(K key, V value) {
        this(key, Collections.singleton(value), true);
    }

    public ColoredNode(K key, Collection<V> values) {
        this(key, values, true);
    }


    public ColoredNode(K key, Collection<V> values, boolean isBlack) {
        this.key = key;
        this.values = new HashSet<>(values);
        this.isBlack = isBlack;
    }

    public ColoredNode<K, V> getParent() {
        return parent;
    }

    public void setParent(ColoredNode<K, V> parent) {
        this.parent = parent;
    }

    public ColoredNode<K, V> getLeft() {
        return left;
    }

    public void setLeft(ColoredNode<K, V> left) {
        this.left = left;
    }

    public ColoredNode<K, V> getRight() {
        return right;
    }

    public void setRight(ColoredNode<K, V> right) {
        this.right = right;
    }

    public boolean isBlack() {
        return isBlack;
    }

    public void setBlack() {
        isBlack = true;
    }

    public boolean isRed() {
        return !isBlack;
    }

    public void setRed() {
        isBlack = false;
    }


    public void addValue(V value) {
        this.values.add(value);
    }

    public Set<V> getValues() {
        return values;
    }

    public K getKey() {
        return key;
    }
}
