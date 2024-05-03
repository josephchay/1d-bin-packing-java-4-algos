import algorithms.ga.GeneticAlgorithm;
import algorithms.ts.TabuSearch;
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
            long startTime;
            long endTime;
            List<Integer> weights = new ArrayList<>();

            for (int i = 0; i < test.getItems().size(); i++) {
                weights.add(test.getItems().get(i).getWeight());
//                System.out.println(test.getItems().get(i).getWeight());
            }

            System.out.println("========== Test problem: " + test.getName() + " with capacity: " + test.getCapacity() + " and items: " + test.getItems().size() + " items ==========");

            startTime = System.nanoTime();  // Start timing

//            ga.setup(500, 50, test.getCapacity(),2, 0.8, 0.5, 0.5, "FF", "TS", false, weights);
//            ga.solve();

            TabuSearch ts = new TabuSearch(test.getCapacity(), weights, 15, 2000, 500);
            ts.run();

            endTime = System.nanoTime();  // End timing

            double durationInSeconds = (endTime - startTime) / 1_000_000_000.0; // Convert nanoseconds to seconds
            System.out.println("Execution Time: " + String.format("%.6f seconds", durationInSeconds) + "\n");
        }
    }
}
