package algorithms.ga;

import data.representation.Bin;
import data.representation.Item;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PopulationGenerator {
    public static List<Candidate> generate(List<Item> items, int capacity, int populationSize, String greedySolver) {
        List<Candidate> population = new ArrayList<>();
        Collections.shuffle(items);
        List<Bin> initialFitness = Fitness.calculate(items, capacity, greedySolver);
        Candidate initialCandidate = new Candidate(new ArrayList<>(items), initialFitness);
        population.add(initialCandidate);

        for (int i = 1; i < populationSize; i++) {
            Collections.shuffle(items);
            List<Bin> fitness = Fitness.calculate(new ArrayList<>(items), capacity, greedySolver);
            Candidate candidate = new Candidate(new ArrayList<>(items), fitness);
            if (!population.contains(candidate)) {
                population.add(candidate);
            }
        }
        return population;
    }
}

