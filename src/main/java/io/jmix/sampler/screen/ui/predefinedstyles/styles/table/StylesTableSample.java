package io.jmix.sampler.screen.ui.predefinedstyles.styles.table;

import io.jmix.sampler.entity.Customer;
import io.jmix.ui.component.CheckBox;
import io.jmix.ui.component.Component;
import io.jmix.ui.component.HasValue;
import io.jmix.ui.component.Table;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("styles-table")
@UiDescriptor("styles-table.xml")
public class StylesTableSample extends ScreenFragment {

    @Autowired
    protected Table<Customer> customersTable;

    @Subscribe
    protected void onInit(InitEvent event) {
        for (Component component : getFragment().getComponents()) {
            if (component instanceof CheckBox) {
                CheckBox checkBox = (CheckBox) component;
                checkBox.addValueChangeListener(this::updateTableStyle);
            }
        }
    }

    private void updateTableStyle(HasValue.ValueChangeEvent<Boolean> e) {
        String id = e.getComponent().getId();
        Object value = e.getValue();
        if (Boolean.TRUE.equals(value)) {
            customersTable.addStyleName(prepareStyleName(id));
        } else {
            customersTable.removeStyleName(prepareStyleName(id));
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
