package io.jmix.uisamples.view.flowui.components.richtexteditor.themevariant;

import com.vaadin.flow.component.AbstractField.ComponentValueChangeEvent;
import io.jmix.flowui.component.checkbox.JmixCheckbox;
import io.jmix.flowui.component.richtexteditor.RichTextEditor;
import io.jmix.flowui.kit.component.richtexteditor.RichTextEditorVariant;
import io.jmix.flowui.view.*;

@ViewController("rich-text-editor-theme-variant")
@ViewDescriptor("rich-text-editor-theme-variant.xml")
public class RichTextEditorThemeVariantSample extends StandardView {

    @ViewComponent
    protected RichTextEditor richTextEditor;

    @Subscribe
    protected void onInit(InitEvent event) {
        richTextEditor.setValue("<p><strong>Bold</strong> <em>Italic</em> <u>Underline</u> <s>Strikethrough</s></p>");
    }

    @Subscribe("noBorderCheckbox")
    protected void onHighlightCheckboxValueChange(ComponentValueChangeEvent<JmixCheckbox, Boolean> event) {
        if (event.getValue()) {
            richTextEditor.addThemeVariants(RichTextEditorVariant.LUMO_NO_BORDER);
        } else {
            richTextEditor.removeThemeVariants(RichTextEditorVariant.LUMO_NO_BORDER);
        }
    }

    @Subscribe("compactCheckbox")
    protected void onHighlightGutterCheckboxValueChange(ComponentValueChangeEvent<JmixCheckbox, Boolean> event) {
        if (event.getValue()) {
            richTextEditor.addThemeVariants(RichTextEditorVariant.LUMO_COMPACT);
        } else {
            richTextEditor.removeThemeVariants(RichTextEditorVariant.LUMO_COMPACT);
        }
    }

    @Subscribe("readOnlyCheckbox")
    protected void onGutterCheckboxValueChange(ComponentValueChangeEvent<JmixCheckbox, Boolean> event) {
        richTextEditor.setReadOnly(event.getValue());
    }

    @Subscribe("enabledCheckbox")
    protected void onLineNumbersCheckboxValueChange(ComponentValueChangeEvent<JmixCheckbox, Boolean> event) {
        richTextEditor.setEnabled(!event.getValue());
    }
}
