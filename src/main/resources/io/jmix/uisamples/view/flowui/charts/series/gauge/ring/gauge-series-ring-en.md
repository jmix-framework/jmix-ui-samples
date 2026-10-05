A `gauge` series without a pointer can show progress as rings. Each data item becomes a separate ring: `progress` with `overlap="false"` draws the progress bars of the items side by side instead of on top of each other, and `roundCap` rounds their ends.

`startAngle="90"` and `endAngle="-270"` close the scale into a full circle. The scale marks are hidden with `splitLine`, `axisTick` and `axisLabel`, and `pointer` with `show="false"` hides the pointer.

The `title` and `detail` elements of each data item set `offsetCenter`, so the names and the values are stacked in the middle of the rings.
