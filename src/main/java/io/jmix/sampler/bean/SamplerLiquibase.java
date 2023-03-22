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
import liquibase.integration.spring.SpringResourceAccessor;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;

public class SamplerLiquibase extends SpringLiquibase {

    protected Map<String, SortedSet<String>> changelogPaths = new HashMap<>();

    @Override
    protected SpringResourceAccessor createResourceOpener() {
        return new SamplerResourceOpener(resourceLoader);
    }

    public class SamplerResourceOpener extends SpringResourceAccessor {

        public SamplerResourceOpener(ResourceLoader resourceLoader) {
            super(resourceLoader);
        }

        @Override
        public SortedSet<String> list(String relativeTo, String path, boolean includeFiles, boolean includeDirectories,
                                      boolean recursive) throws IOException {
            // Store paths to changelogs to use in session data source
            SortedSet<String> set = changelogPaths.get(path);
            if (set == null) {
                set = super.list(relativeTo, path, includeFiles, includeDirectories, recursive);
                changelogPaths.put(path, set);
            }
            return set;
        }
    }
}
