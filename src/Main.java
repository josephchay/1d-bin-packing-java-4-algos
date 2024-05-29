import algorithms.ffd.FirstFitDecreasing;
import algorithms.ga.GeneticAlgorithm;
import algorithms.sa.SimulatedAnnealing;
import algorithms.ts.TabuSearch;
import data.representation.Bin;
import data.representation.TestProblem;

import java.util.ArrayList;
import java.util.List;

import static filesystem.FilesystemReader.parseFile;

public class Main {
    public static void main(String[] args) {
        String filePath = "src/assets/source.txt";
        List<TestProblem> testProblems = parseFile(filePath);

        GeneticAlgorithm ga = GeneticAlgorithm.getInstance();

        for (TestProblem test : testProblems) {
            List<Integer> weights = new ArrayList<>();

            for (int i = 0; i < test.getItems().size(); i++) {
                weights.add(test.getItems().get(i).getWeight());
            }

            System.out.println("========== Test problem: " + test.getName() + " with capacity: " + test.getCapacity() + " and items: " + test.getItems().size() + " items ==========");

            System.out.println("----- First Fit Decreasing -----");
            List<Bin> bins = FirstFitDecreasing.pack(test.getItems(), new ArrayList<>(), test.getCapacity());
            FirstFitDecreasing.printDetails(bins);

            System.out.println("----- Simulated Annealing -----");
            SimulatedAnnealing.simulatedAnnealingAlgorithm(weights, test.getCapacity());

            System.out.println("----- Genetic Algorithm -----");
            ga.setup(500, 50, test.getCapacity(),2, 0.8, 0.5, 0.5, "FF", "TS", false, weights);
            ga.solve();

            System.out.println("----- Tabu Search -----");
            TabuSearch ts = new TabuSearch(test.getCapacity(), weights, 15, 2000, 500);
            ts.run();
        }
    }
}
