package xyz.ielis.algorithms.trees.binary_search_tree;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

class BinarySearchTreeImplBenchmarks {


    IntStream randomIntegers(int seed) {
        Random random = new Random(seed);

        return IntStream.generate(random::nextInt);
    }

    DoubleStream randomDoubles(int seed) {
        return new Random(seed).doubles();
    }


    @Disabled
    @Test
    void searchInBst() {
        List<Integer> nItems = Stream.of(Math.pow(10, 3), Math.pow(10, 4), Math.pow(10, 5), Math.pow(10, 6),
                Math.pow(10, 7), Math.pow(10, 8))
                .map(Double::intValue)
                .collect(Collectors.toList());

        int trials = 100;

        for (Integer count : nItems) {
            List<Integer> nanos = new ArrayList<>();
            for (int i = 0; i < trials; i++) {
                BinarySearchTree<Double> bst = BinarySearchTreeImpl.make(Double::compareTo);
                // populate binary search tree
                randomDoubles(42)
                        .limit(count)
                        .forEach(bst::add);
                // add query which we will search for
                var query = 0.5;
                bst.add(query);
                var begin = Instant.now();
                assertTrue(bst.contains(query));
                var end = Instant.now();
                nanos.add(Duration.between(begin, end).getNano());
            }
            double average = nanos.stream().mapToInt(Integer::intValue).average().orElse(Double.NaN);
            System.out.println(String.format("The average search time in BST with %s items took %.2f ms", count, average / 1000));
        }
    }

    @Disabled
    @Test
    void searchInList() {
        List<Integer> nItems = Stream.of(Math.pow(10, 3), Math.pow(10, 4), Math.pow(10, 5), Math.pow(10, 6),
                Math.pow(10, 7), Math.pow(10, 8))
                .map(Double::intValue)
                .collect(Collectors.toList());
        int trials = 100;

        for (Integer count : nItems) {
            List<Integer> nanos = new ArrayList<>();
            for (int i = 0; i < trials; i++) {
                List<Double> bst = new ArrayList<>();
                // populate binary search tree
                randomDoubles(42)
                        .limit(count)
                        .forEach(bst::add);
                // add query which we will search for
                var query = 0.5;
                bst.add(query);
                var begin = Instant.now();
                assertTrue(bst.contains(query));
                var end = Instant.now();
                nanos.add(Duration.between(begin, end).getNano());
            }

            double average = nanos.stream().mapToInt(Integer::intValue).average().orElse(Double.NaN);
            System.out.println(String.format("The average search time in List with %s items took %.2f ms", count, average / 1000));
        }

    }
}