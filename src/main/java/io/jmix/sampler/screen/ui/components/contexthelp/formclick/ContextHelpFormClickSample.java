package io.jmix.sampler.screen.ui.components.contexthelp.formclick;

import com.google.common.base.Strings;
import io.jmix.ui.Dialogs;
import io.jmix.ui.Notifications;
import io.jmix.ui.component.CheckBox;
import io.jmix.ui.component.ContentMode;
import io.jmix.ui.component.TextField;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("contexthelp-form-click")
@UiDescriptor("contexthelp-form-click.xml")
public class ContextHelpFormClickSample extends ScreenFragment {

    @Autowired
    protected TextField<String> nameField;
    @Autowired
    protected CheckBox activeField;

    @Autowired
    protected Notifications notifications;
    @Autowired
    protected Dialogs dialogs;

    @Subscribe
    protected void onInit(InitEvent event) {
        nameField.setContextHelpIconClickHandler(clickEvent ->
                notifications.create()
                        .withCaption(Strings.nullToEmpty(clickEvent.getSource().getContextHelpText()))
                        .show()
        );

        activeField.setContextHelpIconClickHandler(clickEvent ->
                dialogs.createMessageDialog()
                        .withCaption("Title")
                        .withMessage(Strings.nullToEmpty(clickEvent.getSource().getContextHelpText()))
                        .withContentMode(ContentMode.HTML)
                        .show()
        );
    }
}