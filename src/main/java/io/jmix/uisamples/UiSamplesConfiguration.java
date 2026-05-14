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

package io.jmix.uisamples;

import com.google.common.base.Strings;
import io.jmix.core.CoreProperties;
import io.jmix.core.JmixModules;
import io.jmix.core.Resources;
import io.jmix.core.security.CoreSecurityConfiguration;
import io.jmix.flowui.exception.UiExceptionHandlers;
import io.jmix.flowui.sys.JmixServiceInitListener;
import io.jmix.flowui.sys.LoginViewBeforeEnterHandler;
import io.jmix.flowui.view.ViewRegistry;
import io.jmix.uisamples.bean.UiSamplesRoutingDataSource;
import io.jmix.uisamples.bean.UiSamplesServiceInitListener;
import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import javax.sql.DataSource;

@Configuration
@EnableScheduling
public class UiSamplesConfiguration {

    @Autowired
    private Environment environment;

    @EnableWebSecurity
    static class UiSamplesSecurityConfiguration extends CoreSecurityConfiguration {
    }

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "routing.datasource")
    public DataSource dataSource() {
        return new UiSamplesRoutingDataSource();
    }

    @Bean("uisamples_SessionDataSource")
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    @ConfigurationProperties(prefix = "session.datasource")
    public DataSource sessionDataSource() {
        return new BasicDataSource();
    }

    @Primary
    @Bean("flowui_JmixServiceInitListener")
    public JmixServiceInitListener uiSamplesServiceInitListener(ViewRegistry viewRegistry,
                                                                UiExceptionHandlers uiExceptionHandlers,
                                                                CoreProperties coreProperties,
                                                                LoginViewBeforeEnterHandler loginViewBeforeEnterHandler,
                                                                JmixModules modules,
                                                                Resources resources) {
        return new UiSamplesServiceInitListener(viewRegistry, uiExceptionHandlers, coreProperties, loginViewBeforeEnterHandler, modules, resources);
    }

    @EventListener
    public void printApplicationUrl(ApplicationStartedEvent event) {
        LoggerFactory.getLogger(UiSamplesApplication.class).info("Application started at "
                + "http://localhost:"
                + environment.getProperty("local.server.port")
                + Strings.nullToEmpty(environment.getProperty("server.servlet.context-path")));
    }
}
