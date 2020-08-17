package io.jmix.sampler.screen.ui.containers.buttonspanel.visible;

import io.jmix.ui.screen.LoadDataBeforeShow;
import io.jmix.ui.screen.LookupComponent;
import io.jmix.ui.screen.StandardLookup;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;

@UiController("buttonspanel-visible")
@UiDescriptor("buttonspanel-visible.xml")
@LookupComponent("customerTable")
@LoadDataBeforeShow
public class ButtonsPanelVisible extends StandardLookup {
}
