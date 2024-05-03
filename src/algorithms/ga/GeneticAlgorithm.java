package algorithms.ga;

import data.representation.Bin;
import data.representation.Item;

import java.util.*;

public class GeneticAlgorithm {
    private static GeneticAlgorithm instance;
    private int populationSize;
    private int generations;
    private int capacity;
    private int k;
    private double tournamentSelectionProbability;
    private double crossoverProbability;
    private double mutationProbability;
    private String greedySolver;
    private String selectionMethod;
    private boolean allowDuplicateParents;
    private List<Item> items;

    private static long startTime;
    private static long endTime;

    private GeneticAlgorithm() {
        // Private constructor to prevent instantiation
    }

    public static GeneticAlgorithm getInstance() {
        if (instance == null) {
            instance = new GeneticAlgorithm();
        }
        return instance;
    }

    public void setup(int populationSize, int generations, int capacity, int k, double tournamentSelectionProbability, double crossoverProbability, double mutationProbability, String greedySolver, String selectionMethod, boolean allowDuplicateParents, List<Integer> weights) {
        this.populationSize = populationSize;
        this.generations = generations;
        this.capacity = capacity;
        this.k = k;
        this.tournamentSelectionProbability = tournamentSelectionProbability;
        this.crossoverProbability = crossoverProbability;
        this.mutationProbability = mutationProbability;
        this.greedySolver = greedySolver;
        this.selectionMethod = selectionMethod;
        this.allowDuplicateParents = allowDuplicateParents;
        this.items = new ArrayList<>();
        for (int i = 0; i < weights.size(); i++) {
            this.items.add(new Item(i, weights.get(i)));
        }
    }

    public void solve() {
        this.startTime = System.nanoTime();

        List<Candidate> population = PopulationGenerator.generate(items, capacity, populationSize, greedySolver);
        Candidate bestSolution = population.get(0);

        for (int i = 0; i < generations; i++) {
            List<Candidate> newGeneration = new ArrayList<>();
            Candidate bestChild = bestSolution;

            while (newGeneration.size() < populationSize) {
                Candidate parent1 = selectParent(population, selectionMethod, tournamentSelectionProbability, k);
                Candidate parent2 = selectParent(population, selectionMethod, tournamentSelectionProbability, k);

                if (!allowDuplicateParents) {
                    while (parent1 == parent2) {
                        parent2 = selectParent(population, selectionMethod, tournamentSelectionProbability, k);
                    }
                }

                double rand = Math.random();
                Candidate child = (rand < crossoverProbability) ? crossover(parent1, parent2) : parent1;

                rand = Math.random();
                if (rand <= mutationProbability) {
                    mutate(child, capacity, greedySolver);
                }

                newGeneration.add(child);
                if (child.getFitness().size() < bestChild.getFitness().size()) {
                    bestChild = child;
                }
            }

            population = new ArrayList<>(newGeneration);
            if (bestChild.getFitness().size() < bestSolution.getFitness().size()) {
                bestSolution = bestChild;
            }
        }

        this.endTime = System.nanoTime();

        printBestSolutionBins(bestSolution);
    }

    private void printBestSolutionBins(Candidate bestSolution) {
        double durationInSeconds = (endTime - startTime) / 1_000_000_000.0; // Convert nanoseconds to seconds

        System.out.println("Best solution uses " + bestSolution.getFitness().size() + " bins");
        System.out.println("Execution Time: " + String.format("%.6f seconds", durationInSeconds) + "\n");

//        int binNumber = 1;
//        for (Bin bin : bestSolution.getFitness()) {
//            System.out.print("Bin " + binNumber++ + " contains items: [");
//            List<String> itemSizes = new ArrayList<>();
//            for (Item item : bin.getItems()) {
//                itemSizes.add(String.valueOf(item.getWeight()));
//            }
//            System.out.print(String.join(", ", itemSizes));
//            System.out.println("]");
//            // print the remaining space left for eahc bin
//            System.out.println("Remaining space: " + bin.getRemainingCapacity());
//        }
//
//        System.out.println("Population size: " + populationSize);
//        System.out.println("Best length: " + bestSolution.getFitness().size());
//        System.out.println("Solution: " + formatSolution(bestSolution));
    }

    private String formatSolution(Candidate bestSolution) {
        List<String> formattedBins = new ArrayList<>();
        for (Bin bin : bestSolution.getFitness()) {
            List<String> itemSizes = new ArrayList<>();
            for (Item item : bin.getItems()) {
                itemSizes.add(String.valueOf(item.getWeight()));
            }
            formattedBins.add("[" + String.join(", ", itemSizes) + "]");
        }
        return "[" + String.join(", ", formattedBins) + "]";
    }

    private static Candidate selectParent(List<Candidate> population, String method, double probability, int k) {
        switch (method) {
            case "TS":
                return Selection.tournamentSelection(population, probability, k);
            case "RW":
                return Selection.rouletteWheelSelection(population);
            case "RS":
                return Selection.rankSelection(population);
            case "SUS":
                return Selection.SUS(population, 1).get(0);
            default:
                return null;
        }
    }

    private static Candidate crossover(Candidate parent1, Candidate parent2) {
        List<Item> items1 = parent1.getItems();
        List<Item> items2 = parent2.getItems();
        List<Item> childItems = new ArrayList<>();
        Set<Integer> taken = new HashSet<>();

        for (int i = 0; i < items1.size(); i++) {
            Item item = items1.get(i);
            if (!taken.contains(item.getId())) {
                childItems.add(item);
                taken.add(item.getId());
            }
            item = items2.get(i);
            if (!taken.contains(item.getId())) {
                childItems.add(item);
                taken.add(item.getId());
            }
        }

        return new Candidate(childItems, Fitness.calculate(childItems, 50, "FF"));
    }

    private static void mutate(Candidate candidate, int capacity, String greedySolver) {
        List<Item> items = candidate.getItems();
        Collections.shuffle(items);
        candidate.setFitness(Fitness.calculate(items, capacity, greedySolver));
    }
}
