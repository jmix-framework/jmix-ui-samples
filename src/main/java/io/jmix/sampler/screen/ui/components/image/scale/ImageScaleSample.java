package io.jmix.sampler.screen.ui.components.image.scale;

import io.jmix.ui.component.ComboBox;
import io.jmix.ui.component.HasValue;
import io.jmix.ui.component.Image;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedHashMap;
import java.util.Map;

@UiController("image-scale")
@UiDescriptor("image-scale.xml")
public class ImageScaleSample extends ScreenFragment {

    @Autowired
    protected ComboBox<Image.ScaleMode> scaleComboBox;

    @Autowired
    private Image image;

    @Subscribe
    protected void onInit(InitEvent event) {
        Map<String, Image.ScaleMode> modeMap = new LinkedHashMap<>();
        for (Image.ScaleMode mode : Image.ScaleMode.values()) {
            modeMap.put(mode.name(), mode);
        }
        scaleComboBox.setOptionsMap(modeMap);
        scaleComboBox.setValue(Image.ScaleMode.NONE);
    }

    @Subscribe("scaleComboBox")
    protected void onScaleComboBoxValueChange(HasValue.ValueChangeEvent<Image.ScaleMode> event) {
        if (event.getValue() != null) {
            image.setScaleMode(event.getValue());
        }
    }
}
