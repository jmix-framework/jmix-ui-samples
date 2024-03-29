/*
 * Copyright 2020 Haulmont.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.jmix.sampler.screen.sys.browser;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import io.jmix.core.CoreProperties;
import io.jmix.core.Messages;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.sampler.config.SamplerMenuConfig;
import io.jmix.sampler.config.SamplerMenuItem;
import io.jmix.sampler.screen.sys.main.MainScreen;
import io.jmix.sampler.util.SamplerHelper;
import io.jmix.ui.*;
import io.jmix.ui.component.*;
import io.jmix.ui.navigation.Route;
import io.jmix.ui.navigation.UrlIdSerializer;
import io.jmix.ui.navigation.UrlParamsChangedEvent;
import io.jmix.ui.navigation.UrlRouting;
import io.jmix.ui.screen.MapScreenOptions;
import io.jmix.ui.screen.MessageBundle;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Route("sample")
@UiController("sample-browser")
@UiDescriptor("sample-browser.xml")
public class SampleBrowser extends Screen {

    protected static final String DESCRIPTION_BOX_STYLE = "description-box";
    protected static final String DOC_URL_MESSAGES_KEY = "docUrl";

    @Autowired
    protected UiComponents uiComponents;
    @Autowired
    protected SamplerHelper samplerHelper;
    @Autowired
    protected SamplerMenuConfig menuConfig;
    @Autowired
    protected Messages messages;
    @Autowired
    protected MessageBundle messageBundle;
    @Autowired
    protected CoreProperties coreProperties;
    @Autowired
    protected Fragments fragments;
    @Autowired
    protected UrlRouting urlRouting;
    @Autowired
    protected WindowConfig windowConfig;
    @Autowired
    protected Screens screens;
    @Autowired
    protected CurrentAuthentication currentAuthentication;

    protected String sampleId;
    protected TabSheet tabSheet;

    @Subscribe
    protected void onInit(InitEvent event) {
        if (!(event.getOptions() instanceof MapScreenOptions)) {
            return;
        }

        MapScreenOptions options = (MapScreenOptions) event.getOptions();
        Map<String, Object> params = options.getParams();

        sampleId = (String) params.get("windowId");
        updateSample(sampleId);
    }

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        if (sampleId == null) {
            return;
        }

        String serializedSampleId = UrlIdSerializer.serializeId(sampleId);
        urlRouting.replaceState(this, ImmutableMap.of("id", serializedSampleId));
    }

    @Subscribe
    protected void onUrlParamsChanged(UrlParamsChangedEvent event) {
        String serializedSampleId = event.getParams().get("id");
        sampleId = (String) UrlIdSerializer.deserializeId(String.class, serializedSampleId);
        updateSample(sampleId);

        Screens.OpenedScreens openedScreens = screens.getOpenedScreens();
        Screen rootScreen = openedScreens.getRootScreen();
        if (rootScreen instanceof MainScreen) {
            ((MainScreen) rootScreen).expandItemsFromDirectLink(sampleId);
        }
    }

    protected void updateSample(String sampleId) {
        SamplerMenuItem item = menuConfig.getItemById(sampleId);

        ScreenFragment screenFragment = fragments
                .create(this, sampleId, new MapScreenOptions(item.getScreenParams()))
                .init();
        Fragment fragment = screenFragment.getFragment();

        updateLayout(fragment, item);
        updateCaption(sampleId, item);
        updateTabs(sampleId, item);
        focusFirstPossibleComponent(fragment);
    }

    protected void updateLayout(Fragment fragment, SamplerMenuItem item) {
        getWindow().removeAll();

        TabSheet tabSheet = createTabSheet();

        if (item.isSplitEnabled()) {
            SplitPanel split = uiComponents.create(SplitPanel.class);
            split.setOrientation(SplitPanel.ORIENTATION_VERTICAL);
            split.setWidth("100%");
            split.setHeight("100%");

            ComponentContainer vBox = createContainer(false, false, true, false);
            vBox.add(fragment);

            split.add(vBox);
            split.add(tabSheet);

            fragment.setHeight("100%");

            getWindow().add(split);
        } else {
            getWindow().add(fragment);
            getWindow().add(createSpacer());
            getWindow().add(tabSheet);
            getWindow().expand(tabSheet);
        }
    }

    protected TabSheet createTabSheet() {
        tabSheet = uiComponents.create(TabSheet.NAME);
        tabSheet.setId("tabSheet");
        tabSheet.setHeightFull();
        return tabSheet;
    }

    protected Component createSpacer() {
        Component spacer = uiComponents.create(Label.TYPE_STRING);
        spacer.setId("spacer");
        spacer.setHeight("10px");
        return spacer;
    }

    protected void updateCaption(String id, SamplerMenuItem item) {
        String caption = menuConfig.getMenuItemCaption(item.getId());
        if (Strings.isNullOrEmpty(caption)) {
            caption = id;
        }
        getWindow().setCaption(caption);
    }

    protected void updateTabs(String sampleId, SamplerMenuItem item) {
        tabSheet.removeAllTabs();

        WindowInfo info = windowConfig.getWindowInfo(item.getId());

        Package descriptionPackage = info.getControllerClass().getPackage();
        if (descriptionPackage != null) {
            addTab(messageBundle.getMessage("description"),
                    createDescription(descriptionPackage.getName(),
                            item.getUrl(), item.getPage(), item.getAnchor(), sampleId));
        }

        String screenSrc = info.getTemplate();
        addSourceTab(screenSrc);

        if (info.getControllerClassName() != null) {
            addSourceTab(getControllerFileName(info.getControllerClassName()));
        }

        List<String> otherFiles = item.getOtherFiles();
        if (CollectionUtils.isNotEmpty(otherFiles)) {
            otherFiles.forEach(this::addSourceTab);
        }

        String messagesPack = samplerHelper.findMessagePack(info);
        if (StringUtils.isNotEmpty(messagesPack)) {
            createMessagesContainers(messagesPack);
        }
    }

    protected Component createDescription(String descriptionsPack,
                                          @Nullable String url,
                                          @Nullable String page,
                                          @Nullable String anchor,
                                          String sampleId) {
        ScrollBoxLayout scrollBoxLayout = uiComponents.create(ScrollBoxLayout.class);
        scrollBoxLayout.addStyleName(DESCRIPTION_BOX_STYLE);
        scrollBoxLayout.setWidth("100%");
        scrollBoxLayout.setHeight("100%");
        scrollBoxLayout.setSpacing(true);

        scrollBoxLayout.add(descriptionText(sampleId, descriptionsPack));

        HBoxLayout hbox = uiComponents.create(HBoxLayout.class);
        hbox.setWidth("100%");

        if (!Strings.isNullOrEmpty(url)
                && !Strings.isNullOrEmpty(page)) {
            Component docLink = documentLink(url, page, anchor);
            hbox.add(docLink);
        }

        hbox.add(permalink(sampleId));
        scrollBoxLayout.add(hbox);
        return scrollBoxLayout;
    }

    protected Component descriptionText(String frameId, String descriptionsPack) {
        StringBuilder sb = new StringBuilder();
        String text = samplerHelper.getFileContent(getDescriptionFileName(descriptionsPack, frameId));
        if (!Strings.isNullOrEmpty(text)) {
            sb.append(text);
            sb.append("<hr>");
        }
        Label<String> doc = uiComponents.create(Label.TYPE_STRING);
        doc.setHtmlEnabled(true);
        doc.setHtmlSanitizerEnabled(false);
        doc.setWidth("100%");
        doc.setValue(sb.toString());
        return doc;
    }

    protected String getDescriptionFileName(String descriptionsPack, String frameId) {
        descriptionsPack = descriptionsPack.replaceAll("\\.", "/");
        StringBuilder sb = new StringBuilder(descriptionsPack);
        if (!descriptionsPack.endsWith("/")) {
            sb.append("/");
        }
        sb.append(frameId).append("-");
        sb.append(getUserLocale().toLanguageTag());
        sb.append(".html");
        return sb.toString();
    }

    protected Component documentLink(String url, String page, @Nullable String anchor) {
        String baseUrl = messages.getMessage(DOC_URL_MESSAGES_KEY);

        StringBuilder docUrl = new StringBuilder(baseUrl);
        docUrl.append(url).append("/").append(page).append(".html");
        if (!Strings.isNullOrEmpty(anchor)) {
            docUrl.append("#").append(anchor);
        }

        Link docLink = uiComponents.create(Link.class);
        docLink.setUrl(docUrl.toString());
        docLink.setCaption(messages.getMessage(getClass(), "documentation"));
        docLink.setTarget("_blank");
        return docLink;
    }

    protected PopupView permalink(String fragmentId) {
        PopupView permalink = uiComponents.create(PopupView.class);
        permalink.setAlignment(Component.Alignment.TOP_RIGHT);
        permalink.setHideOnMouseOut(false);
        permalink.setDescription(messages.getMessage(SampleBrowser.class, "permalink.description"));
        permalink.setStyleName("external-link");

        TextField<String> content = uiComponents.create(TextField.TYPE_STRING);
        String value = urlRouting.getRouteGenerator().getRoute(SampleBrowser.class, ImmutableMap.of("id", fragmentId));
        content.setValue(value);
        content.setWidth((value.length() * 8) + "px");
        content.setEditable(false);
        content.selectAll();
        permalink.setPopupContent(content);

        permalink.addPopupVisibilityListener(event -> {
            if (event.isPopupVisible()) {
                content.focus();
            }
        });

        return permalink;
    }

    protected String getControllerFileName(String controllerName) {
        return controllerName.replaceAll("\\.", "/") + ".java";
    }

    protected void focusFirstPossibleComponent(Fragment fragment) {
        fragment.getComponents().stream()
                .filter(component -> component instanceof Component.Focusable)
                .findFirst()
                .ifPresent(component -> ((Component.Focusable) component).focus());
    }

    protected ComponentContainer createTabContainer() {
        return createContainer(true, false, false, false);
    }

    protected ComponentContainer createContainer(boolean topEnable, boolean rightEnable,
                                                 boolean bottomEnable, boolean leftEnable) {
        VBoxLayout vBox = uiComponents.create(VBoxLayout.class);
        vBox.setMargin(topEnable, rightEnable, bottomEnable, leftEnable);
        vBox.setHeight("100%");

        return vBox;
    }

    protected void createMessagesContainers(String messagesPack) {
        for (Locale locale : coreProperties.getAvailableLocales()) {
            String tabTitle = String.format("messages_%s.properties", locale.toString());
            String src = samplerHelper.packageToPath(messagesPack) + "/" + tabTitle;
            String content = samplerHelper.getFileContent(src);
            if (StringUtils.isNotBlank(content)) {
                SourceCodeEditor sourceCodeEditor = createSourceCodeEditor(getAceMode(src));
                sourceCodeEditor.setValue(content);
                addTab(tabTitle, sourceCodeEditor);
            }
        }
    }

    protected void addTab(String name, Component component) {
        ComponentContainer container = createTabContainer();
        container.add(component);
        TabSheet.Tab tab = tabSheet.addTab(name, container);
        tab.setCaption(name);
    }

    protected void addSourceTab(String src) {
        if (!Strings.isNullOrEmpty(src)) {
            SourceCodeEditor sourceCodeEditor = createSourceCodeEditor(getAceMode(src));
            sourceCodeEditor.setValue(samplerHelper.getFileContent(src));
            addTab(samplerHelper.getFileName(src), sourceCodeEditor);
        }
    }

    protected SourceCodeEditor createSourceCodeEditor(SourceCodeEditor.Mode mode) {
        SourceCodeEditor editor = uiComponents.create(SourceCodeEditor.class);
        editor.setStyleName("sampler-browser");
        editor.setShowPrintMargin(false);
        editor.setMode(mode);
        editor.setEditable(false);
        editor.setWidth("100%");
        editor.setHeight("100%");

        return editor;
    }

    protected SourceCodeEditor.Mode getAceMode(String src) {
        String fileExtension = samplerHelper.getFileExtension(src);

        SourceCodeEditor.Mode mode = SourceCodeEditor.Mode.Text;
        if (fileExtension != null) {
            switch (fileExtension) {
                case "xsd":
                case "xml":
                    mode = SourceCodeEditor.Mode.XML;
                    break;
                case "java":
                    mode = SourceCodeEditor.Mode.Java;
                    break;
                case "js":
                    mode = SourceCodeEditor.Mode.JavaScript;
                    break;
                case "properties":
                    mode = SourceCodeEditor.Mode.Properties;
                    break;
                case "css":
                    mode = SourceCodeEditor.Mode.CSS;
                    break;
                case "scss":
                    mode = SourceCodeEditor.Mode.SCSS;
                    break;
            }
        }

        return mode;
    }

    protected Locale getUserLocale() {
        return App.getInstance().getLocale();
    }
}