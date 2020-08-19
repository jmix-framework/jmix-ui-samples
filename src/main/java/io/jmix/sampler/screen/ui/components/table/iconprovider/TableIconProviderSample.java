package io.jmix.sampler.screen.ui.components.table.iconprovider;

import io.jmix.sampler.entity.Customer;
import io.jmix.ui.icon.JmixIcon;
import io.jmix.ui.screen.Install;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;

@UiController("table-icon-provider")
@UiDescriptor("table-icon-provider.xml")
public class TableIconProviderSample extends ScreenFragment {

    @Install(to = "customerTable", subject = "iconProvider")
    protected String customerTableIconProvider(Customer customer) {
        return customer.getActive() ? JmixIcon.OK.source() : JmixIcon.CANCEL.source();
    }
}