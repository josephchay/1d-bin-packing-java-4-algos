package algorithms.ff;

import data.representation.Bin;
import data.representation.Item;
import interfaces.Heuristics;

import java.util.ArrayList;
import java.util.List;

public class FirstFit implements Heuristics {
    public static List<Bin> pack(List<Item> items, List<Bin> currentBins, int capacity) {
        List<Bin> bins = new ArrayList<>(currentBins);

        // Iterate over each item to place it in the first bin where it fits
        for (Item item : items) {
            boolean placed = false;
            for (Bin bin : bins) {
                // Check if the bin can accommodate the item
                if (bin.getFreeSpace() >= item.getWeight()) {
                    bin.addItem(item);
                    placed = true;
                    break;
                }
            }

            // If no bin was found where the item fits, create a new bin
            if (!placed) {
                Bin newBin = new Bin(capacity);
                newBin.addItem(item);
                bins.add(newBin);
            }
        }

        // Optionally print bin usage details.
//        printDetails(bins);

        return bins;
    }

    public void apply(Item item, List<Bin> bins, int binCapacity) {
        for (Bin bin : bins) {
            if (bin.canAddItem(item)) {
                bin.addItem(item);
                return;
            }
        }
        Bin newBin = new Bin(binCapacity);
        newBin.addItem(item);
        bins.add(newBin);
    }

    private static void printDetails(List<Bin> bins) {
        System.out.println("Number of bins used: " + bins.size());
        for (Bin bin : bins) {
            System.out.println("Bin capacity: " + bin.getFreeSpace() + ", Items: " + bin.getItems());
        }
    }
}
