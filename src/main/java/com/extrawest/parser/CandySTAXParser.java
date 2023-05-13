package com.extrawest.parser;

import com.extrawest.model.candy.*;
import org.apache.commons.lang3.StringUtils;

import javax.xml.XMLConstants;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import static com.extrawest.model.candy.CandyConstants.*;

/**
 * @author Yevhenii Filatov
 * @since 5/13/23
 */

public class CandySTAXParser {
    private final XMLInputFactory xmlInputFactory;
    private final LinkedList<Candy> candies;

    public CandySTAXParser() {
        xmlInputFactory = XMLInputFactory.newInstance();
        xmlInputFactory.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, StringUtils.EMPTY);
        xmlInputFactory.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, StringUtils.EMPTY);
        candies = new LinkedList<>();
    }

    public List<Candy> getCandies(String xmlFilePath) throws XMLStreamException, FileNotFoundException {
        XMLEventReader reader = xmlInputFactory.createXMLEventReader(new FileInputStream(xmlFilePath));
        while (reader.hasNext()) {
            XMLEvent currentEvent = reader.nextEvent();
            if (currentEvent.isStartElement()) {
                processStartElementEvent(currentEvent.asStartElement(), reader);
            }
        }
        return candies;
    }

    private void processStartElementEvent(StartElement startElement, XMLEventReader reader) throws XMLStreamException {
        switch (startElement.getName().getLocalPart()) {
            case CANDY -> {
                Candy candy = new Candy();
                candies.add(candy);
                candy.setAllowedForDiabetic(getAttribute(ALLOWED_FOR_DIABETIC, startElement));
                candy.setContainsAllergens(getAttribute(CONTAINS_ALLERGENS, startElement));
                candy.setContainsSugar(getAttribute(CONTAINS_SUGAR, startElement));
            }
            case PRICE -> candies.getLast().setPrice(new Price());
            case MANUFACTURER -> candies.getLast().setManufacturer(new Manufacturer());

            case NAME -> stepNextAndSet(reader, candies.getLast()::setName);
            case WEIGHT -> stepNextAndSet(reader, candies.getLast()::setWeight, Integer::parseInt);
            case CATEGORY -> stepNextAndSet(reader, candies.getLast()::setCategory, Category::valueOf);
            case AMOUNT -> stepNextAndSet(reader, candies.getLast().getPrice()::setAmount, BigDecimal::new);
            case CURRENCY -> stepNextAndSet(reader, candies.getLast().getPrice()::setCurrency, Currency::valueOf);
            case OFFICIAL_NAME -> stepNextAndSet(reader, candies.getLast().getManufacturer()::setOfficialName);
            case COUNTRY -> stepNextAndSet(reader, candies.getLast().getManufacturer()::setCountry);
            case ADDRESS -> stepNextAndSet(reader, candies.getLast().getManufacturer()::setAddress);
            case PHONE -> stepNextAndSet(reader, candies.getLast().getManufacturer()::setPhone);
        }
    }

    private Boolean getAttribute(String name, StartElement startElement) {
        Attribute attribute = startElement.getAttributeByName(new QName(name));
        return attribute != null ? Boolean.valueOf(attribute.getValue()) : null;
    }

    private void stepNextAndSet(XMLEventReader reader, Consumer<String> setter) throws XMLStreamException {
        Characters nextEvent = reader.nextEvent().asCharacters();
        setter.accept(nextEvent.toString());
    }

    private <T> void stepNextAndSet(XMLEventReader reader, Consumer<T> setter, Function<String, T> valueTransformer) throws XMLStreamException {
        Characters nextEvent = reader.nextEvent().asCharacters();
        setter.accept(valueTransformer.apply(nextEvent.getData()));
    }
}
