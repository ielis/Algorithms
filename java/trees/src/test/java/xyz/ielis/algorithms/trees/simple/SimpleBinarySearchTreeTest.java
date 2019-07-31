package xyz.ielis.algorithms.trees.simple;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import xyz.ielis.algorithms.trees.Pilot;

import java.util.*;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static xyz.ielis.algorithms.trees.Pilots.*;

class SimpleBinarySearchTreeTest {

    private SimpleBinarySearchTree<Integer, Pilot> bst;

    @BeforeEach
    void setUp() {
        bst = SimpleBinarySearchTree.make(Integer::compareTo);
        bst.insert(2, nikkiLauda());
        bst.insert(4, chesleySullenberger());
        bst.insert(4, michaelSchumacher());
        bst.insert(3, jamesHunt());
        bst.insert(1, chuckYeager());
        bst.insert(1, rubensBarrichello());
    }

    @Test
    void testInsertion() {
        List<Set<Pilot>> pilots = new ArrayList<>();
        bst.iterator().forEachRemaining(pilots::add);

        assertThat(pilots.get(0), hasItems(chuckYeager(), rubensBarrichello()));
        assertThat(pilots.get(1), hasItem(nikkiLauda()));
        assertThat(pilots.get(2), hasItem(jamesHunt()));
        assertThat(pilots.get(3), hasItem(chesleySullenberger()));
    }

    @Test
    void testRemove() {
        List<Integer> positions = Arrays.asList(2, 4, 3, 1);

        for (Integer position : positions) {
            // remove all the elements
            assertThat(bst.search(position).isPresent(), is(true));
            bst.remove(position);
            assertThat(bst.search(position).isPresent(), is(false));
        }
    }

    @Test
    void testSearchExisting() {
        assertThat(bst.search(2).isPresent(), is(true));
        assertThat(bst.search(2).get(), hasItem(nikkiLauda()));
    }

    @Test
    void searchNonExisting() {
        assertThat(bst.search(5).isPresent(), is(false));
    }


    @Test
    void testContains() {
        assertThat(bst.contains(0), is(false));
        assertThat(bst.contains(1), is(true));
        assertThat(bst.contains(2), is(true));
        assertThat(bst.contains(3), is(true));
        assertThat(bst.contains(4), is(true));
        assertThat(bst.contains(5), is(false));
    }

    @Test
    void minimum() {
        assertThat(bst.min().isPresent(), is(true));
        assertThat(bst.min().get(), hasItems(chuckYeager(), rubensBarrichello()));
    }

    @Test
    void maximum() {
        assertThat(bst.max().isPresent(), is(true));
        assertThat(bst.max().get(), hasItems(chesleySullenberger(), michaelSchumacher()));
    }

    @Test
    void minimumOfEmptyBSTIsEmpty() {
        SimpleBinarySearchTree<Integer, Pilot> bs = SimpleBinarySearchTree.make(Integer::compareTo);
        assertThat(bs.min().isPresent(), is(false));
    }

    @Test
    void maximumOfEmptyBSTIsEmpty() {
        SimpleBinarySearchTree<Integer, Pilot> bs = SimpleBinarySearchTree.make(Integer::compareTo);
        assertThat(bs.max().isPresent(), is(false));
    }

    @Test
    void iterateThroughTheTree() {
        bst.iterator().forEachRemaining(System.err::println);
    }
}