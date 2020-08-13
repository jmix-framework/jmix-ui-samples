package io.jmix.sampler.screen.ui.components.sourcecodeeditor.mode;

import io.jmix.ui.component.ComboBox;
import io.jmix.ui.component.HasValue;
import io.jmix.ui.component.HighlightMode;
import io.jmix.ui.component.SourceCodeEditor;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

@UiController("sourcecodeeditor-mode")
@UiDescriptor("sourcecodeeditor-mode.xml")
public class SourceCodeEditorModeSample extends ScreenFragment {
    @Autowired
    protected SourceCodeEditor sourceCodeEditor;
    @Autowired
    protected ComboBox<HighlightMode> modeField;

    @Subscribe
    protected void onInit(InitEvent event) {
        Map<String, HighlightMode> modes = new HashMap<>();
        for (HighlightMode mode : SourceCodeEditor.Mode.values()) {
            modes.put(mode.toString(), mode);
        }

        modeField.setOptionsMap(modes);
        modeField.setValue(sourceCodeEditor.getMode());
    }

    @Subscribe("modeField")
    protected void onModeFieldValueChange(HasValue.ValueChangeEvent<HighlightMode> event) {
        if (event.getValue() != null) {
            sourceCodeEditor.setMode(event.getValue());
        }
    }
}
