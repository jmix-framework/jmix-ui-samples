Several `boxplot` series on one category axis are placed side by side, so you can compare the distributions within each category. Here each series is a season, and each category is a city.

All series read the same data set: each row holds five values for every season, and each series picks its own five fields with `encode`, for the value axis and for the tooltip.

The chart doesn't calculate the statistics, so the controller does it. It loads the monthly temperatures of the `TemperatureData` entity, groups them by city and season, and calculates the minimum, the quartiles, the median and the maximum of each group.
