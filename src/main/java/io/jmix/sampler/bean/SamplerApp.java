package io.jmix.sampler.bean;

import com.vaadin.spring.annotation.VaadinSessionScope;
import io.jmix.ui.AppUI;
import io.jmix.ui.JmixApp;

import java.util.Locale;

@VaadinSessionScope
public class SamplerApp extends JmixApp {

    public void applyUserTheme(String appWindowTheme) {
        applyTheme(appWindowTheme);
    }

    @Override
    protected void initializeUi() {
        initializeLocale();
        super.initializeUi();
    }

    protected void initializeLocale() {
        AppUI currentUi = AppUI.getCurrent();
        if (currentUi != null) {
            Locale uiLocale = currentUi.getLocale();
            if (!coreProperties.getAvailableLocales().contains(uiLocale)) {
                uiLocale = messageTools.getDefaultLocale();
            }

            setLocale(uiLocale);
        }
    }
}
