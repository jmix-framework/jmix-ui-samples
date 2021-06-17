package io.jmix.sampler.screen.ui.pivottable.dataexport;

import io.jmix.pivottable.component.PivotTable;
import io.jmix.pivottable.component.PivotTableExtension;
import io.jmix.pivottable.component.impl.PivotExcelExporter;
import io.jmix.pivottable.component.impl.PivotTableExtensionImpl;
import io.jmix.ui.component.Button;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("pivottable-data-export")
@UiDescriptor("pivottable-data-export.xml")
public class PivotTableDataExportSample extends ScreenFragment {

    @Autowired
    private PivotTable pivotTable;
    @Autowired
    protected ObjectProvider<PivotExcelExporter> excelExporterObjectProvider;

    private PivotTableExtension extension;

    @Subscribe
    protected void onInit(InitEvent event) {
        extension = new PivotTableExtensionImpl(pivotTable, excelExporterObjectProvider.getObject());
    }

    @Subscribe("exportBtn")
    protected void onExportBtnClick(Button.ClickEvent event) {
        extension.exportTableToXls();
    }
}
