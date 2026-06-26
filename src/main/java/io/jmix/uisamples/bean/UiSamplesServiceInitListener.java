package io.jmix.uisamples.bean;

import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinRequest;
import com.vaadin.flow.server.VaadinResponse;
import com.vaadin.flow.server.VaadinSession;
import io.jmix.core.CoreProperties;
import io.jmix.core.JmixModules;
import io.jmix.core.Resources;
import io.jmix.flowui.exception.UiExceptionHandlers;
import io.jmix.flowui.sys.JmixServiceInitListener;
import io.jmix.flowui.sys.LoginViewBeforeEnterHandler;
import io.jmix.flowui.view.ViewRegistry;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class UiSamplesServiceInitListener extends JmixServiceInitListener {

    private static final Logger log = LoggerFactory.getLogger(UiSamplesServiceInitListener.class);

    public UiSamplesServiceInitListener(ViewRegistry viewRegistry,
                                        UiExceptionHandlers uiExceptionHandlers,
                                        CoreProperties coreProperties,
                                        LoginViewBeforeEnterHandler loginViewBeforeEnterHandler,
                                        JmixModules modules,
                                        Resources resources) {
        super(viewRegistry, uiExceptionHandlers, coreProperties, loginViewBeforeEnterHandler, modules, resources);
    }

    @Override
    protected void initCookieLocale(VaadinSession session) {
        // do nothing
        // locale has been handled by io.jmix.uisamples.bean.SessionManager
    }

    @Override
    protected void initDefaultBrowserLocale(VaadinRequest request, VaadinSession session) {
        // do nothing
        // locale has been handled by io.jmix.uisamples.bean.SessionManager
    }

    @Override
    public void serviceInit(ServiceInitEvent event) {
        super.serviceInit(event);

        // health check handling
        event.addRequestHandler(this::handleRequest);
    }

    private boolean handleRequest(VaadinSession session, VaadinRequest request, VaadinResponse response) {
        if ("/healthcheck".equals(request.getPathInfo())) {

            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("text/html");
            try {
                response.getWriter().write("<h2>This is fine</h2>");
            } catch (IOException e) {
                log.error("Error writing health check response", e);
                return false;
            }

            return true;
        }

        return false;
    }
}
