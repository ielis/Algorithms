package xyz.ielis.algorithms.trees;


import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

/**
 * Note that multiple values are allowed to be stored under a single key. The values are stored in a set, hence dupliacte
 * values will be discarded. Please make sure your {@link V} classes implement {@link Object#equals(Object)} and
 * {@link Object#hashCode()} properly.
 *
 * @param <K>
 * @param <V>
 */
public interface BinarySearchTree<K, V> {

    Node<K, V> getRoot();

    void insert(K key, V value);

    void remove(K key);

    boolean contains(K key);

    Optional<Set<V>> search(K key);

    Optional<Set<V>> min();

    Optional<Set<V>> max();

    Iterator<Set<V>> iterator();

}
