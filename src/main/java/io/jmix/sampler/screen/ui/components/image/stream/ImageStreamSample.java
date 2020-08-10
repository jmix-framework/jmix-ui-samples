package io.jmix.sampler.screen.ui.components.image.stream;

import io.jmix.core.Resources;
import io.jmix.ui.component.Image;
import io.jmix.ui.component.StreamResource;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("image-stream")
@UiDescriptor("image-stream.xml")
public class ImageStreamSample extends ScreenFragment {

    @Autowired
    protected Image image;

    @Autowired
    protected Resources resources;

    @Subscribe
    protected void onInit(InitEvent event) {
        image.setSource(StreamResource.class).
                setStreamSupplier(() ->
                        resources.getResourceAsStream("io/jmix/sampler/screen/ui/components/image/classpath/jmix-icon-login.svg"));
    }
}
