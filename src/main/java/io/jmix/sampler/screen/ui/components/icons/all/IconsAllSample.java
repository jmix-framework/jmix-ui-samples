package io.jmix.sampler.screen.ui.components.icons.all;

import io.jmix.ui.UiComponents;
import io.jmix.ui.component.Component;
import io.jmix.ui.component.CssLayout;
import io.jmix.ui.component.Label;
import io.jmix.ui.component.VBoxLayout;
import io.jmix.ui.icon.JmixIcon;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("icons-all")
@UiDescriptor("icons-all.xml")
public class IconsAllSample extends ScreenFragment {

    protected static final String TEXT_STYLE = "name-label";
    protected static final String SOURCE_TEXT_STYLE = "source-label";
    protected static final String ICON_STYLE = "icon-label";
    protected static final String VBOX_STYLE = "vbox-icon";

    @Autowired
    protected CssLayout cssLayout;

    @Autowired
    protected UiComponents uiComponents;

    @Subscribe
    protected void onInit(InitEvent event) {
        for (JmixIcon icon : JmixIcon.values()) {
            if (icon.source().isEmpty()) {
                continue;
            }

            cssLayout.add(createIconInfo(icon));
        }
    }

    protected VBoxLayout createIconInfo(JmixIcon icon) {
        VBoxLayout vbox = uiComponents.create(VBoxLayout.class);
        vbox.setAlignment(Component.Alignment.MIDDLE_CENTER);
        vbox.setWidth("210px");
        vbox.setStyleName(VBOX_STYLE);

        Label iconLabel = uiComponents.create(Label.class);
        iconLabel.setIconFromSet(icon);
        iconLabel.setAlignment(Component.Alignment.MIDDLE_CENTER);
        iconLabel.addStyleName("h1");
        iconLabel.addStyleName(ICON_STYLE);

        Label<String> nameLabel = uiComponents.create(Label.TYPE_STRING);
        nameLabel.setAlignment(Component.Alignment.MIDDLE_CENTER);
        nameLabel.setStyleName(TEXT_STYLE);
        nameLabel.setValue(icon.name());

        Label<String> sourceLabel = uiComponents.create(Label.TYPE_STRING);
        sourceLabel.setAlignment(Component.Alignment.MIDDLE_CENTER);
        sourceLabel.setStyleName(SOURCE_TEXT_STYLE);
        sourceLabel.setValue(icon.source());

        vbox.add(iconLabel);
        vbox.add(nameLabel);
        vbox.add(sourceLabel);

        return vbox;
    }
}
