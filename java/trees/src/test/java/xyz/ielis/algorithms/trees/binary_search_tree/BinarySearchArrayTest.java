package xyz.ielis.algorithms.trees.binary_search_tree;

import org.junit.jupiter.api.Test;

import java.util.TreeSet;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import static org.hamcrest.CoreMatchers.*;

class BinarySearchArrayTest {


    @Test
    void basic() {
        BinarySearchArray<Integer> bst = BinarySearchArray.make(Integer::compareTo);

        bst.add(5);
        bst.add(6);
        bst.add(4);
        bst.add(8);
        bst.add(3);
        bst.add(7);
        bst.add(2);
        System.out.println(bst.getAll());
    }

    @Test
    void minimumValue() {
        BinarySearchTree<Integer> bst = BinarySearchArray.make(Integer::compareTo);

        bst.add(5);
        bst.add(6);
        bst.add(4);
        bst.add(8);
        bst.add(3);
        bst.add(7);
        bst.add(2);

        assertThat(bst.minimum(), is(2));
    }

    @Test
    void contains() {
        BinarySearchTree<Integer> bst = BinarySearchArray.make(Integer::compareTo);

        bst.add(5);
        bst.add(6);
        bst.add(4);
        bst.add(8);
        bst.add(3);
        bst.add(7);
        bst.add(2);

        assertThat(bst.contains(4), is(true));
        assertThat(bst.contains(5), is(true));
        assertThat(bst.contains(2), is(true));
        assertThat(bst.contains(9), is(false));
    }
}