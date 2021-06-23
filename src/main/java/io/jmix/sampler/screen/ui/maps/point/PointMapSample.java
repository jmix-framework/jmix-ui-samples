package io.jmix.sampler.screen.ui.maps.point;

import io.jmix.maps.utils.GeometryUtils;
import io.jmix.mapsui.component.CanvasLayer;
import io.jmix.mapsui.component.GeoMap;
import io.jmix.mapsui.component.layer.style.GeometryStyles;
import io.jmix.ui.icon.JmixIcon;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.locationtech.jts.geom.Point;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("point-map")
@UiDescriptor("point-map.xml")
public class PointMapSample extends ScreenFragment {

    @Autowired
    private GeoMap map;

    @Autowired
    private GeometryStyles geometryStyles;

    private static void accept(CanvasLayer.Point.ModifiedEvent modifiedEvent) {
    }

    @Subscribe
    protected void onInit(InitEvent event) {
        map.setCenter(-2.24955, 53.590905);
        addPoint(-2.24955, 53.590905);

        CanvasLayer.Point pointWithFontIcon = addPoint(-1.54632, 53.79253);
        pointWithFontIcon.setStyle(
                geometryStyles.point()
                        .withFontIcon(JmixIcon.CAR)
                        .setIconPathFillColor("#0004e0")
                        .setIconPathStrokeColor("#000")
                        .setIconTextFillColor("#fff"))
                .setPopupContent("Point with font icon");

        CanvasLayer.Point pointWithImageIcon = addPoint(-3.39477, 53.71946);

        pointWithImageIcon.setStyle(
                geometryStyles.point()
                        .withImageIcon("classpath:/io/jmix/sampler/screen/ui/maps/point/point-image-icon.svg")
                        .setIconSize(34, 34)
                        .setPopupAnchor(0, -17))
                .setPopupContent("Point with image icon");

        CanvasLayer.Point pointWithDivIcon = addPoint(-3.60351, 53.43408);

        pointWithDivIcon.setStyle(
                geometryStyles.point()
                        .withDivIcon("<font size=5 color='#fff' face='Courier'>Label icon</font>")
                        .setIconSize(150, 20)
                        .setPopupAnchor(0, -10)
                        .setStyles("my-div-style"))
                .setPopupContent("Point with div icon");
        map.getCanvas().addPointModifiedListener(PointMapSample::accept);
    }

    @Subscribe("map")
    public void onMapClick(GeoMap.ClickEvent event) {
        Point point = event.getPoint();
        addPoint(point.getX(), point.getY())
                .setTooltipContent("My place");
    }

    private CanvasLayer.Point addPoint(double longitude, double latitude) {
        CanvasLayer canvas = map.getCanvas();
        return canvas.addPoint(GeometryUtils.createPoint(longitude, latitude))
                .setEditable(true)
                .setPopupContent(String.format("Point: %.2f, %.2f", longitude, latitude));
    }
}