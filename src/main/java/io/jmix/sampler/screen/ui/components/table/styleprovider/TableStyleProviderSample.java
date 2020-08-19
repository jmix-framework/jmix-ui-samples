package io.jmix.sampler.screen.ui.components.table.styleprovider;

import io.jmix.sampler.entity.Customer;
import io.jmix.ui.screen.Install;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;

@UiController("table-style-provider")
@UiDescriptor("table-style-provider.xml")
public class TableStyleProviderSample extends ScreenFragment {

    @Install(to = "customerTable", subject = "styleProvider")
    protected String customerTableStyleProvider(Customer customer, String property) {
        if (property == null) {
            if (Boolean.TRUE.equals(customer.getActive())) {
                return "active-customer";
            }
        } else if (property.equals("grade")) {
            switch (customer.getGrade()) {
                case PREMIUM:
                    return "premium-grade";
                case HIGH:
                    return "high-grade";
                case STANDARD:
                    return "standard-grade";
            }
        }
        return null;
    }
}
