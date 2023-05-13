package com.extrawest;

import com.extrawest.model.flower.Flower;
import com.extrawest.parser.FlowerDOMParser;
import com.extrawest.parser.FlowerSAXParser;
import com.extrawest.parser.FlowerSTAXParser;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class FlowerMain {
    private static final String XML_FILE_URI = "src/main/resources/greenhouse.xml";
    private static final String XSD_FILE_URI = "src/main/resources/greenhouse.xsd";
    private static final String SAX = "SAX";
    private static final String DOM = "DOM";
    private static final String STAX = "STAX";
    private static final String EXIT = "exit";

    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, XMLStreamException {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Choose an option for parsing: SAX/DOM/STAX or exit: ");
            String input = scanner.nextLine();
            switch (input) {
                case SAX -> runSAX();
                case DOM -> runDOM();
                case STAX -> runSTAX();
                case EXIT -> {
                    return;
                }
                default -> System.out.println("Wrong input, try again");
            }
        }
    }

    private static void runSTAX() throws XMLStreamException, IOException {
        FlowerSTAXParser flowerSTAXParser = new FlowerSTAXParser();
        Flower flower = flowerSTAXParser.parse(XML_FILE_URI);
        System.out.println(flower);
    }

    private static void runDOM() throws IOException {
        FlowerDOMParser flowerDOMParser = new FlowerDOMParser();
        Flower flower = flowerDOMParser.getFlower(XML_FILE_URI);
        System.out.println(flower);
    }

    private static void runSAX() throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        FlowerSAXParser flowerSAXParser = new FlowerSAXParser();
        saxParser.parse(XML_FILE_URI, flowerSAXParser);
        Flower flower = flowerSAXParser.getFlower();
        System.out.println(flower);
        validateSAX();
    }

    private static void validateSAX() throws SAXException, IOException {
        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Source schemaFile = new StreamSource(new File(XSD_FILE_URI));
        Schema schema = factory.newSchema(schemaFile);
        Validator validator = schema.newValidator();
        validator.validate(new StreamSource(new File(XML_FILE_URI)));
    }
}
