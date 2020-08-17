package io.jmix.sampler.screen.ui.components.grouptable.styleprovider;

import io.jmix.sampler.entity.Customer;
import io.jmix.sampler.entity.CustomerGrade;
import io.jmix.ui.component.GroupTable;
import io.jmix.ui.gui.data.GroupInfo;
import io.jmix.ui.screen.LoadDataBeforeShow;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Nullable;

@UiController("grouptable-style-provider")
@UiDescriptor("grouptable-style-provider.xml")
@LoadDataBeforeShow
public class GroupTableStyleProviderSample extends ScreenFragment {

    @Autowired
    protected GroupTable<Customer> customerTable;

    @Subscribe
    public void onInit(InitEvent event) {
        customerTable.setStyleProvider(new GroupTable.GroupStyleProvider<Customer>() {
            @SuppressWarnings("unchecked")
            @Override
            public String getStyleName(GroupInfo info) {
                CustomerGrade grade = (CustomerGrade) info.getPropertyValue(info.getProperty());
                switch (grade) {
                    case PREMIUM:
                        return "premium-grade";
                    case HIGH:
                        return "high-grade";
                    case STANDARD:
                        return "standard-grade";
                }
                return null;
            }

            @Override
            public String getStyleName(Customer customer, @Nullable String property) {
                if (Boolean.TRUE.equals(customer.getActive())) {
                    return "active-customer";
                }
                return null;
            }
        });
    }
}
