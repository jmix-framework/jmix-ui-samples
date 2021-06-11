/*
 * Copyright 2021 Haulmont.
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

package io.jmix.sampler.bean;

import com.vaadin.ui.UI;
import io.jmix.ui.navigation.NavigationState;
import io.jmix.ui.navigation.UrlTools;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.googleanalytics.tracking.GoogleAnalyticsTracker;

import java.util.Map;

public class SamplerUrlTools extends UrlTools {

    private static final Logger log = LoggerFactory.getLogger(SamplerUrlTools.class);

    private static final String SAMPLE_ROUTE = "sample";

    @Override
    public void pushState(String navigationState, UI ui) {
        super.pushState(navigationState, ui);

        trackPageView(navigationState);
    }

    @Override
    public void replaceState(String navigationState, UI ui) {
        super.replaceState(navigationState, ui);

        trackPageView(navigationState);
    }

    private void trackPageView(String navigationState) {
        GoogleAnalyticsTracker tracker = SamplerAppUI.getCurrent().getGoogleAnalyticsTracker();
        if (tracker == null) {
            return;
        }

        NavigationState state = parseState(navigationState);

        if (SAMPLE_ROUTE.equals(state.getNestedRoute())
                && MapUtils.isEmpty(state.getParams())) {
            return;
        }
        String pageId = asRoute(state);
        log.debug("trackPageview with pageId: {}", pageId);
        tracker.trackPageview(pageId);
    }

    private String asRoute(NavigationState state) {
        StringBuilder route = new StringBuilder();

        if (StringUtils.isNotEmpty(state.getRoot())) {
            route.append(state.getRoot());
        }

        if (StringUtils.isNotEmpty(state.getNestedRoute())) {
            route.append('/').append(state.getNestedRoute());
        }

        Map<String, String> params = state.getParams();
        if (MapUtils.isNotEmpty(params)) {
            if (SAMPLE_ROUTE.equals(state.getNestedRoute())) {
                if (params.containsKey("id")) {
                    route.append("/").append(params.get("id"));
                }
            } else {
                route.append("?").append(state.getParamsString());
            }
        }

        return route.toString();
    }
}
