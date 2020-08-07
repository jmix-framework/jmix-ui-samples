package io.jmix.sampler.screen.ui.components.contexthelp.fieldclick;


import com.google.common.base.Strings;
import io.jmix.ui.Dialogs;
import io.jmix.ui.component.HasContextHelp;
import io.jmix.ui.screen.Install;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("contexthelp-field-click")
@UiDescriptor("contexthelp-field-click.xml")
public class ContextHelpFieldClickSample extends ScreenFragment {

    @Autowired
    protected Dialogs dialogs;

    @Install(to = "textField", subject = "contextHelpIconClickHandler")
    protected void textFieldContextHelpIconClickHandler(HasContextHelp.ContextHelpIconClickEvent event) {
        dialogs.createMessageDialog()
                .withCaption("Title")
                .withMessage(Strings.nullToEmpty(event.getSource().getContextHelpText()))
                .show();
    }
}
