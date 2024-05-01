
import algorithms.ga.GeneticAlgorithm;
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
//                System.out.println(test.getItems().get(i).getWeight());
            }

            System.out.println("========== Test problem: " + test.getName() + " with capacity: " + test.getCapacity() + " and items: " + test.getItems().size() + " items ==========");
            ga.setup(100, 100, test.getCapacity(), 5, 0.75, 0.8, 0.1, "FF", "TS", false, weights);
            ga.solve();

            System.out.println();
        }
    }
}
