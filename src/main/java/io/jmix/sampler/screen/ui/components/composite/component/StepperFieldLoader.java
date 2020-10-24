package io.jmix.sampler.screen.ui.components.composite.component;

import com.google.common.base.Strings;
import io.jmix.ui.xml.layout.loader.AbstractFieldLoader;

import java.math.BigDecimal;

public class StepperFieldLoader extends AbstractFieldLoader<StepperField> {

    @Override
    public void createComponent() {
        resultComponent = factory.create(StepperField.NAME);
        loadId(resultComponent, element);
    }

    @Override
    public void loadComponent() {
        super.loadComponent();
        String incrementStr = element.attributeValue("step");
        if (!Strings.isNullOrEmpty(incrementStr)) {
            resultComponent.setStep(new BigDecimal(incrementStr));
        }
    }
}
