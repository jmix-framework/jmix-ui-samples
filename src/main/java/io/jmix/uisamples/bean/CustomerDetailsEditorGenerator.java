package io.jmix.uisamples.bean;

import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import io.jmix.core.DataManager;
import io.jmix.core.EntityStates;
import io.jmix.core.Metadata;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.component.grid.DataGrid;
import io.jmix.flowui.component.validation.ValidationErrors;
import io.jmix.flowui.data.grid.ContainerDataGridItems;
import io.jmix.flowui.icon.Icons;
import io.jmix.flowui.kit.action.Action;
import io.jmix.flowui.kit.action.ActionVariant;
import io.jmix.flowui.kit.action.BaseAction;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.kit.icon.JmixFontIcon;
import io.jmix.flowui.view.ViewValidation;
import io.jmix.uisamples.entity.Customer;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Component("uisamples_CustomerDetailsEditorGenerator")
public class CustomerDetailsEditorGenerator {

    protected UiComponents uiComponents;
    protected CustomerDetailsGenerator detailsGenerator;
    protected DataManager dataManager;
    protected Metadata metadata;
    protected ViewValidation viewValidation;
    protected EntityStates entityStates;
    protected Icons icons;

    protected DataGrid<Customer> customersDataGrid;

    public CustomerDetailsEditorGenerator(UiComponents uiComponents,
                                          CustomerDetailsGenerator detailsGenerator,
                                          DataManager dataManager,
                                          Metadata metadata,
                                          ViewValidation viewValidation,
                                          EntityStates entityStates,
                                          Icons icons) {
        this.uiComponents = uiComponents;
        this.detailsGenerator = detailsGenerator;
        this.dataManager = dataManager;
        this.metadata = metadata;
        this.viewValidation = viewValidation;
        this.entityStates = entityStates;
        this.icons = icons;
    }

    public VerticalLayout createDetailsEditorComponent() {
        VerticalLayout verticalLayout = uiComponents.create(VerticalLayout.class);
        verticalLayout.setPadding(false);
        verticalLayout.addComponentAsFirst(detailsGenerator.createCustomerDetailsRenderer());
        return verticalLayout;
    }

    public void setEditedEntity(VerticalLayout verticalLayout, Customer customer) {
        Customer bufferCustomer = metadata.create(Customer.class);
        copy(customer, bufferCustomer);
        detailsGenerator.setCustomer((FormLayout) verticalLayout.getComponentAt(0), bufferCustomer);

        GenerationContext generationContext = new GenerationContext(customer, bufferCustomer, verticalLayout);
        verticalLayout.add(createDetailsButtonPanel(generationContext));
    }

    protected HorizontalLayout createDetailsButtonPanel(GenerationContext context) {
        HorizontalLayout layout = uiComponents.create(HorizontalLayout.class);
        layout.setPadding(false);

        layout.add(createSaveAndCloseButton(context), createCancelButton(context.initialCustomer()));
        return layout;
    }

    protected JmixButton createSaveAndCloseButton(GenerationContext context) {
        JmixButton button = uiComponents.create(JmixButton.class);
        button.setAction(createSaveAndCloseAction(context));

        return button;
    }

    protected JmixButton createCancelButton(Customer customer) {
        JmixButton button = uiComponents.create(JmixButton.class);
        button.setAction(createCancelAction(customer));

        return button;
    }

    protected Action createSaveAndCloseAction(GenerationContext context) {
        return new BaseAction("ok")
                .withText("OK")
                .withIcon(icons.get(JmixFontIcon.CHECK))
                .withVariant(ActionVariant.PRIMARY)
                .withHandler(e -> {
                    ValidationErrors validationErrors = validate(context.mainLayout());
                    if (validationErrors.isEmpty()) {
                        Customer customer = context.initialCustomer();
                        copy(context.bufferCustomer(), customer);

                        dataManager.save(customer);
                        customersDataGrid.setDetailsVisible(customer, false);
                    } else {
                        viewValidation.showValidationErrors(validationErrors);
                    }
                });
    }

    protected Action createCancelAction(Customer customer) {
        return new BaseAction("cancel")
                .withText("Cancel")
                .withIcon(icons.get(JmixFontIcon.BAN))
                .withHandler(e -> {
                    customersDataGrid.setDetailsVisible(customer, false);

                    if (entityStates.isNew(customer)
                            && customersDataGrid.getDataProvider() instanceof ContainerDataGridItems<?> items) {
                        items.getContainer().getMutableItems().remove(customer);
                    }
                });
    }

    protected void copy(Customer copyFrom, Customer copyTo) {
        copyTo.setName(copyFrom.getName());
        copyTo.setLastName(copyFrom.getLastName());
        copyTo.setGrade(copyFrom.getGrade());
        copyTo.setActive(copyFrom.isActive());
        copyTo.setEmail(copyFrom.getEmail());
        copyTo.setAge(copyFrom.getAge());
    }

    protected ValidationErrors validate(com.vaadin.flow.component.Component mainLayout) {
        return viewValidation.validateUiComponents(mainLayout);
    }

    protected record GenerationContext(Customer initialCustomer,
                                       Customer bufferCustomer,
                                       VerticalLayout mainLayout) {
    }

    public void setCustomersDataGrid(DataGrid<Customer> customersDataGrid) {
        this.customersDataGrid = customersDataGrid;
    }
}