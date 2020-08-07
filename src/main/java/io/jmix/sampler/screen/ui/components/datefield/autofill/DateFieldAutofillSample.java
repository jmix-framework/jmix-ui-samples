package io.jmix.sampler.screen.ui.components.datefield.autofill;

import io.jmix.ui.component.CheckBox;
import io.jmix.ui.component.DateField;
import io.jmix.ui.component.HasValue;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

@UiController("datefield-autofill")
@UiDescriptor("datefield-autofill.xml")
public class DateFieldAutofillSample extends ScreenFragment {

    @Autowired
    protected DateField<Date> dateField;
    @Autowired
    protected CheckBox autofillField;

    @Subscribe
    protected void onInit(InitEvent event) {
        autofillField.setValue(true);
    }

    @Subscribe("autofillField")
    public void onAutofillFieldValueChange(HasValue.ValueChangeEvent<Boolean> event) {
        dateField.setAutofill(Boolean.TRUE.equals(event.getValue()));
    }
}
