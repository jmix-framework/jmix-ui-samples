package io.jmix.uisamples.view.flowui.components.markdowncomponents.markdowneditorsimple;

import io.jmix.flowui.component.markdowneditor.MarkdownEditor;
import io.jmix.flowui.view.*;

@ViewController("markdown-editor-simple")
@ViewDescriptor("markdown-editor-simple.xml")
public class MarkdownEditorSimpleSample extends StandardView {

    @ViewComponent
    protected MarkdownEditor markdownEditor;

    @Subscribe
    protected void onInit(InitEvent event) {
        markdownEditor.setValue("""
                # Markdown Editor

                Use the **toolbar** or _keyboard shortcuts_ to format text.

                - Bulleted list item
                - [Link to jmix.io](https://www.jmix.io)
                - `inline code`

                > Switch to the **Preview** tab to see the rendered result.
                """);
    }
}
