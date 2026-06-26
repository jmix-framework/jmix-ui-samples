package io.jmix.uisamples.view.flowui.containers.tabsheet.badges;

import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.tabs.Tab;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.component.tabsheet.JmixTabSheet;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

@ViewController("tabsheet-badges")
@ViewDescriptor("tabsheet-badges.xml")
public class TabSheetBadgesSample extends StandardView {

    @ViewComponent
    protected JmixTabSheet tabSheet;

    @Autowired
    protected UiComponents uiComponents;

    @Subscribe
    protected void onInit(InitEvent event) {
        initTabSheet();
    }

    protected void initTabSheet() {
        tabSheet.add(createIssueTab(), new Span("Issue tab content"));
        tabSheet.add(createFeatureTab(), new Span("Feature tab content"));
        tabSheet.add(createBugTab(), new Span("Bug tab content"));
    }

    protected Tab createIssueTab() {
        Tab tab = uiComponents.create(Tab.class);
        tab.add(new Span("Issues"), createBadge(15, "contrast"));

        return tab;
    }

    protected Tab createFeatureTab() {
        Tab tab = uiComponents.create(Tab.class);
        tab.add(new Span("Features"), createBadge(132, "success"));

        return tab;
    }

    protected Tab createBugTab() {
        Tab tab = uiComponents.create(Tab.class);
        tab.add(new Span("Bugs"), createBadge(32, "error"));

        return tab;
    }

    protected Span createBadge(int value, String theme) {
        Span badge = new Span(String.valueOf(value));
        badge.getElement().getThemeList().add("badge small " + theme);
        badge.addClassName("tab-badge");
        return badge;
    }
}
