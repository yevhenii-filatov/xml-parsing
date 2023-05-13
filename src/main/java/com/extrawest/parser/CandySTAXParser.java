package com.extrawest.parser;

import com.extrawest.model.candy.*;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import static com.extrawest.model.candy.CandyConstants.*;

/**
 * @author Yevhenii Filatov
 * @since 5/13/23
 */

public class CandySTAXParser extends AbstractSTAXParser<List<Candy>> {
    private final LinkedList<Candy> result = new LinkedList<>();

    @Override
    public void processStartElementEvent(StartElement startElement, XMLEventReader reader) throws XMLStreamException {
        switch (startElement.getName().getLocalPart()) {
            case CANDY -> {
                Candy candy = new Candy();
                result.add(candy);
                candy.setAllowedForDiabetic(getAttribute(ALLOWED_FOR_DIABETIC, startElement));
                candy.setContainsAllergens(getAttribute(CONTAINS_ALLERGENS, startElement));
                candy.setContainsSugar(getAttribute(CONTAINS_SUGAR, startElement));
            }
            case PRICE -> result.getLast().setPrice(new Price());
            case MANUFACTURER -> result.getLast().setManufacturer(new Manufacturer());

            case NAME -> stepNextAndSet(reader, result.getLast()::setName);
            case WEIGHT -> stepNextAndSet(reader, result.getLast()::setWeight, Integer::parseInt);
            case CATEGORY -> stepNextAndSet(reader, result.getLast()::setCategory, Category::valueOf);
            case AMOUNT -> stepNextAndSet(reader, result.getLast().getPrice()::setAmount, BigDecimal::new);
            case CURRENCY -> stepNextAndSet(reader, result.getLast().getPrice()::setCurrency, Currency::valueOf);
            case OFFICIAL_NAME -> stepNextAndSet(reader, result.getLast().getManufacturer()::setOfficialName);
            case COUNTRY -> stepNextAndSet(reader, result.getLast().getManufacturer()::setCountry);
            case ADDRESS -> stepNextAndSet(reader, result.getLast().getManufacturer()::setAddress);
            case PHONE -> stepNextAndSet(reader, result.getLast().getManufacturer()::setPhone);
        }
    }

    @Override
    public LinkedList<Candy> getResult() {
        return result;
    }

    @Override
    protected void sortResult() {
        result.sort(Candy::compareTo);
    }
}
