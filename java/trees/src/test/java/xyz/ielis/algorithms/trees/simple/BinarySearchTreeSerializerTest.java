package xyz.ielis.algorithms.trees.simple;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import xyz.ielis.algorithms.trees.Pilot;

import java.io.ByteArrayOutputStream;
import java.util.Random;

import static xyz.ielis.algorithms.trees.Pilots.*;

class BinarySearchTreeSerializerTest {

    private static final Random RANDOM = new Random(150);

    private static SimpleBinarySearchTree<Integer, Pilot> BST;

    private SimpleBinarySearchTree.Serializer<Integer, Pilot> bsts;

    @BeforeAll
    static void setUpBefore() {
        BST = SimpleBinarySearchTree.make(Integer::compareTo);

        int bound = 100;

        BST.insert(RANDOM.nextInt(bound), chuckYeager());
        BST.insert(RANDOM.nextInt(bound), chesleySullenberger());
        BST.insert(RANDOM.nextInt(bound), jamesHunt());
        BST.insert(RANDOM.nextInt(bound), nikkiLauda());
        BST.insert(RANDOM.nextInt(bound), michaelSchumacher());
        BST.insert(RANDOM.nextInt(bound), jensonButton());
        BST.insert(RANDOM.nextInt(bound), rubensBarrichello());
    }

    @BeforeEach
    void setUp() {
        bsts = new SimpleBinarySearchTree.Serializer<>(i -> Integer.toString(i), Pilot::toString);
    }

    @Test
    void serialize() throws Exception{
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bsts.serialize(BST.getRoot(), baos);

        System.out.println(baos.toString());
    }
}