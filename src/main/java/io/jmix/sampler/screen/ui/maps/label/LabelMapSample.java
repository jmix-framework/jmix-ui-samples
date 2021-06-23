package io.jmix.sampler.screen.ui.maps.label;

import io.jmix.mapsui.component.CanvasLayer;
import io.jmix.mapsui.component.GeoMap;
import io.jmix.mapsui.component.layer.style.GeometryStyles;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.locationtech.jts.geom.Point;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

@UiController("label-map")
@UiDescriptor("label-map.xml")
public class LabelMapSample extends ScreenFragment {

    @Autowired
    private GeoMap map;

    @Autowired
    private GeometryStyles geometryStyles;

    private CanvasLayer.Point labelPoint;

    @Subscribe("map")
    public void onMapMoveEnd(GeoMap.MoveEndEvent event) {
        if (labelPoint == null) {
            labelPoint = map.getCanvas().addPoint(event.getCenter());
        }

        labelPoint.setStyle(
                geometryStyles.point()
                        .withDivIcon(createLabelContent(event.getCenter()))
                        .setIconSize(400, 80)
                        .setStyles("my-style"));
    }

    private String createLabelContent(Point center) {
        Point labelPointJts = labelPoint.getGeometry();
        return String.format(
                "<h1 style=font-size:25px>Distance from this point to the current center: " +
                        "<br>latitude: %s<br>longitude: %s</h1>",
                BigDecimal.valueOf(labelPointJts.getY()).subtract(BigDecimal.valueOf(center.getY())),
                BigDecimal.valueOf(labelPointJts.getX()).subtract(BigDecimal.valueOf(center.getX())));
    }
}