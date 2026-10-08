package io.jmix.uisamples.view.flowui.customcomponents.nativeselect;

import io.jmix.flowui.sys.registration.ComponentRegistration;
import io.jmix.flowui.sys.registration.ComponentRegistrationBuilder;
import io.jmix.uisamples.component.nativeselect.NativeSelect;
import io.jmix.uisamples.component.nativeselect.NativeSelectLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NativeSelectRegistration {

    @Bean
    public ComponentRegistration nativeSelect() {
        return ComponentRegistrationBuilder.create(NativeSelect.class)
                .withComponentLoader("nativeSelect", NativeSelectLoader.class)
                .build();
    }
}
