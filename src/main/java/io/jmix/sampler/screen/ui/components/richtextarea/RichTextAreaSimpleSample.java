package io.jmix.sampler.screen.ui.components.richtextarea;

import io.jmix.ui.component.RichTextArea;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("richtextarea-simple")
@UiDescriptor("richtextarea-simple.xml")
public class RichTextAreaSimpleSample extends ScreenFragment {

    @Autowired
    protected RichTextArea richTextArea;

    @Subscribe
    protected void onInit(InitEvent event) {
        richTextArea.setValue("<i>Jackdaws </i><u>love</u> <font color=\"#0000ff\">my</font> " +
                "<font size=\"7\">big</font> <sup>sphinx</sup> " +
                "<font face=\"Verdana\">of</font> <span style=\"background-color: " +
                "red;\">quartz</span>");
    }
}
