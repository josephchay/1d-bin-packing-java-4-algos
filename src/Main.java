import data.representation.TestProblem;

import java.util.ArrayList;
import java.util.List;

import static algorithms.FirstFit.solve;
import static filesystem.FilesystemReader.parseFile;

public class Main {
    public static void main(String[] args) {
        String filePath = "src/assets/source.txt";
        List<TestProblem> testProblems = parseFile(filePath);

        for (TestProblem test : testProblems) {
            List<Integer> itemWeights = new ArrayList<>();

            for (int i = 0; i < test.getItems().size(); i++) {
                for (int j = 0; j < test.getItems().get(i).getCount(); j++) {
                    itemWeights.add(test.getItems().get(i).getWeight());
                    System.out.println(test.getItems().get(i).getWeight());
                }
            }

            int numberOfBins = solve(itemWeights, 10000);
            System.out.println("Number of bins required for Test Problem " + test.getName() + " is: " + numberOfBins);
        }
    }
}
