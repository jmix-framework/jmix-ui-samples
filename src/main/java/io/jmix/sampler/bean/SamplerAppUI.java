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

import com.vaadin.server.VaadinRequest;
import io.jmix.sampler.SamplerProperties;
import io.jmix.ui.AppUI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.googleanalytics.tracking.GoogleAnalyticsTracker;

public class SamplerAppUI extends AppUI {

    private static Logger log = LoggerFactory.getLogger(SamplerAppUI.class);

    @Autowired
    private SamplerProperties samplerProperties;

    private GoogleAnalyticsTracker tracker;

    public static SamplerAppUI getCurrent() {
        return (SamplerAppUI) AppUI.getCurrent();
    }

    @Override
    protected void init(VaadinRequest request) {
        initGoogleAnalyticsTracker();

        super.init(request);
    }

    private void initGoogleAnalyticsTracker() {
        if (!samplerProperties.getGoogleAnalyticsTracker().getEnabled()) {
            log.info("GoogleAnalyticsTracker disabled");
            return;
        }

        String trackerId = samplerProperties.getGoogleAnalyticsTracker().getId();
        String domainName = samplerProperties.getGoogleAnalyticsTracker().getDomainName();
        tracker = new GoogleAnalyticsTracker(trackerId, domainName, "/sampler/");
        tracker.extend(this);

        log.info("GoogleAnalyticsTracker enabled. Tracker Id: {}, Domain Name: {}", trackerId, domainName);
    }

    public GoogleAnalyticsTracker getGoogleAnalyticsTracker() {
        return tracker;
    }
}
