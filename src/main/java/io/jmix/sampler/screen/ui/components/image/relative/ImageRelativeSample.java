package io.jmix.sampler.screen.ui.components.image.relative;

import io.jmix.ui.component.Image;
import io.jmix.ui.component.RelativePathResource;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("image-relative")
@UiDescriptor("image-relative.xml")
public class ImageRelativeSample extends ScreenFragment {

    @Autowired
    protected Image programmaticImage;

    @Subscribe
    protected void onInit(InitEvent event) {
        programmaticImage.setSource(RelativePathResource.class)
                .setPath("VAADIN/themes/demo-helium/files/jmix-icon-login.svg");
    }
}
