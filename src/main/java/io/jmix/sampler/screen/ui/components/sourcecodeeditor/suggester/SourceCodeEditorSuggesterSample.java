package io.jmix.sampler.screen.ui.components.sourcecodeeditor.suggester;

import io.jmix.core.MetadataTools;
import io.jmix.sampler.entity.Customer;
import io.jmix.ui.component.autocomplete.AutoCompleteSupport;
import io.jmix.ui.component.autocomplete.Suggestion;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.model.CollectionLoader;
import io.jmix.ui.screen.Install;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@UiController("sourcecodeeditor-suggester")
@UiDescriptor("sourcecodeeditor-suggester.xml")
public class SourceCodeEditorSuggesterSample extends ScreenFragment {
    @Autowired
    protected CollectionContainer<Customer> customersDc;
    @Autowired
    protected CollectionLoader<Customer> customersLoader;
    @Autowired
    protected MetadataTools metadataTools;

    @Install(to = "sourceCodeEditor", subject = "suggester")
    protected List<Suggestion> sourceCodeEditorSuggester(AutoCompleteSupport source, String text, int cursorPosition) {
        List<Suggestion> suggestions = new ArrayList<>();
        customersLoader.load();
        for (Customer customer : customersDc.getItems()) {
            String customerName = metadataTools.getInstanceName(customer);
            suggestions.add(new Suggestion(source, customerName, customerName, null, -1, -1));
        }
        return suggestions;
    }
}
