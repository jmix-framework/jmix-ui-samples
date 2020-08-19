package io.jmix.sampler.screen.ui.predefinedstyles.styles.datagrid;

import io.jmix.sampler.entity.Customer;
import io.jmix.ui.component.CheckBox;
import io.jmix.ui.component.Component;
import io.jmix.ui.component.DataGrid;
import io.jmix.ui.component.HasValue;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("styles-datagrid")
@UiDescriptor("styles-datagrid.xml")
public class StylesDataGridSample extends ScreenFragment {

    @Autowired
    protected DataGrid<Customer> customersDataGrid;

    @Subscribe
    protected void onInit(InitEvent event) {
        for (Component component : getFragment().getComponents()) {
            if (component instanceof CheckBox) {
                CheckBox checkBox = (CheckBox) component;
                checkBox.addValueChangeListener(this::updateDataGridStyle);
            }
        }
    }

    private void updateDataGridStyle(HasValue.ValueChangeEvent<Boolean> e) {
        String id = e.getComponent().getId();
        Boolean value = e.getValue();
        if (Boolean.TRUE.equals(value)) {
            customersDataGrid.addStyleName(prepareStyleName(id));
        } else {
            customersDataGrid.removeStyleName(prepareStyleName(id));
        }
    }

    private String prepareStyleName(String stylename) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < stylename.length(); i++) {
            char c = stylename.charAt(i);
            if (Character.isUpperCase(c)) {
                sb.append("-").append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}
