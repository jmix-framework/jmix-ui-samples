package io.jmix.sampler.screen.ui.components.label.html;

import io.jmix.ui.component.Label;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("label-html")
@UiDescriptor("label-html.xml")
public class LabelHtmlSample extends ScreenFragment {

    protected static final String HTML = "In HTML mode, all HTML formatting tags, such as \n" +
            "<ul>" +
            "  <li><b>bold</b></li>" +
            "  <li>itemized lists</li>" +
            "  <li>etc.</li>" +
            "</ul> " +
            "are preserved.";

    @Autowired
    protected Label<String> htmlLabel;

    @Subscribe
    protected void onInit(InitEvent event) {
        htmlLabel.setValue(HTML);
    }
}
