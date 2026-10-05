`ChartClickEvent` срабатывает, когда пользователь щёлкает по элементу диаграммы: сектору, столбцу или точке линии. Чтобы обработать щелчок, подпишитесь на событие через `@Subscribe` с id диаграммы.

`getDetail()` описывает элемент, по которому щёлкнули:

- `seriesName` и `seriesIndex` указывают на серию;
- `name` и `dataIndex` указывают на элемент данных;
- `data` содержит поля элемента данных, здесь это `country` и `litres`;
- `percent` — доля элемента в круговой серии.

Поля повторяют параметры [событий мыши ECharts](https://echarts.apache.org/en/api.html#events.Mouse%20events).

Щёлкните по сектору, чтобы увидеть его значение и долю. Остальные события мыши обрабатываются так же: `ChartDoubleClickEvent`, `ChartMouseDownEvent`, `ChartMouseUpEvent`, `ChartMouseMoveEvent`, `ChartMouseOverEvent` и `ChartMouseOutEvent`.
