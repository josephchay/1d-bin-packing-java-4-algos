package algorithms.ts;

import data.representation.Bin;
import data.representation.Item;

import java.util.*;

public class TabuSearch {
    private int maxCombinationLength;
    private int maxIterations;
    private int maxNoChange;
    private int binCapacity;
    private List<Item> items;
    private double fitness;
    private List<Bin> bins;
    private Set<String> tabuList;
    private static final List<MoveOperator> movers = List.of(new Add(), new Change(), new Remove(), new Swap());

    public TabuSearch(int capacity, List<Integer> items, int maxCombinationLength, int maxIterations, int maxNoChange) {
        this.binCapacity = capacity;
        this.items = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            this.items.add(new Item(i, items.get(i)));
        }
        this.fitness = 0;
        this.bins = new ArrayList<>();
        this.bins.add(new Bin(capacity));
        this.tabuList = new HashSet<>();
    }

    public void run() {
        Random random = new Random();
        String combination = generateRandomCombination();
        bins = generateSolution(combination);
        fitness = calculateFitness(bins);
        tabuList.add(combination);

        int currentIteration = 0;
        int numNoChange = 0;

        while (numNoChange < maxNoChange && currentIteration < maxIterations) {
            String newCombination = applyMoveOperator(combination);
            while (newCombination.length() > maxCombinationLength) {
                newCombination = applyMoveOperator(newCombination);
            }
            if (!tabuList.contains(newCombination)) {
                tabuList.add(newCombination);
                List<Bin> solution = generateSolution(newCombination);
                double newFitness = calculateFitness(solution);
                if (newFitness > fitness) {
                    bins = solution;
                    fitness = newFitness;
                    numNoChange = 0;
                }
                currentIteration++;
            } else {
                numNoChange++;
            }
        }

        System.out.printf("Iterations: %d, No change count: %d, Final pattern: %s%n", currentIteration, numNoChange, combination);
        displayResults(bins);
    }

    private String generateRandomCombination() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        int minimumLength = 1;  // Ensure there is always at least one character
        int length = maxCombinationLength > 1 ? random.nextInt(maxCombinationLength - 1) + 1 : minimumLength;
        for (int i = 0; i < length; i++) {
            sb.append("fnwb".charAt(random.nextInt(4)));
        }
        return sb.toString();
    }


    public void displayResults(List<Bin> bins) {
        System.out.println("Number of bins: " + bins.size());
//        System.out.println("Bins content: ");
//        bins.forEach(bin -> {
//            System.out.print("[");
//            List<Item> items = bin.getItems(); // Assuming a getItems() method in Bin class.
//            for (int i = 0; i < items.size(); i++) {
//                System.out.print(items.get(i).getWeight());
//                if (i < items.size() - 1) {
//                    System.out.print(", ");
//                }
//            }
//            System.out.println("]");
//            // print out remaining space
//            System.out.println("Remaining space: " + bin.getFreeSpace());
//        });
    }

    private List<Bin> generateSolution(String pattern) {
        if (pattern.isEmpty()) {
            throw new IllegalStateException("Pattern must not be empty.");
        }
        List<Bin> solution = new ArrayList<>();
        solution.add(new Bin(binCapacity));
        Map<Character, Heuristic> heuristicMap = new HashMap<>();
        heuristicMap.put('f', new FirstFit());
        heuristicMap.put('n', new NextFit());
        heuristicMap.put('w', new WorstFit());
        heuristicMap.put('b', new BestFit());

        int patternLength = pattern.length();
        for (int i = 0; i < items.size(); i++) {
            char heuristicKey = pattern.charAt(i % patternLength);
            Heuristic heuristic = heuristicMap.get(heuristicKey);
            heuristic.apply(items.get(i), solution, binCapacity);
        }
        return solution;
    }

    private double calculateFitness(List<Bin> bins) {
        return bins.stream().mapToDouble(Bin::getFitness).average().orElse(0.0);
    }

    private String applyMoveOperator(String pattern) {
        return movers.get(new Random().nextInt(movers.size())).apply(pattern, List.of("f", "n", "w", "b"));
    }
}
