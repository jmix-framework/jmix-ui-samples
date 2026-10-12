The side panel of `SidePanelLayout` can be resized by the user at runtime. Set the `sidePanelResizable` attribute
to `true` and drag the handle on the panel's inner edge.

The size stays within the bounds defined by the `sidePanelHorizontalMinSize` / `sidePanelHorizontalMaxSize` and
`sidePanelVerticalMinSize` / `sidePanelVerticalMaxSize` attributes.

The `resizer-small` theme renders the handle as a thin line with a grip that appears on hover.

`SidePanelAfterResizeEvent` is fired once the user releases the handle. It provides the new size in `px`.

The chosen size is saved by the `settings` facet and is restored when the view is opened again.
