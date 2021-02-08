package io.jmix.sampler.screen.ui.components.suggestionfield.dataaware;

import io.jmix.core.Messages;
import io.jmix.core.Metadata;
import io.jmix.sampler.entity.Customer;
import io.jmix.sampler.entity.CustomerGrade;
import io.jmix.ui.App;
import io.jmix.ui.component.SuggestionField;
import io.jmix.ui.model.InstanceContainer;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@UiController("suggestionfield-dataaware")
@UiDescriptor("suggestionfield-dataaware.xml")
public class SuggestionFieldDataawareSample extends ScreenFragment {

    @Autowired
    protected InstanceContainer<Customer> customerDc;
    @Autowired
    protected Metadata metadata;
    @Autowired
    protected Messages messages;

    @Autowired
    protected SuggestionField<CustomerGrade> suggestionField;

    @Subscribe
    protected void onInit(InitEvent event) {
        initDataContainer();
        initSearchExecutor();
    }

    protected void initDataContainer() {
        // InstanceContainer initialization. It is usually done automatically if the screen is
        // inherited from StandardEditor and is used as an entity editor.
        Customer customer = metadata.create(Customer.class);
        customerDc.setItem(customer);
    }

    protected void initSearchExecutor() {
        List<CustomerGrade> grades = Arrays.asList(CustomerGrade.values());
        Locale userLocale = App.getInstance().getLocale();
        suggestionField.setSearchExecutor((searchString, searchParams) ->
                grades.stream()
                        .filter(customerGrade -> StringUtils.containsIgnoreCase(
                                messages.getMessage(customerGrade, userLocale), searchString))
                        .collect(Collectors.toList()));
    }
}
