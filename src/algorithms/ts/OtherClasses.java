package algorithms.ts;

import data.representation.Bin;
import data.representation.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

interface Heuristic {
    void apply(Item item, List<Bin> bins, int binCapacity);
}

class FirstFit implements Heuristic {
    @Override
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
}

class NextFit implements Heuristic {
    private Bin lastBin = null;

    @Override
    public void apply(Item item, List<Bin> bins, int binCapacity) {
        if (lastBin == null || !lastBin.canAddItem(item)) {
            lastBin = new Bin(binCapacity);
            bins.add(lastBin);
        }
        lastBin.addItem(item);
    }
}

class BestFit implements Heuristic {
    @Override
    public void apply(Item item, List<Bin> bins, int binCapacity) {
        Bin bestBin = null;
        int minSpaceLeft = Integer.MAX_VALUE;

        for (Bin bin : bins) {
            if (bin.canAddItem(item) && (bin.getRemainingCapacity() - item.getWeight()) < minSpaceLeft) {
                bestBin = bin;
                minSpaceLeft = bin.getRemainingCapacity() - item.getWeight();
            }
        }

        if (bestBin == null) {
            bestBin = new Bin(binCapacity);
            bins.add(bestBin);
        }
        bestBin.addItem(item);
    }
}

class WorstFit implements Heuristic {
    @Override
    public void apply(Item item, List<Bin> bins, int binCapacity) {
        Bin worstBin = null;
        int maxSpaceLeft = -1;

        for (Bin bin : bins) {
            if (bin.canAddItem(item) && (bin.getRemainingCapacity() - item.getWeight()) > maxSpaceLeft) {
                worstBin = bin;
                maxSpaceLeft = bin.getRemainingCapacity() - item.getWeight();
            }
        }

        if (worstBin == null) {
            worstBin = new Bin(binCapacity);
            bins.add(worstBin);
        }
        worstBin.addItem(item);
    }
}

abstract class MoveOperator {
    public abstract String apply(String pattern, List<String> choices);
}

class Remove extends MoveOperator {
    @Override
    public String apply(String pattern, List<String> choices) {
        Random random = new Random();
        StringBuilder patternBuilder = new StringBuilder(pattern);
        // Ensure at least one character remains
        int numRemovals = random.nextInt(patternBuilder.length());
        for (int i = 0; i < numRemovals; i++) {
            int toRemove = random.nextInt(patternBuilder.length());
            patternBuilder.deleteCharAt(toRemove);
        }
        return patternBuilder.toString();
    }
}

class Add extends MoveOperator {
    @Override
    public String apply(String pattern, List<String> choices) {
        Random random = new Random();
        StringBuilder patternBuilder = new StringBuilder(pattern);
        int numInserts = random.nextInt(pattern.length() + 1);
        for (int i = 0; i < numInserts; i++) {
            int toInsert = random.nextInt(patternBuilder.length() + 1);
            patternBuilder.insert(toInsert, choices.get(random.nextInt(choices.size())));
        }
        return patternBuilder.toString();
    }
}

class Change extends MoveOperator {
    @Override
    public String apply(String pattern, List<String> choices) {
        Random random = new Random();
        char[] chars = pattern.toCharArray();
        int numChanges = random.nextInt(chars.length + 1);
        for (int i = 0; i < numChanges; i++) {
            int toChange = random.nextInt(chars.length);
            chars[toChange] = choices.get(random.nextInt(choices.size())).charAt(0);
        }
        return new String(chars);
    }
}

class Swap extends MoveOperator {
    @Override
    public String apply(String pattern, List<String> choices) {
        Random random = new Random();
        char[] chars = pattern.toCharArray();
        int numSwaps = random.nextInt(chars.length);
        for (int i = 0; i < numSwaps; i++) {
            int idx1 = random.nextInt(chars.length);
            int idx2 = random.nextInt(chars.length);
            char temp = chars[idx1];
            chars[idx1] = chars[idx2];
            chars[idx2] = temp;
        }
        return new String(chars);
    }
}
