package xyz.ielis.algorithms.trees.binary_search_tree;

public interface BinarySearchTree<E> {

    void add(E entry);

    void remove(E entry);

    boolean contains(E entry);

    E minimum();

    E maximum();
}
