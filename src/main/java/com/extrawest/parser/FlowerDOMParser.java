package com.extrawest.parser;

import com.extrawest.model.flower.*;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import static com.extrawest.model.flower.FlowerConstants.*;

/**
 * @author Yevhenii Filatov
 * @since 5/11/23
 */

public class FlowerDOMParser {
    public Flower getFlower(String xmlFilePath) throws IOException {
        List<Plant> plants = Jsoup.parse(new File(xmlFilePath))
           .select(PLANT)
           .stream()
           .map(this::plant)
           .filter(Objects::nonNull)
           .sorted(Comparator.comparing(Plant::getName))
           .toList();
        return new Flower(plants);
    }

    private Plant plant(Element plantElement) {
        return parse(plantElement, e -> {
            String name = text(plantElement.selectFirst(NAME));
            String origin = text(plantElement.selectFirst(ORIGIN));
            Soil soil = Soil.valueOf(text(plantElement.selectFirst(SOIL)));
            MultiplyingType multiplyingType = MultiplyingType.valueOf(text(plantElement.selectFirst(MULTIPLYING_TYPE)));
            GrowingTips growingTips = growingTips(plantElement.selectFirst(GROWING_TIPS));
            VisualParameters visualParameters = visualParameters(plantElement.selectFirst(VISUAL_PARAMETERS));
            return new Plant(name, soil, origin, visualParameters, growingTips, multiplyingType);
        });
    }

    private VisualParameters visualParameters(Element visualParametersElement) {
        return parse(visualParametersElement, e -> {
            Color stemColor = getTextAndTransform(visualParametersElement, STEM_COLOR, Color::valueOf);
            Color leafColor = getTextAndTransform(visualParametersElement, LEAF_COLOR, Color::valueOf);
            Integer size = getTextAndTransform(visualParametersElement, PLANT_SIZE, Integer::parseInt);
            return new VisualParameters(stemColor, leafColor, size);
        });
    }

    private GrowingTips growingTips(Element growingTipsElement) {
        return parse(growingTipsElement, e -> {
            Integer temperature = getTextAndTransform(growingTipsElement, TEMPERATURE, Integer::parseInt);
            Boolean lovesLight = getTextAndTransform(growingTipsElement, LOVES_LIGHT, Boolean::parseBoolean);
            Integer watering = getTextAndTransform(growingTipsElement, WATERING, Integer::parseInt);
            return new GrowingTips(temperature, lovesLight, watering);
        });
    }

    private <T> T parse(Element element, Function<Element, T> parser) {
        return Optional.ofNullable(element)
           .map(parser)
           .orElse(null);
    }

    private <T> T getTextAndTransform(Element root, String elementSelector, Function<String, T> textTransformer) {
        if (root == null || StringUtils.isBlank(elementSelector)) {
            return null;
        }
        return textTransformer.apply(text(root.selectFirst(elementSelector)));
    }

    public static String text(Node node) {
        String text = null;
        if (node != null) {
            if (node instanceof TextNode textNode) {
                text = StringUtils.normalizeSpace(textNode.text());
            } else if (node instanceof Element element) {
                text = StringUtils.normalizeSpace(element.text());
            }
        }
        return text;
    }
}
