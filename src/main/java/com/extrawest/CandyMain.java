package com.extrawest;

import com.extrawest.model.candy.Candy;
import com.extrawest.parser.CandySTAXParser;

import javax.xml.stream.XMLStreamException;
import java.io.FileNotFoundException;
import java.util.List;

/**
 * @author Yevhenii Filatov
 * @since 5/13/23
 */

public class CandyMain {
    private static final String XML_FILE_URI = "src/main/resources/candies.xml";

    public static void main(String[] args) throws XMLStreamException, FileNotFoundException {
        CandySTAXParser candySTAXParser = new CandySTAXParser();
        List<Candy> candies = candySTAXParser.parse(XML_FILE_URI);
        candies.forEach(System.out::println);
    }
}
