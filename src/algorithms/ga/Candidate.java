package algorithms.ga;

import data.representation.Bin;
import data.representation.Item;

import java.util.ArrayList;
import java.util.List;

public class Candidate {
    private List<Item> items; // The items in the current candidate solution
    private List<Bin> fitness; // The list of bins representing the candidate's fitness

    public Candidate(List<Item> items, List<Bin> fitness) {
        this.items = new ArrayList<>(items); // Deep copy to ensure encapsulation
        this.fitness = new ArrayList<>(fitness); // Deep copy to ensure encapsulation
    }

    public List<Item> getItems() {
        return new ArrayList<>(items); // Return a copy to avoid outside modification
    }

    public void setItems(List<Item> items) {
        this.items = new ArrayList<>(items); // Deep copy to ensure encapsulation
    }

    public List<Bin> getFitness() {
        return new ArrayList<>(fitness); // Return a copy to avoid outside modification
    }

    public void setFitness(List<Bin> fitness) {
        this.fitness = new ArrayList<>(fitness); // Deep copy to ensure encapsulation
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Candidate with items: ");
        for (Item item : items) {
            sb.append(item.getId()).append(" (").append(item.getWeight()).append("), ");
        }
        sb.append("\nUsing ").append(fitness.size()).append(" bins.");
        return sb.toString();
    }

    // Optional: Equals and hashCode methods for comparison and uniqueness checks
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Candidate candidate = (Candidate) obj;

        // Assuming Item class has properly overridden equals
        return items.equals(candidate.items);
    }

    @Override
    public int hashCode() {
        return items.hashCode();
    }
}

