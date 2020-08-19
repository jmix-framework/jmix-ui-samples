package io.jmix.sampler.screen.ui.components.twincolumn.optionstyleprovider;

import io.jmix.sampler.entity.Customer;
import io.jmix.ui.screen.Install;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;

@UiController("twincolumn-option-style-provider")
@UiDescriptor("twincolumn-option-style-provider.xml")
public class TwinColumnOptionStyleProviderSample extends ScreenFragment {

    @Install(to = "twinColumn", subject = "optionStyleProvider")
    protected String twinColumnOptionStyleProvider(Customer customer, boolean selected) {
        switch (customer.getGrade()) {
            case PREMIUM:
                return "premium-grade";
            case HIGH:
                return "high-grade";
            case STANDARD:
                return "standard-grade";
            default:
                return null;
        }
    }
}