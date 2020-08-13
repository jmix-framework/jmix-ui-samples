package io.jmix.sampler.screen.ui.components.suggestionfield.optionsstyleprovider;

import io.jmix.core.Metadata;
import io.jmix.sampler.entity.Customer;
import io.jmix.sampler.entity.Order;
import io.jmix.ui.model.InstanceContainer;
import io.jmix.ui.screen.Install;
import io.jmix.ui.screen.LoadDataBeforeShow;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("suggestionfield-options-style-provider")
@UiDescriptor("suggestionfield-options-style-provider.xml")
@LoadDataBeforeShow
public class SuggestionFieldOptionsStyleProviderSample extends ScreenFragment {

    @Autowired
    protected InstanceContainer<Order> orderDc;
    @Autowired
    protected Metadata metadata;

    @Subscribe
    protected void onInit(InitEvent event) {
        // InstanceContainer initialization. It is usually done automatically if the screen is
        // inherited from StandardEditor and is used as an entity editor.
        Order order = metadata.create(Order.class);
        orderDc.setItem(order);
    }

    @Install(to = "suggestionField", subject = "optionStyleProvider")
    protected String suggestionFieldOptionStyleProvider(Customer customer) {
        switch (customer.getGrade()) {
            case HIGH:
                return "high-grade";
            case PREMIUM:
                return "premium-grade";
            default:
                return "standard-grade";
        }
    }
}
