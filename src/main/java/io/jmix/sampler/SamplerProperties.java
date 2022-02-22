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

package io.jmix.sampler;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.boot.context.properties.bind.DefaultValue;

@ConstructorBinding
@ConfigurationProperties(prefix = "jmix.sampler")
public class SamplerProperties {

    GoogleAnalyticsTracker googleAnalyticsTracker;
    String googleApiKey;

    public SamplerProperties(@DefaultValue GoogleAnalyticsTracker googleAnalyticsTracker,
                             String googleApiKey) {
        this.googleAnalyticsTracker = googleAnalyticsTracker;
        this.googleApiKey = googleApiKey;
    }

    public GoogleAnalyticsTracker getGoogleAnalyticsTracker() {
        return googleAnalyticsTracker;
    }

    public String getGoogleApiKey() {
        return googleApiKey;
    }

    public static class GoogleAnalyticsTracker {

        String id;
        String domainName;
        Boolean enabled;

        public GoogleAnalyticsTracker(
                String id,
                @DefaultValue("none") String domainName,
                @DefaultValue("false") Boolean enabled) {
            this.id = id;
            this.domainName = domainName;
            this.enabled = enabled;
        }

        public String getId() {
            return id;
        }

        public String getDomainName() {
            return domainName;
        }

        public Boolean getEnabled() {
            return enabled;
        }
    }
}
