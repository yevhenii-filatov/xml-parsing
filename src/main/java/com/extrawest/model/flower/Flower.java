package com.extrawest.model.flower;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

/**
 * @author Yevhenii Filatov
 * @since 5/11/23
 */

public class Flower {
    private List<Plant> plants = new ArrayList<>();

    public Flower(List<Plant> plants) {
        this.plants = plants;
    }

    public Flower() {
    }

    public List<Plant> getPlants() {
        return plants;
    }

    public void setPlants(List<Plant> plants) {
        this.plants = plants;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Flower.class.getSimpleName() + "[", "]")
           .add("plants=" + plants)
           .toString();
    }
}
