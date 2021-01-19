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

package io.jmix.sampler.bean;

import liquibase.integration.spring.SpringLiquibase;
import liquibase.resource.ResourceAccessor;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SamplerLiquibase extends SpringLiquibase {

    protected Map<String, Set<String>> changelogPaths = new HashMap<>();

    @Override
    protected ResourceAccessor createResourceOpener() {
        return new SamplerResourceOpener(getChangeLog());
    }

    public class SamplerResourceOpener extends SpringResourceOpener {

        public SamplerResourceOpener(String parentFile) {
            super(parentFile);
        }

        @Override
        public Set<String> list(String relativeTo, String path, boolean includeFiles, boolean includeDirectories,
                                boolean recursive) throws IOException {
            // Store paths to changelogs to use in session data source
            Set<String> list = changelogPaths.get(path);
            if (list == null) {
                list = super.list(relativeTo, path, includeFiles, includeDirectories, recursive);
                changelogPaths.put(path, list);
            }
            return list;
        }
    }
}
