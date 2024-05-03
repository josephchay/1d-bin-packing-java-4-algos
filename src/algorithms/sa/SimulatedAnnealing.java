package algorithms.sa;

import algorithms.ffd.FirstFitDecreasing;
import data.representation.Bin;
import data.representation.Item;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class SimulatedAnnealing {
    private static long startTime;
    private static long endTime;

    public static void randomiseWeights(List<Integer> list, Random random) {
        for (int i = list.size() - 1; i > 0; i--) {
            int indexOfItem = random.nextInt(i + 1);
            int temporary = list.get(indexOfItem);
            list.set(indexOfItem, list.get(i));
            list.set(i, temporary);
        }
    }

    private static List<Bin> generateBinsFromRandom(List<Integer> weights, int binCapacity) {
        List<Bin> bins = new ArrayList<>();
        randomiseWeights(weights, new Random());

        List<Item> items = new ArrayList<>();
        for (int i = 0; i < weights.size(); i++) {
            items.add(new Item(i, weights.get(i)));
        }

        for (Item item : items) {
            boolean itemPacked = false;
            for (Bin bin : bins) {
                bin.addItem(item);
                itemPacked = true;
                break;
            }

            if (!itemPacked) {
                Bin newBin = new Bin(binCapacity);
                newBin.addItem(item);
                bins.add(newBin);
            }
        }
        //System.out.println("Items: " + items + " Bins: " + bins.size());
        return bins;
    }

    private static int calculateCost(List<Bin> bins) {
        return bins.size();
    }

    public static int simulatedAnnealingAlgorithm(List<Integer> weights, int binCapacity) {
        List<Item> items = new ArrayList<>();
        for (int i = 0; i < weights.size(); i++) {
            items.add(new Item(i, weights.get(i)));
        }

        Random ran = new Random();
        List<Bin> currentSolution = new ArrayList<>();
        int bestCost = 0;
        double currentTemperature = 100;
        double targetTemperature = 0.1;
        double decreaseTemperature = 0.95; //where y lies in the interval [0.9,1.0). The closer it is to 0.9, the faster is the rate of cooling and the closer it is to 1.0, the slower is the cooling rate. In our simulation experiments, we have obtained very good solutions with a y value of about 0.95.

        startTime = System.nanoTime();

        currentSolution = FirstFitDecreasing.pack(items, new ArrayList<>(), binCapacity);
        bestCost = calculateCost(currentSolution);
        List<Bin> newAcceptedSolution = new ArrayList<>(currentSolution);

        while (currentTemperature > targetTemperature) {
            int initialCost = bestCost;
            List<Bin> neighborSolution;

            if (currentTemperature > 65) { //At higher temperatures, we generate neighbor solutions using the first neighbor solution generation method
                neighborSolution = generateNeighborSolution1(currentSolution);
            } else { //At lower temperatures, we generate neighbor solutions using the second neighbor solution generation method

                neighborSolution = generateNeighborSolution2(currentSolution);
            }

            int neighborCost = neighborSolution.size();
            int delta = neighborCost - bestCost;

            double rand = ran.nextDouble();

            if (delta < 0) {
                currentSolution.clear();
                currentSolution.addAll(neighborSolution);
                bestCost = neighborCost;
                //System.out.println("Initial Cost: " + initialCost);
                //System.out.println("Best cost: " + bestCost + " Current cost: " + currentCost + " Delta: " + delta + " Temperature: " + currentTemperature + " Rand: " + rand);
            } else {
                double probability = Math.exp((-delta) / decreaseTemperature);
                if (rand < probability) {
                    currentSolution.clear();
                    currentSolution.addAll(neighborSolution);
                    bestCost = neighborCost;
                    //System.out.println("Initial Cost: " + initialCost);
                    //System.out.println("Best cost: " + bestCost + " Current cost: " + currentCost + " Delta: " + delta + " Temperature: " + currentTemperature + " Rand: " + rand);
                } else {
                    continue; //Reject the solution and accept the current solution which is the initial solution
                }
            }
            currentTemperature *= decreaseTemperature;
        }

        endTime = System.nanoTime();
        double durationInSeconds = (endTime - startTime) / 1_000_000_000.0; // Convert nanoseconds to seconds

        System.out.println("Number of bins: " + bestCost);
        System.out.println("Execution Time: " + String.format("%.6f seconds", durationInSeconds) + "\n");

        return bestCost;
    }

    private static List<Bin> generateNeighborSolution1(List<Bin> currentSolution) {
        //Randomly select a bin by randomly choosing a bin index
        //Then randomly select an item from the randomly selected bin
        //Then randomly select another bin and put the selected item into the randomly selected bin
        //And return the new neighbor solution
        List<Bin> neighborSolution = new ArrayList<>(currentSolution);

        Random random = new Random();
        int currentBinIndex = random.nextInt(neighborSolution.size());
        Bin selectRandomBin = neighborSolution.get(currentBinIndex);

        if (selectRandomBin.getItems().isEmpty()) {
            return neighborSolution;
        } else {
            int itemIndex = random.nextInt(selectRandomBin.getItems().size());
            Item item = selectRandomBin.getItems().get(itemIndex);
            selectRandomBin.getItems().remove(itemIndex);

            int newBinIndex = random.nextInt(neighborSolution.size());
            while (newBinIndex == currentBinIndex) {
                newBinIndex = random.nextInt(neighborSolution.size());
            }
            Bin newBin = neighborSolution.get(newBinIndex);
            newBin.addItem(item);
        }

        return neighborSolution;
    }

    private static List<Bin> generateNeighborSolution2(List<Bin> currentSolution) {
        //Randomly select 2 different bins
        //Then randomly selecting an item from each bin
        //Then exchange positions of the selected items
        //And return the new neighbor solution
        List<Bin> neighborSolution = new ArrayList<>(currentSolution);
        Random random = new Random();

        int binIndex1 = random.nextInt(neighborSolution.size());
        int binIndex2 = random.nextInt(neighborSolution.size());
        while (binIndex1 == binIndex2) {
            binIndex2 = random.nextInt(neighborSolution.size());
        }

        Bin bin1 = neighborSolution.get(binIndex1);
        Bin bin2 = neighborSolution.get(binIndex2);

        if(bin1.getItems().isEmpty() || bin2.getItems().isEmpty()) {
            return neighborSolution;
        } else {
            int itemIndex1 = random.nextInt(bin1.getItems().size());
            int itemIndex2 = random.nextInt(bin2.getItems().size());

            Item item1 = bin1.getItems().get(itemIndex1);
            Item item2 = bin2.getItems().get(itemIndex2);

            bin1.getItems().remove(itemIndex1);
            bin2.getItems().remove(itemIndex2);

            bin1.addItem(item2);
            bin2.addItem(item1);
        }
        return neighborSolution;
    }
}
