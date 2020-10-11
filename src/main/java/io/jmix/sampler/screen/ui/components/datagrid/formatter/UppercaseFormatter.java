package io.jmix.sampler.screen.ui.components.datagrid.formatter;

import io.jmix.ui.component.formatter.Formatter;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;

@Component("sampler_UppercaseFormatter")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class UppercaseFormatter implements Formatter<String> {

    @Nullable
    @Override
    public String apply(@Nullable String value) {
        return value != null
                ? value.toUpperCase()
                : null;
    }
}
