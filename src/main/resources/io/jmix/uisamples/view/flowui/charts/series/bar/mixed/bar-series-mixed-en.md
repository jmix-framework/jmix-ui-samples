A chart can combine series of different types on one category axis. Here the `bar` series shows precipitation, and the `line` series shows temperature.

The values have different units, so each series gets its own value axis. The `yAxes` element declares two axes, and `yAxisIndex="1"` binds the line to the right one. `alignTicks` aligns the ticks of the right axis with the left one, and the `axisLabel` formatters add the units.
