package io.jmix.sampler.screen.ui.components.combobox.iconprovider;

import com.vaadin.server.FontAwesome;
import io.jmix.ui.component.ComboBox;
import io.jmix.ui.screen.Install;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

@UiController("combobox-icon-provider")
@UiDescriptor("combobox-icon-provider.xml")
public class ComboBoxIconProviderSample extends ScreenFragment {

    @Autowired
    protected ComboBox<FontAwesome> comboBox;

    @Subscribe
    protected void onInit(InitEvent event) {
        Map<String, FontAwesome> map = new HashMap<>();
        map.put("Archive file", FontAwesome.FILE_ARCHIVE_O);
        map.put("PDF file", FontAwesome.FILE_PDF_O);
        map.put("TXT file", FontAwesome.FILE_TEXT_O);
        comboBox.setOptionsMap(map);
    }

    @Install(to = "comboBox", subject = "optionIconProvider")
    protected String comboBoxOptionIconProvider(FontAwesome icon) {
        return "font-icon:" + icon;
    }
}
