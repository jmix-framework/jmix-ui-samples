A boxplot shows how values are spread. The box spans from the first quartile to the third one, the line inside the box marks the median, and the whiskers reach the minimum and the maximum.

The `boxplot` series takes five values for each category. `encode` lists them for the value axis in this order: `y="min, q1, median, q3, max"`, and `x` sets the category field. The same list in `tooltip` makes the tooltip show all five values.

The chart doesn't calculate these values from raw data, so the data set must already contain them. See how to calculate them in the controller in the [Multiple Boxplot series]({contextPath}/sample/boxplot-series-multiple) sample.
