package io.jmix.sampler.screen.ui.predefinedstyles.inputfields.simple;

import io.jmix.ui.component.TextArea;
import io.jmix.ui.component.TextField;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("inputfields-simple")
@UiDescriptor("inputfields-simple.xml")
public class InputFieldsSimpleSample extends ScreenFragment {
    @Autowired
    protected TextArea<String> textAreaAlignCenter;
    @Autowired
    protected TextArea<String> textAreaAlignRight;

    @Autowired
    protected TextField<String> textFieldAlignCenter;
    @Autowired
    protected TextField<String> textFieldAlignRight;
    @Autowired
    protected TextField<String> textFieldBorderless;
    @Autowired
    protected TextField<String> textFieldInlineIcon;

    @Subscribe
    protected void onInit(InitEvent event) {
        textFieldBorderless.setValue("Borderless");
        textFieldAlignCenter.setValue("Align center");
        textFieldAlignRight.setValue("Align right");
        textFieldInlineIcon.setValue("Inline icon");

        textAreaAlignCenter.setValue("Align center");
        textAreaAlignRight.setValue("Align right");
    }
}
