package io.jmix.sampler.screen.ui.components.entitysuggestionfield.dataaware;

import io.jmix.core.Metadata;
import io.jmix.core.MetadataTools;
import io.jmix.sampler.entity.Customer;
import io.jmix.sampler.entity.Order;
import io.jmix.ui.component.EntitySuggestionField;
import io.jmix.ui.component.HasValue;
import io.jmix.ui.component.TextField;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.model.CollectionLoader;
import io.jmix.ui.model.InstanceContainer;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@UiController("entitysuggestionfield-dataaware")
@UiDescriptor("entitysuggestionfield-dataaware.xml")
public class EntitySuggestionFieldDataawareSample extends ScreenFragment {

    @Autowired
    protected EntitySuggestionField<Customer> entitySuggestionField;

    @Autowired
    protected TextField<Integer> delayTextField;
    @Autowired
    protected TextField<Integer> stringLengthTextField;
    @Autowired
    protected TextField<Integer> limitTextField;

    @Autowired
    protected InstanceContainer<Order> orderDc;
    @Autowired
    protected CollectionContainer<Customer> customersDc;
    @Autowired
    protected CollectionLoader<Customer> customersLoader;

    @Autowired
    protected Metadata metadata;
    @Autowired
    protected MetadataTools metadataTools;

    @Subscribe
    protected void onInit(InitEvent event) {
        // InstanceContainer initialization. It is usually done automatically if the screen is
        // inherited from StandardEditor and is used as an entity editor.
        Order order = metadata.create(Order.class);
        orderDc.setItem(order);

        customersLoader.load();
        List<Customer> customers = new ArrayList<>(customersDc.getItems());
        entitySuggestionField.setSearchExecutor((searchString, searchParams) ->
                customers.stream()
                        .filter(customer ->
                                StringUtils.containsIgnoreCase(metadataTools.getInstanceName(customer), searchString))
                        .collect(Collectors.toList()));

        delayTextField.setValue(300);
        stringLengthTextField.setValue(2);
        limitTextField.setValue(10);
    }

    @Subscribe("delayTextField")
    protected void onDelayTextFieldValueChange(HasValue.ValueChangeEvent<Integer> event) {
        int delay = event.getValue() != null ? event.getValue() : 0;
        entitySuggestionField.setAsyncSearchDelayMs(delay);
    }

    @Subscribe("stringLengthTextField")
    protected void onStringLengthTextFieldValueChange(HasValue.ValueChangeEvent<Integer> event) {
        int length = event.getValue() != null ? event.getValue() : 0;
        entitySuggestionField.setMinSearchStringLength(length);
    }

    @Subscribe("limitTextField")
    protected void onLimitTextFieldValueChange(HasValue.ValueChangeEvent<Integer> event) {
        int limit = event.getValue() != null ? event.getValue() : 0;
        entitySuggestionField.setSuggestionsLimit(limit);
    }
}
