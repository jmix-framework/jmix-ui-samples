The chart shows 20,000 points, many more than the pixels across it. `sampling` makes the `line` series draw only some of the points while keeping the shape of the line:

- `LARGEST_TRIANGLE_THREE_BUCKET` — the LTTB algorithm keeps the points that define the shape best, including peaks;
- `AVERAGE`, `MAX`, `MIN` and `SUM` — replace each group of points with one aggregated value.

`showSymbol="false"` hides the point markers, which are not visible at this density anyway. Zoom in with the mouse wheel or the slider: the fewer points fall into the visible range, the more of them the chart draws.
