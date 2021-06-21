package io.jmix.sampler.screen.ui.diagrams.charts.other.dynamicallyсreatinggraphs;

import io.jmix.charts.component.SerialChart;
import io.jmix.charts.model.graph.Graph;
import io.jmix.core.MessageTools;
import io.jmix.core.Metadata;
import io.jmix.core.metamodel.model.MetaClass;
import io.jmix.core.metamodel.model.MetaProperty;
import io.jmix.sampler.entity.TransportCount;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("dynamically-creating-graphs-chart")
@UiDescriptor("dynamically-creating-graphs-chart.xml")
public class DynamicallyCreatingGraphsChartSample extends ScreenFragment {
    private static final String CATEGORY_FIELD = "year";

    @Autowired
    private SerialChart serialChart;
    @Autowired
    private Metadata metadata;
    @Autowired
    private MessageTools messageTools;

    @Subscribe
    private void onInit(InitEvent event) {
        getScreenData().loadAll();

        MetaClass metaClass = metadata.getClass(TransportCount.class);
        for (MetaProperty property : metaClass.getOwnProperties()) {
            if (!CATEGORY_FIELD.equals(property.getName())) {
                serialChart.addGraphs(createGraph(property));
            }
        }
    }

    private Graph createGraph(MetaProperty property) {
        Graph graph = new Graph();
        graph.setTitle(messageTools.getPropertyCaption(property));
        graph.setValueField(property.getName());
        graph.setUseLineColorForBulletBorder(true);
        graph.setFillAlphas(0.5);
        graph.setLineThickness(3);
        graph.setBalloonText("[[title]] : [[value]]");
        return graph;
    }
}
