package io.jmix.uisamples.component.nativeselect;

import io.jmix.flowui.data.ValueSource;
import io.jmix.flowui.exception.GuiDevelopmentException;
import io.jmix.flowui.xml.layout.loader.AbstractComponentLoader;
import io.jmix.flowui.xml.layout.support.DataLoaderSupport;
import org.dom4j.Element;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class NativeSelectLoader extends AbstractComponentLoader<NativeSelect<String>> {

    @SuppressWarnings("unchecked")
    @Override
    protected NativeSelect<String> createComponent() {
        return factory.create(NativeSelect.class);
    }

    @Override
    public void loadComponent() {
        componentLoader().loadSizeAttributes(resultComponent, element);
        componentLoader().loadClassNames(resultComponent, element);
        componentLoader().loadEnabled(resultComponent, element);
        componentLoader().loadAriaLabel(resultComponent, element);

        boolean hasOptions = loadOptions();
        if (hasOptions && element.attribute("itemsContainer") != null) {
            throw new GuiDevelopmentException("'nativeSelect' can't have both options and 'itemsContainer'", context);
        }

        DataLoaderSupport dataLoaderSupport = applicationContext.getBean(DataLoaderSupport.class, context);
        dataLoaderSupport.loadItems(resultComponent, element);
        dataLoaderSupport.loadData(resultComponent, element);

        ValueSource<String> valueSource = resultComponent.getValueSource();
        if (hasOptions && valueSource != null && valueSource.getType() != String.class) {
            throw new GuiDevelopmentException("Options declared in XML can be bound only to a String property",
                    context, "property", element.attributeValue("property"));
        }
    }

    protected boolean loadOptions() {
        Map<String, String> labels = new LinkedHashMap<>();
        Map<String, String> groups = new HashMap<>();
        Set<String> groupLabels = new HashSet<>();

        for (Element child : element.elements()) {
            if ("optgroup".equals(child.getName())) {
                String groupLabel = loadResourceString(child, "label", context.getMessageGroup())
                        .orElseThrow(() -> new GuiDevelopmentException("'optgroup' requires a 'label'", context));
                if (!groupLabels.add(groupLabel)) {
                    throw new GuiDevelopmentException("Duplicate 'optgroup' label", context, "label", groupLabel);
                }

                for (Element option : child.elements("option")) {
                    groups.put(loadOption(option, labels), groupLabel);
                }
            } else if ("option".equals(child.getName())) {
                loadOption(child, labels);
            }
        }

        if (labels.isEmpty()) {
            return false;
        }

        resultComponent.setItemLabelGenerator(value -> labels.getOrDefault(value, value));
        resultComponent.setGroupLabelGenerator(groups::get);
        resultComponent.setItems(labels.keySet());
        return true;
    }

    protected String loadOption(Element option, Map<String, String> labels) {
        String value = loadString(option, "value")
                .orElseThrow(() -> new GuiDevelopmentException("'option' requires a non-empty 'value'", context));
        if (labels.containsKey(value)) {
            throw new GuiDevelopmentException("Duplicate 'option' value", context, "value", value);
        }

        labels.put(value, loadResourceString(option, "text", context.getMessageGroup()).orElse(value));
        return value;
    }
}
