package algorithms.bf;

import data.representation.Bin;
import data.representation.Item;

import java.util.ArrayList;
import java.util.List;

public class BestFit {
    public static List<Bin> pack(List<Item> items, List<Bin> currentBins, int capacity) {
        List<Bin> bins = new ArrayList<>(currentBins);
        if (bins.isEmpty()) {
            bins.add(new Bin(capacity)); // Create the initial bin if none exist.
        }

        // Loop through each item to find the best fit bin.
        for (Item item : items) {
            Bin bestBin = null;
            int minSpace = Integer.MAX_VALUE; // Use a large initial value to find the minimum.

            // Check each bin to find the best fit for the current item.
            for (Bin bin : bins) {
                if (bin.getFreeSpace() >= item.getWeight()) { // Ensure the bin can accommodate the item.
                    int space = bin.getFreeSpace() - item.getWeight();
                    if (space < minSpace) { // Find the bin that will leave the least free space after placing the item.
                        bestBin = bin;
                        minSpace = space;
                    }
                }
            }

            // If no suitable bin is found, create a new one.
            if (bestBin == null) {
                bestBin = new Bin(capacity);
                bins.add(bestBin);
            }

            // Add the item to the selected bin.
            bestBin.addItem(item);
        }

        // Optionally print bin usage details.
//        printDetails(bins);

        return bins;
    }


    private static void printDetails(List<Bin> bins) {
        System.out.println("Number of bins used: " + bins.size());
        for (Bin bin : bins) {
            System.out.println("Bin capacity: " + bin.getFreeSpace() + ", Items: " + bin.getItems());
        }
    }
}
