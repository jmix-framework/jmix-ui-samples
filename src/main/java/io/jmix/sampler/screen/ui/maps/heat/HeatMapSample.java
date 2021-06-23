package io.jmix.sampler.screen.ui.maps.heat;

import io.jmix.maps.utils.GeometryUtils;
import io.jmix.mapsui.component.layer.HeatMapLayer;
import io.jmix.ui.screen.*;
import org.locationtech.jts.geom.Point;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@UiController("heat-map")
@UiDescriptor("heat-map.xml")
public class HeatMapSample extends ScreenFragment {

    @Install(to = "map.heatMapLayer1", subject = "dataDelegate")
    private Map<Point, Double> mapHeatMapLayer1DataDelegate(HeatMapLayer heatMapLayer) {
        return Arrays.stream(HeatPoints.getFirstHeatLayer()).collect(
                Collectors.toMap(coordinate -> GeometryUtils.getGeometryFactory().createPoint(coordinate),
                        coordinate -> 1D));
    }

    @Install(to = "map.heatMapLayer2", subject = "dataDelegate")
    private Map<Point, Double> mapHeatMapLayer2DataDelegate(HeatMapLayer heatMapLayer) {
        return Arrays.stream(HeatPoints.getSecondHeatLayer()).collect(
                Collectors.toMap(coordinate -> GeometryUtils.getGeometryFactory().createPoint(coordinate),
                        coordinate -> 1D));
    }
}