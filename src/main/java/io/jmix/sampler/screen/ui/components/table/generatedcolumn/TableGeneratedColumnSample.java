package io.jmix.sampler.screen.ui.components.table.generatedcolumn;

import io.jmix.core.CoreProperties;
import io.jmix.core.MessageTools;
import io.jmix.core.MetadataTools;
import io.jmix.sampler.entity.Customer;
import io.jmix.ui.UiComponents;
import io.jmix.ui.component.ComboBox;
import io.jmix.ui.component.Component;
import io.jmix.ui.component.Table;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@UiController("table-generated-column")
@UiDescriptor("table-generated-column.xml")
public class TableGeneratedColumnSample extends ScreenFragment {

    @Autowired
    protected Table<Customer> customerTable;
    @Autowired
    protected UiComponents uiComponents;
    @Autowired
    protected MetadataTools metadataTools;
    @Autowired
    private MessageTools messageTools;
    @Autowired
    protected CoreProperties coreProperties;

    @Subscribe
    protected void onInit(InitEvent event) {
        customerTable.addGeneratedColumn("language", entity -> {
            ComboBox<String> comboBox = uiComponents.create(ComboBox.TYPE_STRING);
            comboBox.setId("languageComboBox");
            List<String> locales = coreProperties.getAvailableLocales().stream()
                    .map(locale -> messageTools.getLocaleDisplayName(locale))
                    .collect(Collectors.toList());
            comboBox.setOptionsList(locales);
            comboBox.setWidth("100%");
            return comboBox;
        });

        customerTable.getColumn("fullName").setCaption("Full Name");
        customerTable.getColumn("language").setCaption("Language");
    }

    @Install(to = "customerTable.fullName", subject = "columnGenerator")
    protected Component customerTableFullNameColumnGenerator(Customer customer) {
        return new Table.PlainTextCell(metadataTools.getInstanceName(customer));
    }
}
