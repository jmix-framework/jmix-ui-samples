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

package io.jmix.sampler;

import io.jmix.core.Messages;
import io.jmix.core.MetadataTools;
import io.jmix.core.security.CoreSecurityConfiguration;
import io.jmix.sampler.bean.SamplerApp;
import io.jmix.sampler.bean.SamplerMessagesImpl;
import io.jmix.sampler.bean.SamplerMetadataTools;
import io.jmix.sampler.bean.SamplerRoutingDataSource;
import io.jmix.sampler.screen.ui.components.composite.component.StepperField;
import io.jmix.sampler.screen.ui.components.composite.component.StepperFieldLoader;
import io.jmix.ui.App;
import io.jmix.ui.sys.registration.ComponentRegistration;
import io.jmix.ui.sys.registration.ComponentRegistrationBuilder;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import javax.sql.DataSource;

@Configuration
@EnableScheduling
public class SamplerConfiguration {

    @Bean("sampler_App")
    @Primary
    public App app() {
        return new SamplerApp();
    }

    @Bean("sampler_Messages")
    @Primary
    public Messages messages() {
        return new SamplerMessagesImpl();
    }

    @Bean("sampler_MetadataTools")
    @Primary
    public MetadataTools metadataTools() {
        return new SamplerMetadataTools();
    }

    @EnableWebSecurity
    static class SamplerSecurityConfiguration extends CoreSecurityConfiguration {
    }

    @Bean
    public ComponentRegistration stepperField() {
        return ComponentRegistrationBuilder.create(StepperField.NAME)
                .withComponentClass(StepperField.class)
                .withComponentLoaderClass(StepperFieldLoader.class)
                .build();
    }

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "routing.datasource")
    public DataSource dataSource() {
        return new SamplerRoutingDataSource();
    }

    @Bean("sampler_SessionDataSource")
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    @ConfigurationProperties(prefix = "session.datasource")
    public DataSource sessionDataSource() {
        return new BasicDataSource();
    }
}
