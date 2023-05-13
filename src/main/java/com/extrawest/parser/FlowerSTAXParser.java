package com.extrawest.parser;

import com.extrawest.model.*;
import org.apache.commons.lang3.StringUtils;

import javax.xml.XMLConstants;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.function.Consumer;
import java.util.function.Function;

import static com.extrawest.model.FlowerConstants.*;

/**
 * @author Yevhenii Filatov
 * @since 5/13/23
 */

public class FlowerSTAXParser {
    private final XMLInputFactory xmlInputFactory;
    private final Flower flower;

    public FlowerSTAXParser() {
        xmlInputFactory = XMLInputFactory.newInstance();
        xmlInputFactory.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, StringUtils.EMPTY);
        xmlInputFactory.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, StringUtils.EMPTY);
        flower = new Flower();
    }

    public Flower getFlower(String xmlFilePath) throws IOException, XMLStreamException {
        XMLEventReader reader = xmlInputFactory.createXMLEventReader(new FileInputStream(xmlFilePath));
        while (reader.hasNext()) {
            XMLEvent currentEvent = reader.nextEvent();
            if (currentEvent.isStartElement()) {
                processStartElementEvent(currentEvent.asStartElement(), reader);
            }
        }
        return flower;
    }

    private void processStartElementEvent(StartElement startElement, XMLEventReader reader) throws XMLStreamException {
        switch (startElement.getName().getLocalPart()) {
            case PLANT -> flower.getPlants().add(new Plant());
            case VISUAL_PARAMETERS -> lastPlant().setVisualParameters(new VisualParameters());
            case GROWING_TIPS -> lastPlant().setGrowingTips(new GrowingTips());

            case NAME -> stepNextAndSet(reader, lastPlant()::setName);
            case SOIL -> stepNextAndSet(reader, lastPlant()::setSoil, Soil::valueOf);
            case ORIGIN -> stepNextAndSet(reader, lastPlant()::setOrigin);
            case MULTIPLYING_TYPE -> stepNextAndSet(reader, lastPlant()::setMultiplyingType, MultiplyingType::valueOf);
            case STEM_COLOR -> stepNextAndSet(reader, lastPlant().getVisualParameters()::setStemColor, Color::valueOf);
            case LEAF_COLOR -> stepNextAndSet(reader, lastPlant().getVisualParameters()::setLeafColor, Color::valueOf);
            case PLANT_SIZE -> stepNextAndSet(reader, lastPlant().getVisualParameters()::setSize, Integer::valueOf);

            case TEMPERATURE -> stepNextAndSet(reader, lastPlant().getGrowingTips()::setTemperature, Integer::valueOf);
            case LOVES_LIGHT -> stepNextAndSet(reader, lastPlant().getGrowingTips()::setLovesLight, Boolean::valueOf);
            case WATERING -> stepNextAndSet(reader, lastPlant().getGrowingTips()::setWatering, Integer::valueOf);
        }
    }

    private void stepNextAndSet(XMLEventReader reader, Consumer<String> setter) throws XMLStreamException {
        Characters nextEvent = reader.nextEvent().asCharacters();
        setter.accept(nextEvent.toString());
    }

    private <T> void stepNextAndSet(XMLEventReader reader, Consumer<T> setter, Function<String, T> valueTransformer) throws XMLStreamException {
        Characters nextEvent = reader.nextEvent().asCharacters();
        setter.accept(valueTransformer.apply(nextEvent.getData()));
    }

    private Plant lastPlant() {
        int lastPlantIndex = flower.getPlants().size() - 1;
        return flower.getPlants().get(lastPlantIndex);
    }
}
