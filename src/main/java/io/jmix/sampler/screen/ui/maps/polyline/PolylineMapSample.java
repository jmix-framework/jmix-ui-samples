package io.jmix.sampler.screen.ui.maps.polyline;

import io.jmix.maps.utils.GeometryUtils;
import io.jmix.mapsui.component.CanvasLayer;
import io.jmix.mapsui.component.GeoMap;
import io.jmix.mapsui.component.layer.style.GeometryStyles;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.LineString;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("polyline-map")
@UiDescriptor("polyline-map.xml")
public class PolylineMapSample extends ScreenFragment {

    @Autowired
    private GeoMap map;

    @Autowired
    private GeometryStyles geometryStyles;

    @Subscribe
    protected void onInit(InitEvent event) {
        Coordinate[] coordinates = PolylinePoints.getPolylinePoints();
        LineString polyline = GeometryUtils.getGeometryFactory().createLineString(coordinates);

        CanvasLayer canvas = map.getCanvas();
        canvas.addPolyline(polyline)
                .setStyle(geometryStyles.polyline()
                        .setStrokeColor("#000")
                        .setStrokeWeight(5)
                        .setStrokeOpacity(0.8));
    }
}