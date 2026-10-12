package io.jmix.uisamples.view.flowui.cookbook.wizard;

import com.vaadin.flow.component.ClickEvent;
import io.jmix.flowui.DialogWindows;
import io.jmix.flowui.Fragments;
import io.jmix.flowui.icon.Icons;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.kit.icon.JmixFontIcon;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.Subscribe;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import io.jmix.uisamples.entity.Employee;
import io.jmix.uisamples.view.flowui.cookbook.wizard.step.FirstStep;
import io.jmix.uisamples.view.flowui.cookbook.wizard.step.SecondStep;
import io.jmix.uisamples.view.flowui.cookbook.wizard.step.ThirdStep;
import org.springframework.beans.factory.annotation.Autowired;

@ViewController("wizard")
@ViewDescriptor("wizard.xml")
public class WizardSample extends StandardView {

    @Autowired
    private DialogWindows dialogWindows;
    @Autowired
    private Fragments fragments;
    @Autowired
    private Icons icons;

    @Subscribe(id = "button", subject = "clickListener")
    public void onButtonClick(final ClickEvent<JmixButton> event) {
        dialogWindows.detail(this, Employee.class)
                .newEntity()
                .withViewClass(WizardDialog.class)
                .withViewConfigurer(this::wizardConfigurer)
                .open();
    }

    private void wizardConfigurer(WizardDialog wizardDialog) {
        wizardDialog.addStep(
                        new WizardStep(icons.get(JmixFontIcon.USER_CARD), "Employee information",
                                fragments.create(wizardDialog, FirstStep.class))
                )
                .addStep(
                        new WizardStep(icons.get(JmixFontIcon.BUILDING), "Address",
                                fragments.create(wizardDialog, SecondStep.class))
                )
                .addStep(
                        new WizardStep(icons.get(JmixFontIcon.CHECK_CIRCLE), "Confirmation",
                                fragments.create(wizardDialog, ThirdStep.class))
                );
    }
}
