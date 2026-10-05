The chart shows 20,000 points. With `large="true"`, the `scatter` series switches to an optimized rendering mode when the number of points exceeds `largeThreshold` (2000 by default): all points are drawn as one shape instead of separate elements.

The optimization has a price: in this mode, individual points can't have their own styles. A small `symbolSize` and a semi-transparent `itemStyle` make the dense areas of the clusters visible.

Zoom in with the mouse wheel or the sliders. Each axis has its own `insideDataZoom` and `sliderDataZoom`, bound with `xAxisIndexes` or `yAxisIndexes`. `filterMode="EMPTY"` hides the points outside the zoom window instead of removing them from the data, so zooming one axis doesn't change the range of the other.
