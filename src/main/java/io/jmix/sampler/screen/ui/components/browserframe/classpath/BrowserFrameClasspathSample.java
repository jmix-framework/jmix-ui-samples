package io.jmix.sampler.screen.ui.components.browserframe.classpath;

import io.jmix.ui.component.BrowserFrame;
import io.jmix.ui.component.ClasspathResource;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("browserframe-classpath")
@UiDescriptor("browserframe-classpath.xml")
public class BrowserFrameClasspathSample extends ScreenFragment {

    @Autowired
    protected BrowserFrame programmaticBrowserFrame;

    @Subscribe
    protected void onInit(InitEvent event) {
        programmaticBrowserFrame.setSource(ClasspathResource.class)
                .setPath("io/jmix/sampler/screen/ui/components/browserframe/classpath/browserframe-classpath.html");
    }
}
