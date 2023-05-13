package com.extrawest.parser;

import com.extrawest.model.flower.*;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import java.util.Comparator;

import static com.extrawest.model.flower.FlowerConstants.*;

/**
 * @author Yevhenii Filatov
 * @since 5/13/23
 */

public class FlowerSTAXParser extends AbstractSTAXParser<Flower> {
    private final Flower result = new Flower();

    @Override
    public void processStartElementEvent(StartElement startElement, XMLEventReader reader) throws XMLStreamException {
        switch (startElement.getName().getLocalPart()) {
            case PLANT -> result.getPlants().add(new Plant());
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

    @Override
    public Flower getResult() {
        return result;
    }

    @Override
    protected void sortResult() {
        result.getPlants().sort(Comparator.comparing(Plant::getName));
    }

    private Plant lastPlant() {
        int lastPlantIndex = result.getPlants().size() - 1;
        return result.getPlants().get(lastPlantIndex);
    }
}
