package io.jmix.sampler.screen.ui.components.textfield.trim;

import io.jmix.ui.component.Button;
import io.jmix.ui.component.CheckBox;
import io.jmix.ui.component.HasValue;
import io.jmix.ui.component.Label;
import io.jmix.ui.component.TextField;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("textfield-trim")
@UiDescriptor("textfield-trim.xml")
public class TextFieldTrimSample extends ScreenFragment {

    @Autowired
    protected CheckBox trim;
    @Autowired
    protected TextField<String> textField;
    @Autowired
    protected Label<String> valueLabel;

    @Subscribe
    protected void onInit(InitEvent event) {
        trim.setValue(textField.isTrimming());
    }

    @Subscribe("trim")
    protected void onTrimValueChange(HasValue.ValueChangeEvent<Boolean> event) {
        textField.setTrimming(trim.getValue());
    }

    @Subscribe("show")
    protected void onShowClick(Button.ClickEvent event) {
        String value = textField.getValue() == null ?
                "null" : StringEscapeUtils.escapeHtml4(textField.getValue());
        valueLabel.setValue("Value: '" + value.replace(" ", "&nbsp;") + "'");
    }
}
