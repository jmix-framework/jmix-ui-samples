package io.jmix.sampler.screen.ui.components.datagrid.headerfooter;

import io.jmix.core.Messages;
import io.jmix.sampler.entity.CountryGrowth;
import io.jmix.ui.App;
import io.jmix.ui.component.DataGrid;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.model.CollectionLoader;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Collection;

@UiController("datagrid-header-footer")
@UiDescriptor("datagrid-header-footer.xml")
public class DataGridHeaderFooterSample extends ScreenFragment {
    @Autowired
    protected DataGrid<CountryGrowth> dataGrid;
    @Autowired
    protected CollectionContainer<CountryGrowth> countryGrowthDc;
    @Autowired
    protected CollectionLoader<CountryGrowth> countryGrowthLoader;
    @Autowired
    protected Messages messages;
    @Autowired
    protected ApplicationContext applicationContext;

    protected DecimalFormat percentFormat;

    @Subscribe
    protected void onInit(InitEvent event) {
        countryGrowthLoader.load();

        initPercentFormat();
        initHeader();
        initFooter();
        initRenderers();
    }

    protected void initPercentFormat() {
        percentFormat = (DecimalFormat) NumberFormat.getPercentInstance(App.getInstance().getLocale());
        percentFormat.setMultiplier(1);
        percentFormat.setMaximumFractionDigits(2);
    }

    protected void initRenderers() {
        dataGrid.getColumnNN("year2014").setRenderer(
                applicationContext.getBean(DataGrid.NumberRenderer.class, percentFormat));
        dataGrid.getColumnNN("year2015").setRenderer(
                applicationContext.getBean(DataGrid.NumberRenderer.class, percentFormat));
    }

    protected void initHeader() {
        DataGrid.HeaderRow headerRow = dataGrid.prependHeaderRow();
        DataGrid.HeaderCell headerCell = headerRow.join("year2014", "year2015");
        headerCell.setText("GDP growth");
        headerCell.setStyleName("center-bold");
    }

    protected void initFooter() {
        DataGrid.FooterRow footerRow = dataGrid.appendFooterRow();
        footerRow.getCell("country").setHtml(
                "<strong>" + messages.getMessage(DataGridHeaderFooterSample.class, "average") + "</strong>");
        footerRow.getCell("year2014").setText(percentFormat.format(getAverage("year2014")));
        footerRow.getCell("year2015").setText(percentFormat.format(getAverage("year2015")));
    }

    protected double getAverage(String propertyId) {
        double average = 0.0;
        Collection<CountryGrowth> items = countryGrowthDc.getItems();
        for (CountryGrowth countryGrowth : items) {
            Double value = propertyId.equals("year2014") ? countryGrowth.getYear2014() : countryGrowth.getYear2015();
            average += value != null ? value : 0.0;
        }
        return average / items.size();
    }
}
