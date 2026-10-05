A line race shows how the series compete over time. The race is driven by the data: all years are on the category axis from the start, and the values appear year by year.

Click **Start** to run the race. Every half second the timer adds the sales of the next year, and the controller passes the changed item to `updateItem()` of `ListChartItems`. `animationDurationUpdate` equal to the timer delay and the linear `animationEasingUpdate` make the lines grow smoothly between the updates. **Stop** pauses the race. After the last year the timer stops, and **Start** runs a new race.

`endLabel` shows the series name at the end of each line and moves along with it. `labelLayout` with `moveOverlap="SHIFT_Y"` moves the labels apart vertically when they overlap, and the right padding of the `gridItem` leaves room for them. `emphasis` with `focus="SERIES"` fades the other lines when you point at one.
