package io.jmix.uisamples.component.chart;

import com.google.common.base.Strings;
import com.storedobject.chart.*;
import io.jmix.flowui.exception.GuiDevelopmentException;
import io.jmix.flowui.xml.layout.loader.AbstractComponentLoader;
import org.dom4j.Element;

import java.util.Arrays;

public class ChartLoader extends AbstractComponentLoader<SOChart> {

    protected CategoryData predefinedLabels;
    protected Data predefinedData;

    @Override
    protected SOChart createComponent() {
        return factory.create(SOChart.class);
    }

    @Override
    public void loadComponent() {
        componentLoader().loadSizeAttributes(resultComponent, element);

        loadLabels(element);
        loadData(element);
        loadSubComponents(resultComponent, element);
    }

    protected void loadLabels(Element element) {
        String[] labels = loadString(element, "labels")
                .map(this::split)
                .orElse(null);

        predefinedLabels = new CategoryData(labels);
    }

    protected void loadData(Element element) {
        Number[] data = loadString(element, "data")
                .map(this::split)
                .map(this::mapToNumbers)
                .orElse(null);

        predefinedData = new Data(data);

        if (predefinedData.asList().size() != predefinedLabels.asList().size()) {
            throw new GuiDevelopmentException("Chart's data and labels must be the same size", context);
        }
    }

    protected void loadSubComponents(SOChart resultComponent, Element element) {
        for (Element nestedElement : element.elements()) {
            Component subComponent = switch (nestedElement.getName()) {
                case "nightingaleRoseChart" -> loadNightingaleRoseChart(nestedElement);
                case "barChart" -> loadBarChart(nestedElement);
                case "toolbox" -> loadToolbox(nestedElement);
                case "title" -> loadTitle(nestedElement);
                default -> throw new GuiDevelopmentException("Unknown nested tag", context);
            };

            resultComponent.add(subComponent);
        }
    }

    protected NightingaleRoseChart loadNightingaleRoseChart(Element element) {
        NightingaleRoseChart nightingaleRoseChart = new NightingaleRoseChart(predefinedLabels, predefinedData);
        loadString(element, "name", nightingaleRoseChart::setName);
        return nightingaleRoseChart;
    }

    protected BarChart loadBarChart(Element element) {
        BarChart barChart = new BarChart(predefinedLabels, predefinedData);

        RectangularCoordinate coordinate =
                new RectangularCoordinate(new XAxis(DataType.CATEGORY), new YAxis(DataType.NUMBER));
        barChart.plotOn(coordinate);

        loadString(element, "name", barChart::setName);

        return barChart;
    }

    protected Toolbox loadToolbox(Element element) {
        Element buttons = element.element("buttons");

        if (buttons == null) {
            throw new GuiDevelopmentException("Buttons are required for Toolbox", context);
        }

        Toolbox toolbox = new Toolbox();

        for (Element button : buttons.elements()) {
            loadString(button, "type")
                    .map(this::toolboxButtonMapper)
                    .ifPresent(toolbox::addButton);
        }

        return toolbox;
    }

    protected Title loadTitle(Element element) {
        String text = loadString(element, "text")
                .orElseThrow(() -> new GuiDevelopmentException("Required attribute 'text' is not found", context));

        Title title = new Title(text);
        loadString(element, "subText", title::setSubtext);

        return title;
    }

    protected ToolboxButton toolboxButtonMapper(String type) {
        return switch (type) {
            case "download" -> new Toolbox.Download();
            case "restore" -> new Toolbox.Restore();
            case "zoom" -> new Toolbox.Zoom();
            case "data-view" -> new Toolbox.DataView();
            default -> throw new GuiDevelopmentException("Unknown toolbox button type", context);
        };
    }

    protected String[] split(String names) {
        return Arrays.stream(names.split("[\\s,]+"))
                .filter(split -> !Strings.isNullOrEmpty(split))
                .toArray(String[]::new);
    }

    protected Number[] mapToNumbers(String[] strings) {
        return Arrays.stream(strings)
                .map(Double::valueOf)
                .toArray(Number[]::new);
    }
}
