package io.jmix.sampler.screen.ui.components.image.theme;

import io.jmix.ui.component.Image;
import io.jmix.ui.component.ThemeResource;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("image-theme")
@UiDescriptor("image-theme.xml")
public class ImageThemeSample extends ScreenFragment {

    @Autowired
    protected Image programmaticImage;

    @Subscribe
    protected void onInit(InitEvent event) {
        programmaticImage.setSource(ThemeResource.class).setPath("files/jmix-icon-login.svg");
    }
}
