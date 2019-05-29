package xyz.ielis.algorithms.trees.binary_search_tree;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.CoreMatchers.*;

class BinarySearchTreeImplTest {


    @Test
    void minimumValue() {
        BinarySearchTree<Integer> bst = BinarySearchTreeImpl.make(Integer::compareTo);

        bst.add(5);
        bst.add(6);
        bst.add(4);
        bst.add(8);
        bst.add(3);
        bst.add(7);
        bst.add(2);

        assertThat(bst.minimum(), is(2));

        bst = BinarySearchTreeImpl.make(Integer::compareTo);
        assertThat(bst.minimum(), is(nullValue()));
    }

    @Test
    void maximumValue() {
        BinarySearchTree<Integer> bst = BinarySearchTreeImpl.make(Integer::compareTo);

        bst.add(5);
        bst.add(6);
        bst.add(4);
        bst.add(8);
        bst.add(3);
        bst.add(7);
        bst.add(2);

        assertThat(bst.maximum(), is(8));

        bst = BinarySearchTreeImpl.make(Integer::compareTo);
        assertThat(bst.maximum(), is(nullValue()));
    }



    @Test
    void contains() {
        BinarySearchTree<Integer> bst = BinarySearchTreeImpl.make(Integer::compareTo);

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

    @Test
    void iterator() {
        BinarySearchTree<Integer> bst = BinarySearchTreeImpl.make(Integer::compareTo);

        assertThat(bst.iterator().hasNext(), is(false));

        bst.add(5);
        bst.add(6);
        bst.add(4);

        ArrayList<Integer> all = new ArrayList<>();
        bst.iterator().forEachRemaining(all::add);
        assertThat(all, hasItems(4, 5, 6));
    }

    @Test
    void delete() {
        BinarySearchTree<Integer> bst = BinarySearchTreeImpl.make(Integer::compareTo);

        bst.add(5);
        bst.add(6);
        bst.add(4);
        bst.add(8);
        bst.add(3);
        bst.add(7);
        bst.add(2);

        List<Integer> content = new ArrayList<>();
        bst.iterator().forEachRemaining(content::add);
        assertThat(content, hasItems(2, 3, 4, 5, 6, 7, 8));

        bst.remove(7);
        content.clear();
        bst.iterator().forEachRemaining(content::add);
        assertThat(content, hasItems(2, 3, 4, 5, 6, 8));
        assertThat(content, not(hasItem(7)));

        bst.remove(4);
        content.clear();
        bst.iterator().forEachRemaining(content::add);
        assertThat(content, hasItems(2, 3, 5, 6, 8));
        assertThat(content, not(hasItem(4)));

        bst.remove(5);
        bst.remove(6);
        bst.remove(2);
        bst.remove(3);
        bst.remove(8);
        content.clear();
        bst.iterator().forEachRemaining(content::add);
        assertThat(content.isEmpty(), is(true));

    }
}