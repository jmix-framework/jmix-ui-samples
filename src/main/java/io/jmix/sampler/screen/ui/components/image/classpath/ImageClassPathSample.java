package io.jmix.sampler.screen.ui.components.image.classpath;

import io.jmix.ui.component.ClasspathResource;
import io.jmix.ui.component.Image;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("image-classpath")
@UiDescriptor("image-classpath.xml")
public class ImageClassPathSample extends ScreenFragment {

    @Autowired
    protected Image programmaticImage;

    @Subscribe
    protected void onInit(InitEvent event) {
        programmaticImage.setSource(ClasspathResource.class)
                .setPath("io/jmix/sampler/screen/ui/components/image/classpath/jmix-logo.svg");
    }
}
