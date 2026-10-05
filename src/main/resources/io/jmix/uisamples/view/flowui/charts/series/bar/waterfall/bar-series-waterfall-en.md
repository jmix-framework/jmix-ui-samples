A waterfall chart shows how a total breaks down into parts. It is built from two `bar` series in the same `stack`.

The first series is an invisible placeholder: its `itemStyle` sets a transparent `color` and `borderColor`, and `silent` turns off its reaction to the mouse. The placeholder lifts each visible bar to the amount that remains after the previous expenses. The controller calculates these heights when it fills the data set.

The tooltip `formatterFunction` shows only the visible series.
