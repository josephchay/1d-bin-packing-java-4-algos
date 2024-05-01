package algorithms.ga;

import algorithms.bf.BestFit;
import algorithms.ff.FirstFit;
import algorithms.nf.NextFit;
import algorithms.wf.WorstFit;
import data.representation.Bin;
import data.representation.Item;

import java.util.ArrayList;
import java.util.List;

public class Fitness {
    public static List<Bin> calculate(List<Item> items, int capacity, String greedySolver) {
        switch (greedySolver) {
            case "FF":
                return FirstFit.pack(items, new ArrayList<>(), capacity);
            case "BF":
                return BestFit.pack(items, new ArrayList<>(), capacity);
            case "NF":
                return NextFit.pack(items, new ArrayList<>(), capacity);
            case "WF":
            default:
                return WorstFit.pack(items, new ArrayList<>(), capacity);
        }
    }
}

