package io.jmix.sampler.screen.ui.components.datagrid.renderer;

import io.jmix.sampler.entity.CountryGrowth;
import io.jmix.ui.App;
import io.jmix.ui.Notifications;
import io.jmix.ui.component.ContentMode;
import io.jmix.ui.component.DataGrid;
import io.jmix.ui.screen.Install;
import io.jmix.ui.screen.LoadDataBeforeShow;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.text.DecimalFormat;
import java.text.NumberFormat;

@UiController("datagrid-renderer")
@UiDescriptor("datagrid-renderer.xml")
@LoadDataBeforeShow
public class DataGridRendererSample extends ScreenFragment {

    @Autowired
    protected DataGrid<CountryGrowth> dataGrid;
    @Autowired
    protected Notifications notifications;
    @Autowired
    protected ApplicationContext applicationContext;

    protected DecimalFormat percentFormat;

    @Subscribe
    protected void onInit(InitEvent event) {
        initPercentFormat();
        initRenderers();
    }

    protected void initPercentFormat() {
        percentFormat = (DecimalFormat) NumberFormat.getPercentInstance(App.getInstance().getLocale());
        percentFormat.setMultiplier(1);
        percentFormat.setMaximumFractionDigits(2);
    }

    protected void initRenderers() {
        dataGrid.getColumnNN("flag").setRenderer(applicationContext.getBean(DataGrid.ImageRenderer.class));
        dataGrid.getColumnNN("year2015").setRenderer(
                applicationContext.getBean(DataGrid.NumberRenderer.class, percentFormat));

        DataGrid.ClickableTextRenderer<CountryGrowth> clickableTextRenderer = 
                applicationContext.getBean(DataGrid.ClickableTextRenderer.class);
        clickableTextRenderer.setRendererClickListener(clickEvent -> {
            CountryGrowth item = clickEvent.getItem();
            notifications.create()
                    .withContentMode(ContentMode.HTML)
                    .withCaption("<b>" + item.getCountry() + "</b>")
                    .show();
        });
        dataGrid.getColumnNN("country").setRenderer(clickableTextRenderer);
    }

    @Install(to = "dataGrid.growth", subject = "columnGenerator")
    protected String dataGridGrowthColumnGenerator(DataGrid.ColumnGeneratorEvent<CountryGrowth> event) {
        CountryGrowth item = event.getItem();
        double growth = item.getYear2015() - item.getYear2014();

        return growth > 0
                ? "<font color='green'>" + percentFormat.format(growth) + "</font>"
                : "<font color='red'>" + percentFormat.format(growth) + "</font>";
    }
}
