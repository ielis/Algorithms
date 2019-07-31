package xyz.ielis.algorithms.trees;

import java.util.Set;

public interface Node<K, V> {

    K getKey();

    Set<V> getValues();

    Node<K, V> getLeft();

    Node<K, V> getRight();


}
