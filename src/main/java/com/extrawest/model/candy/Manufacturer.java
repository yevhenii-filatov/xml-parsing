package com.extrawest.model.candy;

import java.util.Comparator;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * @author Yevhenii Filatov
 * @since 5/13/23
 */

public class Manufacturer implements Comparable<Manufacturer> {
    private String officialName;
    private String country;
    private String address;
    private String phone;

    public Manufacturer() {
    }

    public Manufacturer(String officialName, String country, String address, String phone) {
        this.officialName = officialName;
        this.country = country;
        this.address = address;
        this.phone = phone;
    }

    public String getOfficialName() {
        return officialName;
    }

    public void setOfficialName(String officialName) {
        this.officialName = officialName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Manufacturer that)) return false;
        return getOfficialName().equals(that.getOfficialName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getOfficialName());
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Manufacturer.class.getSimpleName() + "[", "]")
           .add("officialName='" + officialName + "'")
           .add("country='" + country + "'")
           .add("address='" + address + "'")
           .add("phone='" + phone + "'")
           .toString();
    }

    @Override
    public int compareTo(Manufacturer o) {
        return Comparator
           .comparing(Manufacturer::getOfficialName)
           .compare(this, o);
    }
}
