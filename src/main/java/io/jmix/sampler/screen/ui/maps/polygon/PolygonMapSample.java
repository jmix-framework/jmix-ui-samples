package io.jmix.sampler.screen.ui.maps.polygon;

import io.jmix.maps.utils.GeometryUtils;
import io.jmix.mapsui.component.CanvasLayer;
import io.jmix.mapsui.component.GeoMap;
import io.jmix.mapsui.component.layer.style.GeometryStyles;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Polygon;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("polygon-map")
@UiDescriptor("polygon-map.xml")
public class PolygonMapSample extends ScreenFragment {

    @Autowired
    private GeoMap map;

    @Autowired
    private GeometryStyles geometryStyles;

    @Subscribe
    protected void onInit(InitEvent event) {
        CanvasLayer canvas = map.getCanvas();
        map.selectLayer(canvas);

        Coordinate[] coordinates = PolygonPoints.getManchesterPoints();
        Polygon polygon = GeometryUtils.getGeometryFactory().createPolygon(coordinates);
        canvas.addPolygon(polygon)
                .setStyle(geometryStyles.polygon()
                        .setFillColor("#9CFBA9")
                        .setFillOpacity(0.6)
                        .setStrokeColor("#2CA860")
                        .setStrokeOpacity(1.0)
                        .setStrokeWeight(2));

        coordinates = PolygonPoints.getHydePoints();
        polygon = GeometryUtils.getGeometryFactory().createPolygon(coordinates);
        canvas.addPolygon(polygon)
                .setStyle(geometryStyles.polygon()
                        .setFillColor("#0068A3")
                        .setFillOpacity(0.6)
                        .setStrokeColor("#081B42")
                        .setStrokeOpacity(1.0)
                        .setStrokeWeight(2))
                .setEditable(true);
    }
}