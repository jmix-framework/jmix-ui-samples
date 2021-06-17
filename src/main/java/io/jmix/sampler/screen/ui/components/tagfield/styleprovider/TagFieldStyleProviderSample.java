package io.jmix.sampler.screen.ui.components.tagfield.styleprovider;

import io.jmix.sampler.entity.ProductTag;
import io.jmix.ui.screen.Install;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;

@UiController("tagfield-styleprovider")
@UiDescriptor("tagfield-styleprovider.xml")
public class TagFieldStyleProviderSample extends ScreenFragment {

    @Install(to = "tagField", subject = "tagStyleProvider")
    private String tagFieldTagStyleProvider(ProductTag tag) {
        return tag.getName().toLowerCase();
    }
}
