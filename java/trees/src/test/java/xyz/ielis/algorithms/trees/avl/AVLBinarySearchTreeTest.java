package xyz.ielis.algorithms.trees.avl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import xyz.ielis.algorithms.trees.Node;
import xyz.ielis.algorithms.trees.Pilot;


import java.util.stream.IntStream;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static xyz.ielis.algorithms.trees.Pilots.*;

class AVLBinarySearchTreeTest {

    private AVLBinarySearchTree<Integer, Pilot> avl, empty;

    @BeforeEach
    void setUp() {
        avl = AVLBinarySearchTree.make(Integer::compare);
        avl.insert(1, chesleySullenberger());
        avl.insert(2, chuckYeager());
        avl.insert(3, jamesHunt());
        avl.insert(4, jensonButton());
        avl.insert(5, michaelSchumacher());
        avl.insert(6, nikkiLauda());
        avl.insert(7, rubensBarrichello());

        empty = AVLBinarySearchTree.make(Integer::compare);
    }

    @Test
    void inorderWalk() {
        avl.inorderTraverse();
    }

    @Test
    void height() {
        // the empty tree has height 0
        assertThat(empty.getHeight(), is(0));

        // a tree containing a single element has height 1
        empty.insert(1, chesleySullenberger());
        assertThat(empty.getHeight(), is(1));
        // the tree still has height 1 after inserting another value with the same key
        empty.insert(1, chuckYeager());
        assertThat(empty.getHeight(), is(1));

        // now the tree should have height 2
        empty.insert(2, jamesHunt());
        assertThat(empty.getHeight(), is(2));

        // the height is still 2
        empty.insert(3, jensonButton());
        assertThat(empty.getHeight(), is(2));

        // the height is now 3
        empty.insert(4, michaelSchumacher());
        assertThat(empty.getHeight(), is(3));

        assertThat(avl.getHeight(), is(3));
    }

    @Test
    void insertSequentialValues() {
        // insert 1023 elements, the tree should have 10 levels
        // the tree should have 11 levels after inserting the 1024th element
        AVLBinarySearchTree<Integer, String> integerAVLBST = AVLBinarySearchTree.make(Integer::compare);
        IntStream.range(1, 1024).forEach(i -> integerAVLBST.insert(i, Integer.toString(i)));

        assertThat(integerAVLBST.getHeight(), is(10));
        integerAVLBST.insert(1_024, "BLA");
        assertThat(integerAVLBST.getHeight(), is(11));
    }

    @Test
    void getRoot() {
        Node<Integer, Pilot> root = avl.getRoot();

        assertThat(root.getKey(), is(4));
        assertThat(root.getValues(), hasItem(jensonButton()));
        assertThat(root.getLeft(), is(not(nullValue())));
        assertThat(root.getRight(), is(not(nullValue())));
        assertThat(root, is(instanceOf(AVLNode.class)));
    }

    @Test
    void contains() {
        assertTrue(avl.contains(4));
        assertFalse(avl.contains(0) || avl.contains(8));
    }

    @Test
    void search() {
        assertThat(avl.search(7).isPresent(), is(true));
        assertThat(avl.search(7).get(), hasItem(rubensBarrichello()));

        assertThat(avl.search(0).isPresent(), is(false));
    }

    @Test
    void min() {
        assertThat(avl.min().isPresent(), is(true));
        assertThat(avl.min().get(), hasItem(chesleySullenberger()));
    }

    @Test
    void max() {
        assertThat(avl.max().isPresent(), is(true));
        assertThat(avl.max().get(), hasItem(rubensBarrichello()));
    }

    @Test
    void minOfEmptyTree() {
        assertThat(empty.min().isPresent(), is(false));
    }

    @Test
    void maxOfEmptyTree() {
        assertThat(empty.max().isPresent(), is(false));
    }

    @Test
    void remove() {
        avl.remove(4); // this is the root
        // TODO - test this thoroughly
        Node<Integer, Pilot> newRoot = avl.getRoot();

        assertThat(newRoot.getKey(), is(3));
        assertThat(newRoot.getValues(), hasItem(jamesHunt()));
        assertThat(newRoot.getLeft().getKey(), is(2));
        assertThat(newRoot.getRight().getKey(), is(5));
    }
}