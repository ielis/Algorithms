package xyz.ielis.algorithms.trees.avl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AVLNodeTest {

    private AVLNode<Integer, String> one, anotherOne, two;

    @BeforeEach
    void setUp() {
        one = new AVLNode<>(1, "ONE");
        anotherOne = new AVLNode<>(1, "ONE");
        two = new AVLNode<>(2, "TWO");
    }

    @Test
    void leftAndRightAreNullByDefault() {
        assertThat(one.getLeft(), is(nullValue()));
        assertThat(one.getRight(), is(nullValue()));
    }

    @Test
    void getkeyAndValue() {
        assertThat(one.getKey(), is(1));
        assertThat(one.getValues(), hasItem("ONE"));
    }

    @Test
    void testDefaultHeight() {
        assertThat(one.getHeight(), is(0));
    }

    @Test
    void setAndGetHeight() {
        one.setHeight(2001);
        assertThat(one.getHeight(), is(2001));
    }

    @Test
    void setLeftNode() {
        one.setLeft(two);
        assertThat(one.getLeft(), is(sameInstance(two)));
    }

    @Test
    void setRightNode() {
        one.setRight(two);
        assertThat(one.getRight(), is(sameInstance(two)));
    }

    @Test
    void testEquality() {
        assertThat(one, is(equalTo(anotherOne)));
        assertThat(one, is(not(equalTo(two))));
    }

    @Test
    void testIdentityEquality() {
        assertSame(one, one);
    }

    @Test
    void addValue() {
        one.addValue("ONEE");
        assertThat(one.getValues(), hasItems("ONE", "ONEE"));
    }
}