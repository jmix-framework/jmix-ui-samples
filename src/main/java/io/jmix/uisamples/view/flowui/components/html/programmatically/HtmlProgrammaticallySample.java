package io.jmix.uisamples.view.flowui.components.html.programmatically;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.*;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.Subscribe;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@ViewController("html-programmatically")
@ViewDescriptor("html-programmatically.xml")
public class HtmlProgrammaticallySample extends StandardView {

    protected static final String JMIX_LINK = "https://www.jmix.io";

    @Subscribe
    protected void onInit(InitEvent event) {
        initHtmlContent();
    }

    protected void initHtmlContent() {
        Div div = new Div();

        div.add(createH1("HTML Tags Sample"));
        div.add(createParagraph("This is a sample text that showcases the usage of different HTML tags."));
        div.add(createH2("Formatting Tags:"));
        div.add(createParagraph("Here are some formatting tags:"));
        div.add(createUnorderedList(createFormattingListItems()));

        div.add(createH2("Lists:"));
        div.add(createParagraph("Here are some examples of lists:"));
        div.add(createH3("Ordered List:"));
        div.add(createOrderedList(createOrderedListItems()));

        div.add(createH3("Unordered List:"));
        div.add(createUnorderedList(createUnorderedListItems()));

        div.add(createH2("Hyperlinks:"));
        div.add(createParagraph("Hyperlinks can be added using the <a> tag:"));
        div.add(createAnchor("Visit jmix.io", JMIX_LINK));

        getContent().add(div);
    }

    protected Component createH1(String text) {
        return new H1(text);
    }

    protected Component createH2(String text) {
        return new H2(text);
    }

    protected Component createH3(String text) {
        return new H3(text);
    }

    protected Component createParagraph(String text) {
        return new Paragraph(text);
    }

    protected Component createSpan(String text, String... classNames) {
        Component span = createSpan(text);
        span.addClassNames(classNames);

        return span;
    }

    protected Component createSpan(String text) {
        return new Span(text);
    }

    protected Component createUnorderedList(ListItem... listItems) {
        return new UnorderedList(listItems);
    }

    protected Component createOrderedList(ListItem... listItems) {
        return new OrderedList(listItems);
    }

    protected ListItem[] createFormattingListItems() {
        return new ListItem[]{
                new ListItem(
                        createSpan("Bold text: ", "text-semibold"),
                        createSpan("Use the <b> tag to make text "),
                        createSpan("bold.", "text-semibold")
                ),
                new ListItem(
                        createSpan("Italic text: ", "font-italic"),
                        createSpan("Use the <i> tag to make text "),
                        createSpan("italic.", "font-italic")
                ),
                new ListItem(
                        createSpan("Highlighted text: ", "highlight"),
                        createSpan("Use the <span> tag to add a "),
                        createSpan("highlighted effect", "highlight"),
                        createSpan(" to text.")
                )
        };
    }

    protected ListItem[] createOrderedListItems() {
        return new ListItem[]{
                new ListItem("First item"),
                new ListItem("Second item"),
                new ListItem("Third item")
        };
    }

    protected ListItem[] createUnorderedListItems() {
        return new ListItem[]{
                new ListItem("Apples"),
                new ListItem("Oranges"),
                new ListItem("Bananas")
        };
    }

    protected Component createAnchor(String text, String href) {
        return new Anchor(href, text, AnchorTarget.BLANK);
    }

}
