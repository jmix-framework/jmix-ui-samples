`RangeSlider` lets the user pick a range of values with two handles: `integerRangeSlider` for `Integer` bounds and `decimalRangeSlider` for `Double` ones. The initial range is set with `startValue` and `endValue`, the track with `min`, `max` and `step`.

Its value is a record – `IntegerRangeSliderValue` or `DecimalRangeSliderValue` – with `start()` and `end()` accessors. Unlike a plain slider, a range slider cannot be bound to a data container.
