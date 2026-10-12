A bar race is a bar chart that reorders its bars as the values change. Set `realtimeSort` on the `bar` series to sort the bars by value on every update.

Click **Start** to run the race. The timer increases the sales every two seconds, and the controller passes the changed items to `updateItem()` of `ListChartItems`. The chart updates the data set without rebuilding. **Stop** pauses the timer.

`inverse` on the category axis puts the largest bar at the top. `animationDurationUpdate` and `animationEasingUpdate` of the chart make the bars grow smoothly for the whole interval between updates. The shorter `animationDurationUpdate` of the axis moves the bars to their new places faster.
