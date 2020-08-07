package io.jmix.sampler.screen.ui.components.currencyfield.annotation;

import io.jmix.core.Metadata;
import io.jmix.sampler.entity.Product;
import io.jmix.ui.model.InstanceContainer;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("currencyfield-annotation")
@UiDescriptor("currencyfield-annotation.xml")
public class CurrencyFieldAnnotationSample extends ScreenFragment {

    @Autowired
    protected InstanceContainer<Product> productDc;

    @Autowired
    protected Metadata metadata;

    @Subscribe
    public void onInit(InitEvent event) {
        // InstanceContainer initialization. It is usually done automatically if the screen is
        // inherited from StandardEditor and is used as an entity editor.
        Product product = metadata.create(Product.class);
        productDc.setItem(product);
    }
}
