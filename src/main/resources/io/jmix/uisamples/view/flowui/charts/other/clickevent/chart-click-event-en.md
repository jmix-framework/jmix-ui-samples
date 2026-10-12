`ChartClickEvent` is fired when the user clicks a chart element: a pie sector, a bar or a line point. To handle the click, subscribe to the event with `@Subscribe` and the chart id.

`getDetail()` describes the clicked element:

- `seriesName` and `seriesIndex` identify the series;
- `name` and `dataIndex` identify the data item;
- `data` holds the fields of the data item, here `country` and `litres`;
- `percent` is the share of the item in a pie series.

The fields follow the parameters of [ECharts mouse events](https://echarts.apache.org/en/api.html#events.Mouse%20events).

Click a sector to see its value and share. Other mouse events are handled the same way: `ChartDoubleClickEvent`, `ChartMouseDownEvent`, `ChartMouseUpEvent`, `ChartMouseMoveEvent`, `ChartMouseOverEvent` and `ChartMouseOutEvent`.
