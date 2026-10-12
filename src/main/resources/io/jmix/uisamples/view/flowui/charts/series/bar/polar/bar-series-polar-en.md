A `bar` series with `coordinateSystem="POLAR"` is drawn in polar coordinates instead of rectangular ones. The `polar` element sets the size of the polar area, and the `radiusAxis` and `angleAxis` elements replace the X and Y axes.

Here the countries are on the radius axis, so each bar is an arc. `encode` maps the data fields to the axes: `radius` takes the category, `angle` takes the value.

The `maxFunction` of the angle axis sets its maximum to 4/3 of the largest value, so the longest arc takes three quarters of the circle.
