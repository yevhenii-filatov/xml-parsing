package com.extrawest.model;

import java.util.StringJoiner;

/**
 * @author Yevhenii Filatov
 * @since 5/11/23
 */

public class VisualParameters {
    private Color stemColor;
    private Color leafColor;
    private Integer size;

    public Color getStemColor() {
        return stemColor;
    }

    public void setStemColor(Color stemColor) {
        this.stemColor = stemColor;
    }

    public Color getLeafColor() {
        return leafColor;
    }

    public void setLeafColor(Color leafColor) {
        this.leafColor = leafColor;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", VisualParameters.class.getSimpleName() + "[", "]")
           .add("stemColor='" + stemColor + "'")
           .add("leafColor='" + leafColor + "'")
           .add("size=" + size)
           .toString();
    }
}
