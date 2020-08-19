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

import io.jmix.core.CoreProperties;
import io.jmix.core.Messages;
import io.jmix.core.MetadataTools;
import io.jmix.core.entity.BaseUser;
import io.jmix.core.security.CoreAuthenticationProvider;
import io.jmix.core.security.impl.InMemoryUserRepository;
import io.jmix.core.security.impl.SystemAuthenticationProvider;
import io.jmix.sampler.bean.SamplerApp;
import io.jmix.sampler.bean.SamplerMessagesImpl;
import io.jmix.sampler.bean.SamplerMetadataTools;
import io.jmix.ui.App;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SamplerConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    protected InMemoryUserRepository userRepository;
    @Autowired
    protected CoreProperties coreProperties;

    @Bean(name = App.NAME)
    public App app() {
        return new SamplerApp();
    }

    @Bean(name = Messages.NAME)
    public Messages messages() {
        return new SamplerMessagesImpl();
    }

    @Bean(name = MetadataTools.NAME)
    public MetadataTools metadataTools() {
        return new SamplerMetadataTools();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(new SystemAuthenticationProvider(userRepository));

        CoreAuthenticationProvider userAuthenticationProvider = new CoreAuthenticationProvider();
        userAuthenticationProvider.setUserDetailsService(userRepository);
        auth.authenticationProvider(userAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.antMatcher("/**")
                .authorizeRequests().anyRequest().permitAll()
                .and()
                .anonymous(anonymousConfigurer -> {
                    BaseUser anonymousUser = userRepository.getAnonymousUser();
                    anonymousConfigurer.principal(anonymousUser);
                    anonymousConfigurer.key(coreProperties.getAnonymousAuthenticationTokenKey());
                })
                .csrf().disable()
                .headers().frameOptions().sameOrigin();
    }
}
