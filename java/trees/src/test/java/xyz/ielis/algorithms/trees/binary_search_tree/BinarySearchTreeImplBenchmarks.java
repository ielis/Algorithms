package xyz.ielis.algorithms.trees.binary_search_tree;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * This test class benchmarks performance of the <code>contains</code> method of {@link ArrayList}, {@link java.util.LinkedList}
 */
@Disabled // benchmarks are disabled by default
class BinarySearchTreeImplBenchmarks {

    private static final int SEED = 42;

    DoubleStream randomDoubles(int seed) {
        return new Random(seed).doubles();
    }


    @Test
    void searchInBst() {
        List<Integer> nItems = Stream.of(Math.pow(10, 3), Math.pow(10, 4), Math.pow(10, 5), Math.pow(10, 6), Math.pow(10, 7))
                .map(Double::intValue)
                .collect(Collectors.toList());

        int trials = 20;

        for (Integer count : nItems) {
            List<Integer> nanos = new ArrayList<>();
            for (int i = 0; i < trials; i++) {
                BinarySearchTree<Double> tree = BinarySearchTreeImpl.make(Double::compareTo);
                // populate binary search tree
                randomDoubles(SEED)
                        .limit(count)
                        .forEach(tree::add);
                // add query which we will search for
                var query = new Random().nextDouble();
                tree.add(query);
                var begin = Instant.now();
                assertTrue(tree.contains(query));
                var end = Instant.now();
                nanos.add(Duration.between(begin, end).getNano());
            }
            double average = nanos.stream().mapToInt(Integer::intValue).average().orElse(Double.NaN);
            System.out.println(String.format("The average search time in BST with %s items took %.5f us", count, average / 1_000));
        }
    }

    @Test
    void searchInArrayList() {
        List<Integer> nItems = Stream.of(Math.pow(10, 3), Math.pow(10, 4), Math.pow(10, 5), Math.pow(10, 6), Math.pow(10, 7))
                .map(Double::intValue)
                .collect(Collectors.toList());
        int trials = 20;

        for (Integer count : nItems) {
            List<Integer> nanos = new ArrayList<>();
            for (int i = 0; i < trials; i++) {
                List<Double> list = new ArrayList<>();
                // populate the collection
                randomDoubles(42)
                        .limit(count)
                        .forEach(list::add);
                // add query which we will search for
                var query = new Random().nextDouble();
                list.add(query);
                var begin = Instant.now();
                assertTrue(list.contains(query));
                var end = Instant.now();
                nanos.add(Duration.between(begin, end).getNano());
            }

            double average = nanos.stream().mapToInt(Integer::intValue).average().orElse(Double.NaN);
            System.out.println(String.format("The average search time in ArrayList with %s items took %.2f us", count, average / 1_000));
        }
    }

    @Test
    void searchInLinkedList() {
        List<Integer> nItems = Stream.of(Math.pow(10, 3), Math.pow(10, 4), Math.pow(10, 5), Math.pow(10, 6), Math.pow(10, 7))
                .map(Double::intValue)
                .collect(Collectors.toList());
        int trials = 20;

        for (Integer count : nItems) {
            List<Integer> nanos = new ArrayList<>();
            for (int i = 0; i < trials; i++) {
                List<Double> list = new LinkedList<>();
                // populate the collection
                randomDoubles(42)
                        .limit(count)
                        .forEach(list::add);
                // add query which we will search for
                var query = new Random().nextDouble();
                list.add(query);
                var begin = Instant.now();
                assertTrue(list.contains(query));
                var end = Instant.now();
                nanos.add(Duration.between(begin, end).getNano());
            }

            double average = nanos.stream().mapToInt(Integer::intValue).average().orElse(Double.NaN);
            System.out.println(String.format("The average search time in LinkedList with %s items took %.2f us", count, average / 1_000));
        }
    }

    @Test
    void searchInHashSet() {
        List<Integer> nItems = Stream.of(Math.pow(10, 3), Math.pow(10, 4), Math.pow(10, 5), Math.pow(10, 6), Math.pow(10, 7))
                .map(Double::intValue)
                .collect(Collectors.toList());
        int trials = 20;

        for (Integer count : nItems) {
            List<Integer> nanos = new ArrayList<>();
            for (int i = 0; i < trials; i++) {
                Set<Double> set = new HashSet<>();
                // populate the collection
                randomDoubles(42)
                        .limit(count)
                        .forEach(set::add);
                // add query which we will search for
                var query = new Random().nextDouble();
                set.add(query);
                var begin = Instant.now();
                assertTrue(set.contains(query));
                var end = Instant.now();
                nanos.add(Duration.between(begin, end).getNano());
            }

            double average = nanos.stream().mapToInt(Integer::intValue).average().orElse(Double.NaN);
            System.out.println(String.format("The average search time in HashSet with %s items took %.2f us", count, average / 1_000));
        }
    }

}