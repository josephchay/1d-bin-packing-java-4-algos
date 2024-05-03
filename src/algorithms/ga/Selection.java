package algorithms.ga;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Comparator;

public class Selection {
    public static Candidate tournamentSelection(List<Candidate> population, double probability, int k) {
        List<Candidate> candidates = new ArrayList<>();
        Random rand = new Random();

        // Select k random candidates for the tournament
        for (int i = 0; i < k; i++) {
            candidates.add(population.get(rand.nextInt(population.size())));
        }

        // Sort candidates by fitness (number of bins, assuming fewer bins is better)
        candidates.sort(Comparator.comparingInt(c -> c.getFitness().size()));

        // With a certain probability, choose the best candidate; otherwise, choose a random candidate from the tournament
        if (Math.random() < probability) {
            return candidates.get(0); // Return the candidate with the smallest fitness value (best candidate)
        } else {
            return candidates.get(rand.nextInt(candidates.size())); // Return a random candidate from the tournament
        }
    }

    public static Candidate rouletteWheelSelection(List<Candidate> population) {
        double totalFitness = population.stream().mapToDouble(c -> 1 / (double) c.getFitness().size()).sum();
        double value = Math.random() * totalFitness;
        double cumulativeFitness = 0.0;

        for (Candidate candidate : population) {
            cumulativeFitness += 1 / (double) candidate.getFitness().size();
            if (cumulativeFitness >= value) {
                return candidate;
            }
        }
        return population.get(population.size() - 1); // Fallback
    }

    public static Candidate rankSelection(List<Candidate> population) {
        int size = population.size();
        int totalRank = size * (size + 1) / 2; // Sum of series formula
        double value = Math.random() * totalRank;
        int cumulativeRank = 0;

        for (int i = 0; i < size; i++) {
            cumulativeRank += (size - i); // Rank is inverse of index position
            if (cumulativeRank >= value) {
                return population.get(i);
            }
        }
        return population.get(0); // Fallback
    }

    public static List<Candidate> SUS(List<Candidate> population, int n) {
        List<Candidate> selected = new ArrayList<>();
        double totalFitness = population.stream().mapToDouble(c -> 1 / (double) c.getFitness().size()).sum();
        double distance = totalFitness / n;
        double start = Math.random() * distance;
        double[] pointers = new double[n];

        for (int i = 0; i < n; i++) {
            pointers[i] = start + i * distance;
        }

        for (double pointer : pointers) {
            double cumulativeFitness = 0.0;
            for (Candidate candidate : population) {
                cumulativeFitness += 1 / (double) candidate.getFitness().size();
                if (cumulativeFitness >= pointer) {
                    selected.add(candidate);
                    break;
                }
            }
        }
        return selected;
    }
}
