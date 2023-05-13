package com.extrawest.parser;

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
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author Yevhenii Filatov
 * @since 5/13/23
 */

public abstract class AbstractSTAXParser<T> {
    private final XMLInputFactory xmlInputFactory;

    protected AbstractSTAXParser() {
        xmlInputFactory = XMLInputFactory.newInstance();
        xmlInputFactory.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, StringUtils.EMPTY);
        xmlInputFactory.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, StringUtils.EMPTY);
    }

    public T parse(String xmlFilePath) throws XMLStreamException, FileNotFoundException {
        XMLEventReader reader = xmlInputFactory.createXMLEventReader(new FileInputStream(xmlFilePath));
        while (reader.hasNext()) {
            XMLEvent currentEvent = reader.nextEvent();
            if (currentEvent.isStartElement()) {
                processStartElementEvent(currentEvent.asStartElement(), reader);
            }
        }
        sortResult();
        return getResult();
    }

    protected abstract void processStartElementEvent(StartElement startElement, XMLEventReader reader) throws XMLStreamException;

    protected abstract void sortResult();

    protected abstract T getResult();

    protected Boolean getAttribute(String name, StartElement startElement) {
        Attribute attribute = startElement.getAttributeByName(new QName(name));
        return attribute != null ? Boolean.valueOf(attribute.getValue()) : null;
    }

    protected void stepNextAndSet(XMLEventReader reader, Consumer<String> setter) throws XMLStreamException {
        Characters nextEvent = reader.nextEvent().asCharacters();
        setter.accept(nextEvent.toString());
    }

    protected <O> void stepNextAndSet(XMLEventReader reader, Consumer<O> setter, Function<String, O> valueTransformer) throws XMLStreamException {
        Characters nextEvent = reader.nextEvent().asCharacters();
        setter.accept(valueTransformer.apply(nextEvent.getData()));
    }
}
