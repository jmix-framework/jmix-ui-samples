package io.jmix.sampler.screen.ui.components.sourcecodeeditor.simple;

import io.jmix.ui.component.CheckBox;
import io.jmix.ui.component.HasValue;
import io.jmix.ui.component.SourceCodeEditor;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("sourcecodeeditor-simple")
@UiDescriptor("sourcecodeeditor-simple.xml")
public class SourceCodeEditorSimpleSample extends ScreenFragment {
    @Autowired
    protected SourceCodeEditor sourceCodeEditor;
    @Autowired
    protected CheckBox highlightActiveLineCheck;
    @Autowired
    protected CheckBox printMarginCheck;
    @Autowired
    protected CheckBox showGutterCheck;

    @Subscribe
    protected void onInit(InitEvent event) {
        highlightActiveLineCheck.setValue(sourceCodeEditor.isHighlightActiveLine());
        printMarginCheck.setValue(sourceCodeEditor.isShowPrintMargin());
        showGutterCheck.setValue(sourceCodeEditor.isShowGutter());
    }

    @Subscribe("highlightActiveLineCheck")
    protected void onHighlightActiveLineCheckValueChange(HasValue.ValueChangeEvent<Boolean> event) {
        if (event.getValue() != null) {
            sourceCodeEditor.setHighlightActiveLine(event.getValue());
        }
    }

    @Subscribe("printMarginCheck")
    protected void onPrintMarginCheckValueChange(HasValue.ValueChangeEvent<Boolean> event) {
        if (event.getValue() != null) {
            sourceCodeEditor.setShowPrintMargin(event.getValue());
        }
    }

    @Subscribe("showGutterCheck")
    protected void onShowGutterCheckValueChange(HasValue.ValueChangeEvent<Boolean> event) {
        if (event.getValue() != null) {
            sourceCodeEditor.setShowGutter(event.getValue());
        }
    }
}
