package io.jmix.sampler.screen.ui.predefinedstyles.styles.tabsheet;

import io.jmix.ui.component.CheckBox;
import io.jmix.ui.component.Component;
import io.jmix.ui.component.HasValue;
import io.jmix.ui.component.TabSheet;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("styles-tabsheet")
@UiDescriptor("styles-tabsheet.xml")
public class StylesTabSheetSample extends ScreenFragment {
    @Autowired
    protected TabSheet tabSheet;

    @Subscribe
    protected void onInit(InitEvent event) {
        for (Component component : getFragment().getComponents()) {
            if (component instanceof CheckBox) {
                CheckBox checkBox = (CheckBox) component;
                checkBox.addValueChangeListener(this::changeTableStyle);
            }
        }

        for (TabSheet.Tab tab : tabSheet.getTabs()) {
            tab.setClosable(true);
        }
    }

    private void changeTableStyle(HasValue.ValueChangeEvent e) {
        String id = e.getComponent().getId();
        Object value = e.getValue();
        if (value != null) {
            Boolean checked = (Boolean) value;
            if (checked) {
                tabSheet.addStyleName(prepareStyleName(id));
            } else {
                tabSheet.removeStyleName(prepareStyleName(id));
            }
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
