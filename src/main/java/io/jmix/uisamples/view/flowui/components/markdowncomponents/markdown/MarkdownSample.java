package io.jmix.uisamples.view.flowui.components.markdowncomponents.markdown;

import com.vaadin.flow.component.markdown.Markdown;
import io.jmix.core.Resources;
import io.jmix.flowui.component.tabsheet.JmixTabSheet;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

@ViewController("markdown")
@ViewDescriptor("markdown.xml")
public class MarkdownSample extends StandardView {

    private static final String SRC_PATH = "META-INF/resources/markdown/markdown-component.md";

    @ViewComponent
    private JmixTabSheet tabSheet;
    @ViewComponent
    private MessageBundle messageBundle;

    @Autowired
    private Resources resources;

    @Subscribe
    public void onInit(InitEvent event) {
        String content = resources.getResourceAsString(SRC_PATH);

        Markdown markdown = new Markdown(content);
        tabSheet.add(messageBundle.getMessage("programmaticTab.label"), markdown);
    }
}
