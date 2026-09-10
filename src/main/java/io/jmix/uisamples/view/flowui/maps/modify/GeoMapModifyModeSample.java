package io.jmix.uisamples.view.flowui.maps.modify;

import com.vaadin.flow.component.notification.Notification;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.view.*;
import io.jmix.mapsflowui.component.model.feature.LineStringFeature;
import io.jmix.mapsflowui.component.model.feature.PolygonFeature;
import io.jmix.mapsflowui.component.model.source.HasFeatureDelete.SourceFeatureDeleteEvent;
import io.jmix.mapsflowui.component.model.source.HasFeatureModify.SourceFeatureModifyEndEvent;
import io.jmix.mapsflowui.component.model.source.HasFeatureModify.SourceFeatureModifyStartEvent;
import io.jmix.mapsflowui.component.model.source.VectorSource;
import io.jmix.mapsflowui.kit.component.model.feature.Feature;
import org.springframework.beans.factory.annotation.Autowired;

@ViewController(id = "geo-map-modify-mode")
@ViewDescriptor(path = "geo-map-modify-mode.xml")
public class GeoMapModifyModeSample extends StandardView {

    @Autowired
    private Notifications notifications;

    @ViewComponent("map.vectorLayer.source")
    private VectorSource source;

    @Subscribe
    public void onInit(final InitEvent event) {
        source.addFeature(new LineStringFeature(Geometries.createLineString())
                .withProperty("name", "LineString"));
        source.addFeature(new PolygonFeature(Geometries.createPolygon())
                .withProperty("name", "Polygon"));
    }

    @Subscribe("map.vectorLayer.source")
    public void onSourceFeatureModifyStart(final SourceFeatureModifyStartEvent event) {
        Feature feature = event.getFeatures().iterator().next();
        notifications.create("ModifyStartEvent",
                        "Feature: " + feature.getProperties().get("name"))
                .withPosition(Notification.Position.BOTTOM_END)
                .show();
    }

    @Subscribe("map.vectorLayer.source")
    public void onSourceFeatureModifyEnd(final SourceFeatureModifyEndEvent event) {
        Feature feature = event.getFeatures().iterator().next();
        notifications.create("ModifyEndEvent",
                        "Feature: " + feature.getProperties().get("name"))
                .withPosition(Notification.Position.BOTTOM_END)
                .show();
    }

    @Subscribe("map.vectorLayer.source")
    public void onSourceFeatureDelete(final SourceFeatureDeleteEvent event) {
        Feature feature = event.getFeatures().iterator().next();
        notifications.create("DeleteEvent",
                        "Feature: " + feature.getProperties().get("name"))
                .withPosition(Notification.Position.BOTTOM_END)
                .show();
    }
}