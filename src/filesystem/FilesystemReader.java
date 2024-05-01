package filesystem;

import data.representation.Item;
import data.representation.TestProblem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FilesystemReader {
    public static List<TestProblem> parseFile(String fileName) {
        List<TestProblem> tests = new ArrayList<>();
        TestProblem currentTest = null;

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            boolean readCapacityNext = false;  // Flag to indicate the next line should read capacity

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.startsWith("'TEST")) {
                    if (currentTest != null) {
                        tests.add(currentTest);
                    }
                    String testName = line.substring(1, line.length() - 1); // Remove quotes
                    currentTest = new TestProblem(testName, 0, 0);
                    readCapacityNext = true;  // Next valid line should be item count
                } else if (!line.isEmpty() && currentTest != null) {
                    if (readCapacityNext) {
                        // Read item count from this line and expect the next line to be capacity
                        currentTest.setItemCount(Integer.parseInt(line));
                        readCapacityNext = false; // Next line will be capacity, not items yet
                    } else if (currentTest.getCapacity() == 0) {
                        // Set capacity and now ready to read items
                        currentTest.setCapacity(Integer.parseInt(line));
                    } else {
                        // Parse items
                        String[] parts = line.split("\\s+");
                        if (parts.length > 1) {
                            int weight = Integer.parseInt(parts[0]);
                            int count = Integer.parseInt(parts[1]);

                            for (int i = 0; i < count; i++) {
                                currentTest.addItem(new Item(i, weight));
                            }
                        } else {
                            System.out.println("Skipping malformed line: " + line);
                        }
                    }
                }
            }
            if (currentTest != null) {
                tests.add(currentTest);
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        return tests;
    }
}
