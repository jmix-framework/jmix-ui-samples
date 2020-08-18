package io.jmix.sampler.screen.ui.containers.htmlboxlayout.template;

import io.jmix.ui.Notifications;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.HtmlBoxLayout;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("htmlboxlayout-template")
@UiDescriptor("htmlboxlayout-template.xml")
public class HtmlBoxLayoutTemplateSample extends ScreenFragment {

    @Autowired
    protected HtmlBoxLayout htmlSample;
    @Autowired
    protected Notifications notifications;

    @Subscribe
    protected void onInit(InitEvent event) {
        htmlSample.setTemplateContents(
                "<div class=\"box-container\">" +
                        "    <img src=\"../files/jmix-icon-login.svg\" class=\"logo\">" +
                        "    <div class=\"button-container\">" +
                        "        <div class=\"sample-button addons-button\" location=\"addons\"></div>" +
                        "        <div class=\"sample-button buy-button\" location=\"buy\"></div>" +
                        "        <div class=\"sample-button support-button\" location=\"support\"></div>" +
                        "    </div>" +
                        "</div>");
    }

    @Subscribe("okbutton")
    protected void onOkbuttonClick(Button.ClickEvent event) {
        click();
    }

    @Subscribe("addons")
    protected void onAddonsClick(Button.ClickEvent event) {
        click();
    }

    @Subscribe("buy")
    protected void onBuyClick(Button.ClickEvent event) {
        click();
    }

    @Subscribe("support")
    protected void onSupportClick(Button.ClickEvent event) {
        click();
    }

    public void click() {
        notifications.create()
                .withCaption("Button clicked")
                .show();
    }
}
