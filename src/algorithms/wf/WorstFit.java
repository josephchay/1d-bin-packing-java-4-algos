package algorithms.wf;

import data.representation.Bin;
import data.representation.Item;

import java.util.ArrayList;
import java.util.List;

public class WorstFit {
    public static List<Bin> pack(List<Item> items, List<Bin> currentBins, int capacity) {
        List<Bin> bins = new ArrayList<>(currentBins);
        if (bins.isEmpty()) {
            bins.add(new Bin(capacity));
        }

        for (Item item : items) {
            if (item.getWeight() > capacity) continue;
            Bin worstBin = null;
            int maxSpace = -1;

            for (Bin bin : bins) {
                int space = bin.getFreeSpace() - item.getWeight();
                if (bin.fits(item) && space > maxSpace) {
                    worstBin = bin;
                    maxSpace = space;
                }
            }

            if (worstBin == null) {
                worstBin = new Bin(capacity);
                bins.add(worstBin);
            }
            worstBin.addItem(item);
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

