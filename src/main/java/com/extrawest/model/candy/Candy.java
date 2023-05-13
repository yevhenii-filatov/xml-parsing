package com.extrawest.model.candy;

import java.util.Comparator;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * @author Yevhenii Filatov
 * @since 5/13/23
 */

public class Candy implements Comparable<Candy> {
    private String name;
    private Price price;
    private Integer weight;
    private Category category;
    private Boolean containsSugar;
    private Boolean allowedForDiabetic;
    private Boolean containsAllergens;
    private Manufacturer manufacturer;

    public Candy() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Boolean getContainsSugar() {
        return containsSugar;
    }

    public void setContainsSugar(Boolean containsSugar) {
        this.containsSugar = containsSugar;
    }

    public Boolean getAllowedForDiabetic() {
        return allowedForDiabetic;
    }

    public void setAllowedForDiabetic(Boolean allowedForDiabetic) {
        this.allowedForDiabetic = allowedForDiabetic;
    }

    public Boolean getContainsAllergens() {
        return containsAllergens;
    }

    public void setContainsAllergens(Boolean containsAllergens) {
        this.containsAllergens = containsAllergens;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Candy candy = (Candy) o;
        return getName().equals(candy.getName()) && getManufacturer().equals(candy.getManufacturer());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getManufacturer());
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Candy.class.getSimpleName() + "[", "]")
           .add("name='" + name + "'")
           .add("price='" + price)
           .add("weight=" + weight)
           .add("category=" + category)
           .add("containsSugar=" + containsSugar)
           .add("allowedForDiabetic=" + allowedForDiabetic)
           .add("containsAllergens=" + containsAllergens)
           .add("manufacturer=" + manufacturer)
           .toString();
    }

    @Override
    public int compareTo(Candy o) {
        return Comparator.comparing(Candy::getName)
           .thenComparing(Candy::getManufacturer)
           .compare(this, o);
    }
}
