package data.representation;

public class Item {
    int weight;
    int count;

    public Item(int weight, int count) {
        this.weight = weight;
        this.count = count;
    }

    @Override
    public String toString() {
        return "Item{" +
                "weight=" + weight +
                ", count=" + count +
                '}';
    }

    public int getWeight() {
        return weight;
    }

    public int getCount() {
        return count;
    }
}
