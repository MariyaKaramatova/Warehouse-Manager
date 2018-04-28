package model;

import java.util.HashMap;

public class Lot {
    private int id;
    private int volume;
    private int load_capacity;
    private HashMap<Integer, Integer> supply;

    public Lot(int id, int volume, int load_capacity, HashMap<Integer, Integer> supply) {
        this.id = id;
        this.volume = volume;
        this.load_capacity = load_capacity;
        this.supply = supply;
    }

    public int getId() {
        return id;
    }

    public int getVolume() {
        return volume;
    }

    public int getLoad_capacity() {
        return load_capacity;
    }

    public HashMap<Integer, Integer> getSupply() {
        return supply;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public void setLoad_capacity(int load_capacity) {
        this.load_capacity = load_capacity;
    }

    public void setSupply(HashMap<Integer, Integer> supply) {
        this.supply = supply;
    }
}
