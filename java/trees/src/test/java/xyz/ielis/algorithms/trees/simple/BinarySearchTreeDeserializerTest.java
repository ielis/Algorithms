package xyz.ielis.algorithms.trees.simple;

import org.junit.jupiter.api.BeforeEach;
import xyz.ielis.algorithms.trees.Pilot;

import java.nio.file.Path;
import java.nio.file.Paths;

class BinarySearchTreeDeserializerTest {

    private static final Path EXAMPLE_BST = Paths.get(BinarySearchTreeDeserializerTest.class.getResource("example.bst").toString());

    private SimpleBinarySearchTree.Deserializer<Integer, Pilot> bstd;

    @BeforeEach
    void setUp() {
        bstd = new SimpleBinarySearchTree.Deserializer<>(line -> {
            return 0;
        },
                line -> {
                    return null;
                });
    }
}