package xyz.ielis.algorithms.trees.simple;

import xyz.ielis.algorithms.trees.Node;

import java.util.*;


public class SimpleNode<K, V> implements Node<K, V> {

    private final K key;

    private final Set<V> values;

    private SimpleNode<K, V> parent;

    private SimpleNode<K, V> left, right;

    SimpleNode(K key, V value) {
        this(key, Collections.singleton(value));
    }

    SimpleNode(K key, Collection<V> values) {
        this.key = key;
        this.values = new HashSet<>(values);
    }

    public SimpleNode<K, V> getParent() {
        return parent;
    }

    public void setParent(SimpleNode<K, V> parent) {
        this.parent = parent;
    }

    @Override
    public SimpleNode<K, V> getLeft() {
        return left;
    }

    public void setLeft(SimpleNode<K, V> left) {
        this.left = left;
    }

    @Override
    public SimpleNode<K, V> getRight() {
        return right;
    }

    public void setRight(SimpleNode<K, V> right) {
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
        SimpleNode<?, ?> that = (SimpleNode<?, ?>) o;
        return Objects.equals(key, that.key) &&
                Objects.equals(values, that.values);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, values);
    }

    @Override
    public String toString() {
        return "SimpleNode{" +
                "key=" + key +
                ", values=" + values +
                '}';
    }
}
