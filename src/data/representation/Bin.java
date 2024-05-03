package data.representation;

import java.util.ArrayList;
import java.util.List;

public class Bin {
    private static int nextId = 0;
    private int id;
    private int capacity;
    private int freeSpace;
    private List<Item> items;
    private int usedSpace;
    private int currentLoad;

    public Bin(int capacity) {
        this.id = nextId++;
        this.capacity = capacity;
        this.freeSpace = capacity;
        this.items = new ArrayList<>();
        this.usedSpace = 0;
    }

    public void addItem(Item item) {
        if (canAddItem(item)) {
            this.items.add(item);
            this.freeSpace -= item.getWeight();
            this.usedSpace += item.getWeight();
            currentLoad += item.getWeight();
        }
    }

    public void removeItem(int itemIndex) {
        Item itemToRemove = this.items.get(itemIndex);
        this.items.remove(itemIndex);
        this.freeSpace += itemToRemove.getWeight();
        this.usedSpace -= itemToRemove.getWeight();
    }

    public boolean canAddItem(Item item) {
        return currentLoad + item.getWeight() <= capacity;
    }

    public int filledSpace() {
        return items.stream().mapToInt(Item::getWeight).sum();
    }

    public int getRemainingCapacity() {
        return capacity - currentLoad;
    }

    public double getFitness() {
        double fillRatio = (double) filledSpace() / capacity;
        return Math.pow(fillRatio, 2); // Squaring the fill ratio to emphasize higher fill rates
    }

    public boolean fits(Item item) {
        return this.freeSpace >= item.getWeight();
    }

    public int getFreeSpace() {
        return this.freeSpace;
    }

    public int getUsedSpace() {
        return this.usedSpace;
    }

    public int getCapacity() {
        return this.capacity;
    }

    public List<Item> getItems() {
        return this.items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        StringBuilder itemsString = new StringBuilder("[");
        for (Item it : items) {
            itemsString.append(it.toString()).append(" ");
        }
        itemsString.append("]");
        return "Bin n° " + this.id + " containing the " + this.items.size() +
                " following items : " + itemsString.toString() +
                " with " + this.freeSpace + " free space.";
    }

    @Override
    public Bin clone() {
        Bin newBin = new Bin(this.capacity);
        newBin.freeSpace = this.freeSpace;
        newBin.usedSpace = this.usedSpace;
        newBin.items = new ArrayList<>(this.items); // Shallow copy of items list
        return newBin;
    }
}
