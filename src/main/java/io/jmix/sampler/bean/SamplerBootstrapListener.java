package io.jmix.sampler.bean;

import com.vaadin.server.*;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

@Component("sampler_BootstrapListener")
public class SamplerBootstrapListener implements BootstrapListener {

    @Override
    public void modifyBootstrapFragment(BootstrapFragmentResponse response) {
        // do nothing
    }

    @Override
    public void modifyBootstrapPage(BootstrapPageResponse response) {
        Element head = response.getDocument().head();

        String themeName = getThemeName(response);
        String themeUri = getThemeUri(response, themeName);

        head.appendElement("link").attr("rel", "icon")
                .attr("type", "image/svg+xml")
                .attr("href", themeUri + "/favicon.svg");

        // Safari
        head.appendElement("link").attr("rel", "mask-icon")
                .attr("type", "image/svg+xml")
                .attr("href", themeUri + "/pinned-icon.svg")
                .attr("color", "#000000");
    }

    private String getThemeName(BootstrapPageResponse response) {
        UICreateEvent event = new UICreateEvent(response.getRequest(), response.getUiClass());
        return response.getUIProvider().getTheme(event);
    }

    private String getThemeUri(BootstrapPageResponse response, String themeName) {
        VaadinRequest request = response.getRequest();
        String staticFileLocation = request.getService().getStaticFileLocation(request);
        return staticFileLocation + "/" + VaadinServlet.THEME_DIR_PATH + "/" + themeName;
    }
}
