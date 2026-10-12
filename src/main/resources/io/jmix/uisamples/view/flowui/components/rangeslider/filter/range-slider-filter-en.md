A range slider is a natural filter control. This sample feeds both bounds of the selected range into the collection loader's query parameters and reloads the grid whenever the range changes.

Sliders use the `ON_CHANGE` value change mode by default, so the query runs once the user releases a handle rather than on every step of the drag. Read the bounds from the event value with `start()` and `end()`.
