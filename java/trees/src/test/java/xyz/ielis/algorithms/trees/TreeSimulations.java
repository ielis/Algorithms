package xyz.ielis.algorithms.trees;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import xyz.ielis.algorithms.trees.avl.AVLBinarySearchTree;
import xyz.ielis.algorithms.trees.simple.SimpleBinarySearchTree;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.IntStream;

@Disabled // because it is time consuming
class TreeSimulations {


    private static final int RANDOM_SEED = 100;
    private static final int N_ELEMENTS_IN_TREE = 10_000;

    private Supplier<IntStream> sequential;
    private Supplier<IntStream> random;

    @BeforeEach
    void setUp() {
        sequential = () -> IntStream.range(0, N_ELEMENTS_IN_TREE);
        Random RND = new Random(RANDOM_SEED);
        this.random = () -> IntStream.generate(() -> RND.nextInt(N_ELEMENTS_IN_TREE * 10)).limit(N_ELEMENTS_IN_TREE);
    }

    @Test
    void ubst() {
        Supplier<BinarySearchTree<Integer, String>> bsts = () -> SimpleBinarySearchTree.make(Integer::compareTo);
        List<Integer> seqAvg = benchmark(bsts, sequential, 10);
        System.out.println("UBST seq : " + seqAvg);

        List<Integer> randAvg = benchmark(bsts, random, 10);
        System.out.println("UBST rand: " + randAvg);
    }

    @Test
    void avl() {
        Supplier<BinarySearchTree<Integer, String>> bsts = () -> AVLBinarySearchTree.make(Integer::compareTo);
        List<Integer> seqAvg = benchmark(bsts, sequential, 10);
        System.out.println("AVL seq : " + seqAvg);

        List<Integer> randAvg = benchmark(bsts, random, 10);
        System.out.println("AVL rand: " + randAvg);
    }

    private List<Integer> benchmark(Supplier<BinarySearchTree<Integer, String>> bstsupp, Supplier<IntStream> intstream, int nBenchmarks) {
        List<Integer> avgs = new ArrayList<>();
        for (int x = 0; x < nBenchmarks; x++) {
            BinarySearchTree<Integer, String> bst = bstsupp.get();

            intstream.get().forEach(i -> bst.insert(i, Integer.toString(i)));

            int avg = measureHeight(bst.getRoot(), 100);
            avgs.add(avg);
        }
        return avgs;
    }

    private <K, V> int measureHeight(Node<K, V> root, int nSim) {
        List<Integer> counts = new ArrayList<>();
        Random random = new Random(RANDOM_SEED);
        for (int i = 0; i < nSim; i++) {
            Node<K, V> current = root;
            int count = 0;
            while (current != null) {
                if (current.getLeft() == null && current.getRight() == null) {
                    current = random.nextBoolean() ? current.getLeft() : current.getRight();
                } else if (current.getLeft() == null) {
                    current = current.getRight();
                } else {
                    current = current.getLeft();
                }
                count++;

            }
            counts.add(count);
        }
        return counts.stream().reduce(Integer::max).orElse(-1);
    }
}
