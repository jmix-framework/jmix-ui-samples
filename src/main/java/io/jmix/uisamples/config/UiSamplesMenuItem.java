/*
 * Copyright 2022 Haulmont.
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

package io.jmix.uisamples.config;

import io.jmix.flowui.menu.MenuItem;
import org.jspecify.annotations.Nullable;

import java.util.*;

public class UiSamplesMenuItem {

    protected final UiSamplesMenuItem parent;
    protected final List<UiSamplesMenuItem> children = new ArrayList<>();
    protected final String id;

    protected String url;
    protected String page;
    protected String anchor;
    protected boolean isNew;
    protected boolean isVaadinCommercial;
    protected boolean splitEnabled;
    protected boolean defaultFiles = true;
    protected List<String> otherFiles;
    protected List<MenuItem.MenuItemParameter> urlQueryParameters;
    protected Map<String, Object> viewParams;

    protected String overviewLocation;

    protected boolean isMenu = false;
    protected boolean isOverview = false;

    public UiSamplesMenuItem(@Nullable UiSamplesMenuItem parent, String id) {
        this.parent = parent;
        this.id = id;
    }

    @Nullable
    public UiSamplesMenuItem getParent() {
        return parent;
    }

    public String getId() {
        return id;
    }

    public boolean isMenu() {
        return isMenu;
    }

    public void setMenu(boolean isMenu) {
        this.isMenu = isMenu;
    }

    public boolean isOverview() {
        return isOverview;
    }

    public void setOverview(boolean isOverview) {
        this.isOverview = isOverview;
    }

    @Nullable
    public String getOverviewLocation() {
        return overviewLocation;
    }

    public void setOverviewLocation(String overviewLocation) {
        this.overviewLocation = overviewLocation;
    }

    public boolean isSplitEnabled() {
        return splitEnabled;
    }

    public void setSplitEnabled(boolean splitEnabled) {
        this.splitEnabled = splitEnabled;
    }

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean aNew) {
        isNew = aNew;
    }

    public boolean isVaadinCommercial() {
        return isVaadinCommercial;
    }

    public void setVaadinCommercial(boolean vaadinCommercial) {
        isVaadinCommercial = vaadinCommercial;
    }

    public boolean isDefaultFiles() {
        return defaultFiles;
    }

    public void setDefaultFiles(boolean defaultFiles) {
        this.defaultFiles = defaultFiles;
    }

    public List<String> getOtherFiles() {
        if (otherFiles == null)
            return Collections.emptyList();
        return otherFiles;
    }

    public void setOtherFiles(List<String> otherFiles) {
        this.otherFiles = otherFiles;
    }

    public List<MenuItem.MenuItemParameter> getUrlQueryParameters() {
        return Objects.requireNonNullElse(urlQueryParameters, Collections.emptyList());
    }

    public void setUrlQueryParameters(List<MenuItem.MenuItemParameter> urlQueryParameters) {
        this.urlQueryParameters = urlQueryParameters;
    }

    @Nullable
    public String getUrl() {
        UiSamplesMenuItem currentParent = parent;
        while (url == null && currentParent != null) {
            url = currentParent.getUrl();
            currentParent = currentParent.getParent();
        }

        return url;
    }

    public void setUrl(@Nullable String url) {
        this.url = url;
    }

    @Nullable
    public String getPage() {
        return page;
    }

    public void setPage(@Nullable String page) {
        this.page = page;
    }

    @Nullable
    public String getAnchor() {
        return anchor;
    }

    public void setAnchor(@Nullable String anchor) {
        this.anchor = anchor;
    }

    public List<UiSamplesMenuItem> getChildren() {
        return Collections.unmodifiableList(children);
    }

    public void addChild(UiSamplesMenuItem item) {
        children.add(item);
    }

    public Map<String, Object> getViewParams() {
        return viewParams != null
                ? Collections.unmodifiableMap(viewParams)
                : Collections.emptyMap();
    }

    public void setViewParams(Map<String, Object> viewParams) {
        this.viewParams = viewParams;
    }

    @Override
    public String toString() {
        return id;
    }
}
