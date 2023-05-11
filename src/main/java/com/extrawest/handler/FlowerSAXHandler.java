package com.extrawest.handler;

import com.extrawest.model.*;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.util.function.Consumer;
import java.util.function.Function;

import static com.extrawest.model.FlowerConstants.*;

/**
 * @author Yevhenii Filatov
 * @since 5/11/23
 */

public class FlowerSAXHandler extends DefaultHandler {
    private Flower flower;
    private StringBuilder elementValue;

    @Override
    public void characters(char[] ch, int start, int length) {
        if (elementValue == null) {
            elementValue = new StringBuilder();
        } else {
            elementValue.append(ch, start, length);
        }
    }

    @Override
    public void startDocument() {
        this.flower = new Flower();
    }

    @Override
    public void startElement(String uri, String lName, String qName, Attributes attr) {
        switch (qName) {
            case PLANT -> flower.getPlants().add(new Plant());
            case VISUAL_PARAMETERS -> lastPlant().setVisualParameters(new VisualParameters());
            case GROWING_TIPS -> lastPlant().setGrowingTips(new GrowingTips());
            case NAME, SOIL, ORIGIN, MULTIPLYING_TYPE, STEM_COLOR, LEAF_COLOR, PLANT_SIZE, TEMPERATURE, LOVES_LIGHT, WATERING ->
               elementValue = new StringBuilder();
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        Plant lastPlant = lastPlant();
        switch (qName) {
            case NAME -> set(lastPlant::setName);
            case SOIL -> set(lastPlant::setSoil, Soil::valueOf);
            case ORIGIN -> set(lastPlant::setOrigin);
            case MULTIPLYING_TYPE -> set(lastPlant::setMultiplyingType, MultiplyingType::valueOf);

            case STEM_COLOR -> set(lastPlant.getVisualParameters()::setStemColor, Color::valueOf);
            case LEAF_COLOR -> set(lastPlant.getVisualParameters()::setLeafColor, Color::valueOf);
            case PLANT_SIZE -> set(lastPlant.getVisualParameters()::setSize, Integer::valueOf);

            case TEMPERATURE -> set(lastPlant.getGrowingTips()::setTemperature, Integer::valueOf);
            case LOVES_LIGHT -> set(lastPlant.getGrowingTips()::setLovesLight, Boolean::valueOf);
            case WATERING -> set(lastPlant.getGrowingTips()::setWatering, Integer::valueOf);
        }
    }

    private void set(Consumer<String> setter) {
        setter.accept(elementValue.toString());
    }

    private <T> void set(Consumer<T> setter, Function<String, T> valueTransformer) {
        setter.accept(valueTransformer.apply(elementValue.toString()));
    }

    private Plant lastPlant() {
        int lastPlantIndex = flower.getPlants().size() - 1;
        return flower.getPlants().get(lastPlantIndex);
    }

    public Flower getFlower() {
        return flower;
    }
}
