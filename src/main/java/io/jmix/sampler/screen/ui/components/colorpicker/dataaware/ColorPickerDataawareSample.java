package io.jmix.sampler.screen.ui.components.colorpicker.dataaware;

import io.jmix.core.Metadata;
import io.jmix.sampler.entity.Color;
import io.jmix.ui.model.InstanceContainer;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("colorpicker-dataaware")
@UiDescriptor("colorpicker-dataaware.xml")
public class ColorPickerDataawareSample extends ScreenFragment {

    @Autowired
    protected InstanceContainer<Color> colorDc;
    @Autowired
    protected Metadata metadata;

    @Subscribe
    protected void onInit(InitEvent event) {
        // InstanceContainer initialization. It is usually done automatically if the screen is
        // inherited from StandardEditor and is used as an entity editor.
        Color color = metadata.create(Color.class);
        color.setHex("6F5CC3");
        colorDc.setItem(color);
    }
}