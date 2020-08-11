package io.jmix.sampler.screen.ui.components.combobox.optionsstyleprovider;

import io.jmix.core.Metadata;
import io.jmix.sampler.entity.Customer;
import io.jmix.sampler.entity.CustomerGrade;
import io.jmix.ui.model.InstanceContainer;
import io.jmix.ui.screen.Install;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("combobox-options-style-provider")
@UiDescriptor("combobox-options-style-provider.xml")
public class ComboBoxOptionsStyleProviderSample extends ScreenFragment {

    @Autowired
    protected Metadata metadata;

    @Autowired
    protected InstanceContainer<Customer> customerDc;

    @Subscribe
    protected void onInit(InitEvent event) {
        // InstanceContainer initialization. It is usually done automatically if the screen is
        // inherited from StandardEditor and is used as an entity editor.
        Customer order = metadata.create(Customer.class);
        customerDc.setItem(order);
    }

    @Install(to = "comboBox", subject = "optionStyleProvider")
    protected String comboBoxOptionStyleProvider(CustomerGrade grade) {
        if (grade != null) {
            switch (grade) {
                case HIGH:
                    return "high-grade";
                case PREMIUM:
                    return "premium-grade";
                case STANDARD:
                    return "standard-grade";
            }
        }
        return null;
    }
}
