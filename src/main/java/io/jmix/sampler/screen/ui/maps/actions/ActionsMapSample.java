package io.jmix.sampler.screen.ui.maps.actions;

import io.jmix.mapsui.component.GeoMap;
import io.jmix.ui.Notifications;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("actions-map")
@UiDescriptor("actions-map.xml")
public class ActionsMapSample extends ScreenFragment {

    @Autowired
    private GeoMap map;
    @Autowired
    private Notifications notifications;

    @Subscribe("map")
    public void onMapClick(GeoMap.ClickEvent event) {
        String caption = String.format("Map left click: %.2f, %.2f", event.getPoint().getX(),
                event.getPoint().getY());
        notifications.create()
                .withCaption(caption)
                .show();
    }

    @Subscribe("map")
    public void onMapRightClick(GeoMap.RightClickEvent event) {
        String caption = String.format("Map right click: %.2f, %.2f", event.getPoint().getX(),
                event.getPoint().getY());
        notifications.create()
                .withCaption(caption)
                .show();
    }

    @Subscribe("map")
    public void onMapMoveEnd(GeoMap.MoveEndEvent event) {
        String content = String.format(
                "North-east bound: %.8f, %.8f\nSouth-west bound: %.8f, %.8f\nCenter: %.8f, %.8f\nZoom level: %s\n",
                map.getBounds().getNorthEast().getX(),
                map.getBounds().getNorthEast().getY(),
                map.getBounds().getSouthWest().getX(),
                map.getBounds().getSouthWest().getY(),
                event.getCenter().getX(),
                event.getCenter().getY(),
                event.getZoomLevel());
        notifications.create()
                .withCaption("Map has been moved")
                .withDescription(content)
                .show();
    }

    @Subscribe("map")
    public void onMapZoomEnd(GeoMap.ZoomEndEvent event) {
        notifications.create(Notifications.NotificationType.TRAY)
                .withCaption("Map has been zoomed")
                .show();
    }

    @Subscribe("map")
    public void onMapDragEnd(GeoMap.DragEndEvent event) {
        notifications.create(Notifications.NotificationType.TRAY)
                .withCaption("Map has been dragged")
                .show();
    }
}