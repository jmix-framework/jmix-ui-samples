package io.jmix.uisamples.view.flowui.pivottable.events;

import io.jmix.core.Messages;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.view.*;
import io.jmix.pivottableflowui.component.PivotTable;
import io.jmix.pivottableflowui.kit.component.model.AggregationMode;
import io.jmix.pivottableflowui.kit.component.model.Renderer;
import io.jmix.pivottableflowui.kit.event.PivotTableCellClickEvent;
import io.jmix.pivottableflowui.kit.event.PivotTableRefreshEvent;
import io.jmix.pivottableflowui.kit.event.PivotTableRefreshEventDetail;
import io.jmix.uisamples.entity.TemperatureData;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@ViewController("pivot-table-events")
@ViewDescriptor("pivot-table-events.xml")
public class PivotTableEvents extends StandardView {

    @ViewComponent
    private PivotTable<TemperatureData> pivotTable;

    @Autowired
    private Notifications notifications;
    @Autowired
    private Messages messages;

    @Subscribe("pivotTable")
    public void onPivotTablePivotTableCellClick(final PivotTableCellClickEvent<?> event) {
        List<TemperatureData> items = event.getDetail().getItems();

        String message = items.stream()
                .map(temperatureData -> "Entity id: %s, Celsius: %s".formatted(
                                temperatureData.getId(), temperatureData.getTemperature()
                        )
                )
                .collect(Collectors.joining("\n"));

        notifications.show("Cell click event (items in the cell)", message);
    }

    @Subscribe("pivotTable")
    public void onPivotTablePivotTableRefresh(final PivotTableRefreshEvent event) {
        PivotTableRefreshEventDetail refreshDetail = event.getDetail();
        String message = """
                Renderer: %s
                Aggregation: %s
                Rows: %s
                Columns: %s
                """.formatted(
                getRendererMessage(refreshDetail.getRenderer()),
                getAggregatorMessage(refreshDetail.getAggregationMode()),
                getLocalizedProperties(refreshDetail.getRows()),
                getLocalizedProperties(refreshDetail.getColumns())
        );

        notifications.show("Refresh event", message);
    }

    private String getRendererMessage(Renderer renderer) {
        return messages.getMessage("pivottable.renderer." + renderer.getId());
    }

    private String getAggregatorMessage(AggregationMode aggregationMode) {
        return messages.getMessage("pivottable.aggregator." + aggregationMode.getId());
    }

    private String getLocalizedProperties(List<String> properties) {
        if (CollectionUtils.isEmpty(properties)) {
            return "[Empty]";
        }

        return properties.stream()
                .map(pivotTable.getProperties()::get)
                .collect(Collectors.joining(", "));
    }
}
