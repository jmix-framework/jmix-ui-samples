package io.jmix.sampler.screen.ui.components.entitysuggestionfield.optionsstyleprovider;

import io.jmix.core.DataManager;
import io.jmix.core.Metadata;
import io.jmix.core.QueryUtils;
import io.jmix.core.common.util.ParamsMap;
import io.jmix.core.querycondition.JpqlCondition;
import io.jmix.sampler.entity.Customer;
import io.jmix.sampler.entity.Order;
import io.jmix.ui.model.InstanceContainer;
import io.jmix.ui.screen.Install;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@UiController("entitysuggestionfield-options-style-provider")
@UiDescriptor("entitysuggestionfield-options-style-provider.xml")
public class EntitySuggestionFieldStyleProviderSample extends ScreenFragment {

    @Autowired
    protected Metadata metadata;
    @Autowired
    protected DataManager dataManager;

    @Autowired
    protected InstanceContainer<Order> orderDc;

    @Subscribe
    protected void onInit(InitEvent event) {
        // InstanceContainer initialization. It is usually done automatically if the screen is
        // inherited from StandardEditor and is used as an entity editor.
        Order order = metadata.create(Order.class);
        orderDc.setItem(order);
    }

    @Install(to = "entitySuggestionField", subject = "searchExecutor")
    protected List<Customer> entitySuggestionFieldSearchExecutor(String searchString, Map<String, Object> searchParams) {
        searchString = QueryUtils.escapeForLike(searchString);
        return dataManager.load(Customer.class)
                .condition(JpqlCondition.createWithParameters("e.name like :name order by e.name escape '\\'",
                        null, ParamsMap.of("name", "%" + searchString + "%")))
                .list();
    }

    @Install(to = "entitySuggestionField", subject = "optionStyleProvider")
    protected String entitySuggestionFieldOptionStyleProvider(Customer customer) {
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
