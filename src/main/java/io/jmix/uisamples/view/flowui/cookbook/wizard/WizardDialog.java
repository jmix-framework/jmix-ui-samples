package io.jmix.uisamples.view.flowui.cookbook.wizard;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.ThemableLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import io.jmix.flowui.component.tabsheet.JmixTabSheet;
import io.jmix.flowui.component.validation.ValidationErrors;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;
import io.jmix.uisamples.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.function.Supplier;

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

    private int tabsCount;

    @Subscribe
    public void onBeforeShow(final BeforeShowEvent event) {
        if (tabsCount == 0) {
            throw new IllegalStateException("No steps added");
        }

        wizardContent.getTabAt(0).setEnabled(true);
        wizardContent.setSelectedIndex(0);

        updateControlsState();
    }

    public WizardDialog addStep(WizardStep<?> step) {
        Tab tab = createTab(step, ++tabsCount);
        tab.setEnabled(false);
        step.content().setupData(getViewData());
        wizardContent.add(tab, step.content());
        return this;
    }

    @Subscribe("wizardContent")
    public void onWizardContentSelectedChange(final JmixTabSheet.SelectedChangeEvent event) {
        updateControlsState();
    }

    @Subscribe(id = "backButton", subject = "clickListener")
    public void onBackButtonClick(final ClickEvent<JmixButton> event) {
        wizardContent.getSelectedTab().setEnabled(false);
        int selectedIndex = wizardContent.getSelectedIndex();
        wizardContent.getTabAt(--selectedIndex).setEnabled(true);
        wizardContent.setSelectedIndex(selectedIndex);
    }

    @Subscribe(id = "nextButton", subject = "clickListener")
    public void onNextButtonClick(final ClickEvent<JmixButton> event) {
        if (isLastTabSelected()) {
            closeWithSave();
            return;
        }

        ValidationErrors validationErrors = validateCurrentStep();

        if (validationErrors.isEmpty()) {
            wizardContent.getSelectedTab().setEnabled(false);
            int selectedIndex = wizardContent.getSelectedIndex();
            wizardContent.getTabAt(++selectedIndex).setEnabled(true);
            wizardContent.setSelectedIndex(selectedIndex);
        } else {
            viewValidation.focusProblemComponent(validationErrors);
            viewValidation.showValidationErrors(validationErrors);
        }
    }

    private void updateControlsState() {
        backButton.setVisible(wizardContent.getSelectedIndex() != 0);

        if (isLastTabSelected()) {
            nextButton.setText("Complete");
            nextButton.setIcon(VaadinIcon.CHECK.create());
            nextButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        } else {
            nextButton.setText("Next");
            nextButton.setIcon(VaadinIcon.ARROW_CIRCLE_RIGHT_O.create());
            nextButton.removeThemeVariants(ButtonVariant.LUMO_PRIMARY);
        }
    }

    private boolean isLastTabSelected() {
        return wizardContent.getSelectedIndex() == tabsCount - 1;
    }

    private ValidationErrors validateCurrentStep() {
        return viewValidation.validateUiComponents(wizardContent.getContentByTab(wizardContent.getSelectedTab()));
    }

    private Tab createTab(WizardStep<?> step, int index) {
        VerticalLayout layout = createLayout(VerticalLayout::new);
        layout.setAlignItems(FlexComponent.Alignment.CENTER);
        layout.addClassName("tab-container");

        HorizontalLayout horizontalLayout = createLayout(HorizontalLayout::new);
        horizontalLayout.addClassName("label-container");

        Span label = new Span("Step #" + index);
        label.addClassName("label");
        horizontalLayout.add(step.icon(), label);

        Span text = new Span(step.text());
        text.addClassNames("sublabel");

        layout.add(horizontalLayout, text);

        return new Tab(layout);
    }

    private <T extends ThemableLayout & FlexComponent> T createLayout(Supplier<T> factory) {
        T layout = factory.get();
        layout.setPadding(false);
        layout.setSpacing(false);
        return layout;
    }
}
