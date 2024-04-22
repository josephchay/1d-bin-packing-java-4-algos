package data.representation;

import java.util.ArrayList;
import java.util.List;

public class TestProblem {
    String name;
    int itemCount;
    int capacity;
    List<Item> items;

    public TestProblem(String testName, int itemCount, int capacity) {
        this.name = testName;
        this.itemCount = itemCount;
        this.capacity = capacity;
        this.items = new ArrayList<>();
    }

    public void addItem(Item item) {
        this.items.add(item);
    }

    @Override
    public String toString() {
        return "TestClass{" +
                "testName='" + name + '\'' +
                ", itemCount=" + itemCount +
                ", capacity=" + capacity +
                ", items=" + items +
                '}';
    }

    public String getName() {
        return name;
    }

    public List<Item> getItems() {
        return items;
    }

    public int getItemCount() {
        return itemCount;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
