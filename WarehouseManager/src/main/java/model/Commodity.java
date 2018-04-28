package model;

public class Commodity {
    private int id;
    private int volume;
    private int weight;
    private int price;

    public Commodity(int id, int volume, int weight, int price) {
        this.id = id;
        this.volume = volume;
        this.weight = weight;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public int getVolume() {
        return volume;
    }

    public int getWeight() {
        return weight;
    }

    public int getPrice() {
        return price;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
