package algorithms.nf;

import data.representation.Bin;
import data.representation.Item;
import interfaces.Heuristics;

import java.util.ArrayList;
import java.util.List;

public class NextFit implements Heuristics {
    private Bin lastBin = null;

    public static List<Bin> pack(List<Item> items, List<Bin> currentBins, int capacity) {
        List<Bin> bins = new ArrayList<>(currentBins);
        if (bins.isEmpty()) {
            Bin newBin = new Bin(capacity);
            bins.add(newBin);
        }
        Bin currentBin = bins.get(bins.size() - 1);

        for (Item item : items) {
            if (item.getWeight() > capacity) continue;
            if (!currentBin.fits(item)) {
                currentBin = new Bin(capacity);
                bins.add(currentBin);
            }
            currentBin.addItem(item);
        }

        // Optionally print bin usage details.
//        printDetails(bins);

        return bins;
    }

    public void apply(Item item, List<Bin> bins, int binCapacity) {
        if (lastBin == null || !lastBin.canAddItem(item)) {
            lastBin = new Bin(binCapacity);
            bins.add(lastBin);
        }
        lastBin.addItem(item);
    }

    private static void printDetails(List<Bin> bins) {
        System.out.println("Number of bins used: " + bins.size());
        for (Bin bin : bins) {
            System.out.println("Bin capacity: " + bin.getFreeSpace() + ", Items: " + bin.getItems());
        }
    }
}
