package io.jmix.uisamples.view.flowui.cookbook.wizard;

import com.vaadin.flow.component.Component;
import io.jmix.flowui.fragment.Fragment;

public record WizardStep(Component icon, String text, Fragment<?> content) {
}
