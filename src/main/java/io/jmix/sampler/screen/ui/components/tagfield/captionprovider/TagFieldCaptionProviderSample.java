package io.jmix.sampler.screen.ui.components.tagfield.captionprovider;

import io.jmix.sampler.entity.ProductTag;
import io.jmix.ui.screen.Install;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;

@UiController("tagfield-captionprovider")
@UiDescriptor("tagfield-captionprovider.xml")
public class TagFieldCaptionProviderSample extends ScreenFragment {

    @Install(to = "tagField", subject = "tagCaptionProvider")
    private String tagFieldTagCaptionProvider(ProductTag tag) {
        return "#" + tag.getName();
    }
}
