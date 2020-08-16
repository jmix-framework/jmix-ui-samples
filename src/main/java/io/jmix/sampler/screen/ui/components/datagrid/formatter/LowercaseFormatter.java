package io.jmix.sampler.screen.ui.components.datagrid.formatter;

import io.jmix.core.Messages;
import io.jmix.ui.component.formatter.Formatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;

@Component(LowercaseFormatter.NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class LowercaseFormatter implements Formatter<String> {

    public static final String NAME = "sampler_LowercaseFormatter";

    @Autowired
    protected Messages messages;

    @Nullable
    @Override
    public String apply(@Nullable String value) {
        return value != null
                ? value.toLowerCase()
                : null;
    }
}
