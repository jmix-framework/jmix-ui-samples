package io.jmix.sampler.screen.ui.diagrams.charts.other.twolevelcategory;

import io.jmix.charts.component.SerialChart;
import io.jmix.charts.model.Guide;
import io.jmix.charts.model.JsFunction;
import io.jmix.sampler.entity.CountrySales;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@UiController("two-level-category-chart")
@UiDescriptor("two-level-category-chart.xml")
public class TwoLevelCategoryChartSample extends ScreenFragment {

    @Autowired
    private SerialChart serialChart;
    @Autowired
    private CollectionContainer<CountrySales> countrySalesDc;

    @Subscribe
    private void onInit(InitEvent event) {
        getScreenData().loadAll();

        Map<String, List<CountrySales>> groupedData = groupDataByGuide(countrySalesDc.getItems());
        List<Guide> guides = new ArrayList<>();

        for (Map.Entry<String, List<CountrySales>> entry : groupedData.entrySet()) {
            List<CountrySales> guideData = entry.getValue();
            guides.add(new Guide()
                    .setCategory(guideData.get(0).getCategory())
                    .setToCategory(guideData.get(guideData.size() - 1).getCategory())
                    .setLabel(entry.getKey())
                    .setExpand(true)
                    .setLabelRotation(0)
                    .setTickLength(120));
        }

        serialChart.getCategoryAxis().addGuides(guides.toArray(new Guide[0]));
        serialChart.getCategoryAxis().setLabelFunction(new JsFunction(
                "function(label, item) { return item.dataContext.product; }"
        ));
    }

    private Map<String, List<CountrySales>> groupDataByGuide(List<CountrySales> dataList) {
        Map<String, List<CountrySales>> result = new HashMap<>();
        for (CountrySales data : dataList) {
            String guideKey = data.getCountry();
            List<CountrySales> guideData = result.computeIfAbsent(guideKey, k -> new ArrayList<>());
            guideData.add(data);
        }
        return result;
    }
}