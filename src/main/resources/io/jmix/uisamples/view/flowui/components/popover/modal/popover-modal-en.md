A modal popover works as a small dialog anchored to its target. With `modal="true"`, it blocks interaction with the rest of the UI, moves the focus inside and keeps it there until the popover closes. `backdropVisible="true"` additionally dims the UI behind it.

A click outside a modal popover closes it without triggering the component under the pointer. To keep the popover open until the user presses a button inside it, set `closeOnOutsideClick` to `false`.

The popover content belongs to the view, so it can include data-aware components. Here, property filters are bound to the loader of the grid and are not applied automatically (`autoApply="false"`), and the **Apply** button loads the data and closes the popover.
