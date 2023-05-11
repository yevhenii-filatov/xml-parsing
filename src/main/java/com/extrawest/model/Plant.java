package com.extrawest.model;

import java.util.StringJoiner;

/**
 * @author Yevhenii Filatov
 * @since 5/11/23
 */

public class Plant {
    private String name;
    private Soil soil;
    private String origin;
    private VisualParameters visualParameters;
    private GrowingTips growingTips;
    private MultiplyingType multiplyingType;

    public Plant() {
    }

    public Plant(String name, Soil soil, String origin, VisualParameters visualParameters, GrowingTips growingTips, MultiplyingType multiplyingType) {
        this.name = name;
        this.soil = soil;
        this.origin = origin;
        this.visualParameters = visualParameters;
        this.growingTips = growingTips;
        this.multiplyingType = multiplyingType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Soil getSoil() {
        return soil;
    }

    public void setSoil(Soil soil) {
        this.soil = soil;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public VisualParameters getVisualParameters() {
        return visualParameters;
    }

    public void setVisualParameters(VisualParameters visualParameters) {
        this.visualParameters = visualParameters;
    }

    public GrowingTips getGrowingTips() {
        return growingTips;
    }

    public void setGrowingTips(GrowingTips growingTips) {
        this.growingTips = growingTips;
    }

    public MultiplyingType getMultiplyingType() {
        return multiplyingType;
    }

    public void setMultiplyingType(MultiplyingType multiplyingType) {
        this.multiplyingType = multiplyingType;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Plant.class.getSimpleName() + "[", "]")
           .add("name='" + name + "'")
           .add("soil=" + soil)
           .add("origin='" + origin + "'")
           .add("visualParameters=" + visualParameters)
           .add("growingTips=" + growingTips)
           .add("multiplyingType=" + multiplyingType)
           .toString();
    }
}
