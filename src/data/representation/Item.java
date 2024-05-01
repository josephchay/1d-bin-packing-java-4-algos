package data.representation;

public class Item {
    int id;
    int weight;

    public Item(int id, int weight) {
        this.id = id;
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                "weight=" + weight +
                '}';
    }

    public int getId() {
        return id;
    }

    public int getWeight() {
        return weight;
    }
}
