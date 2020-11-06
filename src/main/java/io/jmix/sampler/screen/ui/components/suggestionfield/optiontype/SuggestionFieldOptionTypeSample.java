package io.jmix.sampler.screen.ui.components.suggestionfield.optiontype;

import io.jmix.core.Messages;
import io.jmix.sampler.entity.SendingStatus;
import io.jmix.ui.component.SuggestionField;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@UiController("suggestionfield-option-type")
@UiDescriptor("suggestionfield-option-type.xml")
public class SuggestionFieldOptionTypeSample extends ScreenFragment {

    @Autowired
    protected SuggestionField<String> stringSuggestionField;
    @Autowired
    protected SuggestionField<SendingStatus> enumSuggestionField;

    @Autowired
    protected Messages messages;

    @Subscribe
    protected void onInit(InitEvent event) {
        List<String> strings = Arrays.asList("John", "Andy", "Dora", "Martin", "Peter", "George");
        stringSuggestionField.setSearchExecutor((searchString, searchParams) ->
                strings.stream()
                        .filter(str -> StringUtils.containsIgnoreCase(str, searchString))
                        .collect(Collectors.toList()));

        List<SendingStatus> enums = Arrays.asList(SendingStatus.values());
        enumSuggestionField.setSearchExecutor((searchString, searchParams) ->
                enums.stream()
                        .filter(status -> StringUtils.containsIgnoreCase(messages.getMessage(status), searchString))
                        .collect(Collectors.toList()));
    }
}
