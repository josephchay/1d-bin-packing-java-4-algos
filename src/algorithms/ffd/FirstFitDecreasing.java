package algorithms.ffd;

import algorithms.ff.FirstFit;
import data.representation.Bin;
import data.representation.Item;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FirstFitDecreasing extends FirstFit {
    private static long startTime;

    private static long endTime;

    /**
     * Method to sort items in decreasing order and then pack them using the First Fit strategy
     *
     * @param items
     * @param currentBins
     * @param capacity
     * @return
     */
    public static List<Bin> pack(List<Item> items, List<Bin> currentBins, int capacity) {
        startTime = System.nanoTime();  // Start timing

        // Sort the items in decreasing order by weight
        List<Item> sortedItems = new ArrayList<>(items);
        Collections.sort(sortedItems, new Comparator<Item>() {
            @Override
            public int compare(Item i1, Item i2) {
                return Integer.compare(i2.getWeight(), i1.getWeight());
            }
        });

        List<Bin> bins = FirstFit.pack(sortedItems, currentBins, capacity);

        endTime = System.nanoTime();  // End timing

        return bins;
    }

    public static void printDetails(List<Bin> bins) {
        double durationInSeconds = (endTime - startTime) / 1_000_000_000.0; // Convert nanoseconds to seconds

        System.out.println("Number of bins used: " + bins.size());
        System.out.println("Execution Time: " + String.format("%.6f seconds", durationInSeconds) + "\n");

//        for (Bin bin : bins) {
//            System.out.println("Bin capacity: " + bin.getFreeSpace() + ", Items: " + bin.getItems());
//        }
    }
}
