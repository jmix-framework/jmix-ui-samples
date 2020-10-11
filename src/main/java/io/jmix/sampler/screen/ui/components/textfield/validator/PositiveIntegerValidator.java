package io.jmix.sampler.screen.ui.components.textfield.validator;

import io.jmix.core.Messages;
import io.jmix.ui.component.ValidationException;
import io.jmix.ui.component.validation.AbstractValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("sampler_PositiveIntegerValidator")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class PositiveIntegerValidator extends AbstractValidator<Integer> {

    @Autowired
    protected Messages messages;

    @Override
    public void accept(Integer value) throws ValidationException {
        if (value != null) {
            if (value <= 0) {
                throw new ValidationException(messages.getMessage("io.jmix.sampler.screen.ui.components.textfield.validator/integerTextField"));
            }
        }
    }
}
