package xyz.ielis.algorithms.trees.rb;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import xyz.ielis.algorithms.trees.Pilot;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static xyz.ielis.algorithms.trees.Pilots.*;
import static xyz.ielis.algorithms.trees.Pilots.rubensBarrichello;

@Disabled
class RedBlackTreeTest {

    RedBlackTree<Integer, Pilot> rbt;

    @BeforeEach
    void setUp() {
        rbt = new RedBlackTree<>(Integer::compareTo);
        rbt.insert(1, chesleySullenberger());
        rbt.insert(2, chuckYeager());
        rbt.insert(3, jamesHunt());
        rbt.insert(4, michaelSchumacher());
        rbt.insert(5, nikkiLauda());
        rbt.insert(6, rubensBarrichello());
    }

    @Test
    void search() {
//        assertThat(rbt.search(5).isPresent(), is(true));
//        assertThat(rbt.search(5).get(), hasItem(nikkiLauda()));
    }

    @Test
    void printout() {
//        rbt.iterator().forEachRemaining(System.err::println);
    }
}