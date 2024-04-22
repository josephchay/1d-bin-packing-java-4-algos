package algorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class FirstFit {
    public static int solve(List<Integer> weights, int capacity) {
        if (weights.isEmpty()) return 0;

        PriorityQueue<Integer> firstFit = new PriorityQueue<>(Collections.reverseOrder());
        List<Integer> storeElements = new ArrayList<>();
        int i = 0;

        while (i < weights.size()) {
            boolean placed = false;
            while (!firstFit.isEmpty() && !placed) {
                int sum = weights.get(i) + firstFit.peek();
                if (sum <= capacity) {
                    double oldTop = firstFit.poll();  // remove the top element
                    firstFit.add(sum);  // add new summed value
                    placed = true;  // mark the current item as placed
                } else {
                    storeElements.add(firstFit.poll());  // store temporarily
                }
            }

            // Add to a new bin if not placed
            if (!placed) {
                firstFit.add(weights.get(i));
                placed = true;
            }
            i++;
            // Return all temporary stored elements back to the priority queue
            firstFit.addAll(storeElements);
            storeElements.clear();
        }

        return firstFit.size();
    }
}
