package algorithms.ffd;

import algorithms.ff.FirstFit;
import data.representation.Bin;
import data.representation.Item;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FirstFitDecreasing extends FirstFit {
    /**
     * Method to sort items in decreasing order and then pack them using the First Fit strategy
     *
     * @param items
     * @param currentBins
     * @param capacity
     * @return
     */
    public static List<Bin> pack(List<Item> items, List<Bin> currentBins, int capacity) {
        // Sort the items in decreasing order by weight
        List<Item> sortedItems = new ArrayList<>(items);
        Collections.sort(sortedItems, new Comparator<Item>() {
            @Override
            public int compare(Item i1, Item i2) {
                return Integer.compare(i2.getWeight(), i1.getWeight());
            }
        });

        return FirstFit.pack(sortedItems, currentBins, capacity);
    }

    private static void printDetails(List<Bin> bins) {
        System.out.println("Number of bins used: " + bins.size());
        for (Bin bin : bins) {
            System.out.println("Bin capacity: " + bin.getFreeSpace() + ", Items: " + bin.getItems());
        }
    }
}
