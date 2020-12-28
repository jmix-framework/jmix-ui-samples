package io.jmix.sampler.bean;

import com.vaadin.spring.annotation.VaadinSessionScope;
import io.jmix.core.Messages;
import io.jmix.ui.AppUI;
import io.jmix.ui.JmixApp;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Locale;

@VaadinSessionScope
public class SamplerApp extends JmixApp {

    @Autowired
    protected Messages messages;

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
            if (!coreProperties.getAvailableLocales().containsValue(uiLocale)) {
                Locale defaultLocale = messageTools.getDefaultLocale();
                setLocale(defaultLocale);
                uiLocale = defaultLocale;
            }

            if (messages instanceof SamplerMessagesImpl) {
                ((SamplerMessagesImpl) messages).setUserLocale(uiLocale);
            }
        }
    }
}
