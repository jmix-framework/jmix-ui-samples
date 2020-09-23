package io.jmix.sampler.bean;

import com.vaadin.spring.annotation.VaadinSessionScope;
import io.jmix.ui.JmixApp;

@VaadinSessionScope
public class SamplerApp extends JmixApp {
    public void applyUserTheme(String appWindowTheme) {
        applyTheme(appWindowTheme);
    }
}
