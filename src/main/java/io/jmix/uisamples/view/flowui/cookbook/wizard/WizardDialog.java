package io.jmix.uisamples.view.flowui.cookbook.wizard;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.tabs.Tab;
import io.jmix.flowui.component.tabsheet.JmixTabSheet;
import io.jmix.flowui.component.validation.ValidationErrors;
import io.jmix.flowui.icon.Icons;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.kit.icon.JmixFontIcon;
import io.jmix.flowui.view.*;
import io.jmix.uisamples.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;

@ViewController("wizard-dialog")
@ViewDescriptor("wizard-dialog.xml")
@EditedEntityContainer("employeeDc")
@DialogMode(width = "50em")
public class WizardDialog extends StandardDetailView<Employee> {

    @ViewComponent
    private JmixTabSheet wizardContent;

    @ViewComponent
    private JmixButton backButton;
    @ViewComponent
    private JmixButton nextButton;

    @Autowired
    private ViewValidation viewValidation;
    @Autowired
    private Icons icons;

    @Subscribe
    public void onBeforeShow(final BeforeShowEvent event) {
        if (wizardContent.getTabCount() == 0) {
            throw new IllegalStateException("No steps added");
        }

        wizardContent.setSelectedIndex(0);

        updateStepsState();
        updateControlsState();
    }

    public WizardDialog addStep(WizardStep step) {
        wizardContent.add(createTab(step), step.content());

        updateStepsState();
        updateControlsState();
        return this;
    }

    @Subscribe("wizardContent")
    public void onWizardContentSelectedChange(final JmixTabSheet.SelectedChangeEvent event) {
        updateStepsState();
        updateControlsState();
    }

    @Subscribe(id = "backButton", subject = "clickListener")
    public void onBackButtonClick(final ClickEvent<JmixButton> event) {
        wizardContent.setSelectedIndex(wizardContent.getSelectedIndex() - 1);
    }

    @Subscribe(id = "nextButton", subject = "clickListener")
    public void onNextButtonClick(final ClickEvent<JmixButton> event) {
        if (isLastTabSelected()) {
            closeWithSave();
            return;
        }

        ValidationErrors validationErrors = validateCurrentStep();

        if (validationErrors.isEmpty()) {
            int nextIndex = wizardContent.getSelectedIndex() + 1;
            wizardContent.getTabAt(nextIndex).setEnabled(true);
            wizardContent.setSelectedIndex(nextIndex);
        } else {
            viewValidation.focusProblemComponent(validationErrors);
            viewValidation.showValidationErrors(validationErrors);
        }
    }

    private void updateStepsState() {
        int selectedIndex = wizardContent.getSelectedIndex();
        for (int i = 0; i < wizardContent.getTabCount(); i++) {
            wizardContent.getTabAt(i).setEnabled(i <= selectedIndex);
        }
    }

    private void updateControlsState() {
        backButton.setVisible(wizardContent.getSelectedIndex() != 0);

        if (isLastTabSelected()) {
            nextButton.setText("Complete");
            nextButton.setIcon(icons.get(JmixFontIcon.CHECK));
            nextButton.addThemeVariants(ButtonVariant.PRIMARY);
        } else {
            nextButton.setText("Next");
            nextButton.setIcon(icons.get(JmixFontIcon.ARROW_CIRCLE_RIGHT));
            nextButton.removeThemeVariants(ButtonVariant.PRIMARY);
        }
    }

    private boolean isLastTabSelected() {
        return wizardContent.getSelectedIndex() == wizardContent.getTabCount() - 1;
    }

    private ValidationErrors validateCurrentStep() {
        return viewValidation.validateUiComponents(wizardContent.getContentByTab(wizardContent.getSelectedTab()));
    }

    private Tab createTab(WizardStep step) {
        Div stepContent = new Div(step.icon(), new Span(step.text()));
        stepContent.addClassName("wizard-step");
        return new Tab(stepContent);
    }
}
