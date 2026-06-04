package io.jmix.uisamples.view.flowui.components.markdowneditor.dataaware;

import com.vaadin.flow.component.AbstractField.ComponentValueChangeEvent;
import com.vaadin.flow.component.html.Span;
import io.jmix.core.Metadata;
import io.jmix.flowui.component.markdowneditor.MarkdownEditor;
import io.jmix.flowui.model.InstanceContainer;
import io.jmix.flowui.view.*;
import io.jmix.uisamples.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;

@ViewController("markdown-editor-dataaware")
@ViewDescriptor("markdown-editor-dataaware.xml")
public class MarkdownEditorDataawareSample extends StandardView {

    @ViewComponent
    protected InstanceContainer<Order> orderDc;
    @ViewComponent
    protected Span spanValue;

    @Autowired
    protected Metadata metadata;

    @Subscribe
    protected void onInit(View.InitEvent event) {
        Order order = metadata.create(Order.class);
        order.setDescription("""
                ## Order description

                **Customer:** John Doe

                **E-mail:** j.doe@jmix.io

                **Comment:** Deliver as quickly as possible.
                """);
        orderDc.setItem(order);
    }

    @Subscribe("markdownEditor")
    protected void onMarkdownEditorValueChange(ComponentValueChangeEvent<MarkdownEditor, String> changeEvent) {
        spanValue.setText(orderDc.getItem().getDescription());
    }
}
