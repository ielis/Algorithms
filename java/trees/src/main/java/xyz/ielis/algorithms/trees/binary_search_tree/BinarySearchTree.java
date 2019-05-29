package xyz.ielis.algorithms.trees.binary_search_tree;

import java.util.Iterator;

public interface BinarySearchTree<E> {

    void add(E entry);

    void remove(E entry);

    boolean contains(E entry);

    E minimum();

    E maximum();

    Iterator<E> iterator();

}
