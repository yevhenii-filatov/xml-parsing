package com.extrawest.handler;

import com.extrawest.model.*;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static com.extrawest.model.FlowerConstants.*;

/**
 * @author Yevhenii Filatov
 * @since 5/11/23
 */

public class FlowerDOMHandler {
    public Flower getFlower(String xmlFilePath) throws IOException {
        List<Plant> plants = Jsoup.parse(new File(xmlFilePath))
           .select(PLANT)
           .stream()
           .map(this::plant)
           .filter(Objects::nonNull)
           .toList();
        return new Flower(plants);
    }

    private Plant plant(Element plantElement) {
        if (plantElement == null) {
            return null;
        }
        String name = text(plantElement.selectFirst(NAME));
        String origin = text(plantElement.selectFirst(ORIGIN));
        Soil soil = Soil.valueOf(text(plantElement.selectFirst(SOIL)));
        MultiplyingType multiplyingType = MultiplyingType.valueOf(text(plantElement.selectFirst(MULTIPLYING_TYPE)));
        GrowingTips growingTips = growingTips(plantElement.selectFirst(GROWING_TIPS));
        VisualParameters visualParameters = visualParameters(plantElement.selectFirst(VISUAL_PARAMETERS));
        return new Plant(name, soil, origin, visualParameters, growingTips, multiplyingType);
    }

    private VisualParameters visualParameters(Element visualParametersElement) {
        if (visualParametersElement == null) {
            return null;
        }
        Color stemColor = Color.valueOf(text(visualParametersElement.selectFirst(STEM_COLOR)));
        Color leafColor = Color.valueOf(text(visualParametersElement.selectFirst(LEAF_COLOR)));
        Integer size = Integer.parseInt(text(visualParametersElement.selectFirst(PLANT_SIZE)));
        return new VisualParameters(stemColor, leafColor, size);
    }

    private GrowingTips growingTips(Element growingTipsElement) {
        if (growingTipsElement == null) {
            return null;
        }
        Integer temperature = Integer.parseInt(text(growingTipsElement.selectFirst(TEMPERATURE)));
        Boolean lovesLight = Boolean.parseBoolean(text(growingTipsElement.selectFirst(LOVES_LIGHT)));
        Integer watering = Integer.parseInt(text(growingTipsElement.selectFirst(WATERING)));
        return new GrowingTips(temperature, lovesLight, watering);
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
