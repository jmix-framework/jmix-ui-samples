package io.jmix.sampler.screen.ui.components.image.url;

import io.jmix.ui.component.Image;
import io.jmix.ui.component.UrlResource;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.MalformedURLException;
import java.net.URL;

@UiController("image-url")
@UiDescriptor("image-url.xml")
public class ImageUrlSample extends ScreenFragment {

    private static final Logger log = LoggerFactory.getLogger(ImageUrlSample.class);

    @Autowired
    protected Image programmaticImage;

    @Subscribe
    protected void onInit(InitEvent event) {
        String address = "https://www.cuba-platform.com/sites/all/themes/cuba_adaptive/img/upper-header-logo.png";
        URL url = null;

        try {
            url = new URL(address);
        } catch (MalformedURLException e) {
            log.debug(e.getMessage());
        }

        programmaticImage.setSource(UrlResource.class).setUrl(url);
    }
}
