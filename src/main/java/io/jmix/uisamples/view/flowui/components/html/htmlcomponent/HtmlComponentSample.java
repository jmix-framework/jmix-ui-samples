package io.jmix.uisamples.view.flowui.components.html.htmlcomponent;

import com.vaadin.flow.component.Html;
import io.jmix.core.Resources;
import io.jmix.flowui.component.tabsheet.JmixTabSheet;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;

@ViewController("html-component")
@ViewDescriptor("html-component.xml")
public class HtmlComponentSample extends StandardView {

    // tag::stream-resource[] sample-hide
    private static final String SRC_PATH = "META-INF/resources/html/html-component.html";

    @ViewComponent
    private JmixTabSheet tabSheet;

    @Autowired
    private Resources resources;

    @Subscribe
    public void onInit(InitEvent event) {
        try (InputStream resourceAsStream = resources.getResourceAsStream(SRC_PATH)) {
            Html html = new Html(resourceAsStream);
            tabSheet.add("Programmatically added component", html);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
    // end::stream-resource[] sample-hide
}
