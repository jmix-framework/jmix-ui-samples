package io.jmix.sampler.screen.ui.components.twincolumn.simple;

import io.jmix.core.MetadataTools;
import io.jmix.sampler.entity.Customer;
import io.jmix.ui.Notifications;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.TwinColumn;
import io.jmix.ui.screen.LoadDataBeforeShow;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

@UiController("twincolumn-simple")
@UiDescriptor("twincolumn-simple.xml")
@LoadDataBeforeShow
public class TwinColumnSimpleSample extends ScreenFragment {

    @Autowired
    private TwinColumn<Customer> twinColumn;
    @Autowired
    private Notifications notifications;
    @Autowired
    private MetadataTools metadataTools;

    @Subscribe("button")
    protected void onButtonClick(Button.ClickEvent event) {
        StringBuilder sb = new StringBuilder();
        Collection<Customer> value = twinColumn.getValue();
        if (value == null) {
            sb.append("null");
        } else {
            for (Customer customer : value) {
                sb.append(metadataTools.getInstanceName(customer))
                        .append("\n");
            }
        }
        notifications.create()
                .withCaption(sb.toString())
                .show();
    }
}
