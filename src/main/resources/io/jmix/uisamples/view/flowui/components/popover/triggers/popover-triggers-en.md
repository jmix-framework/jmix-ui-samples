A popover can open on a click on the target, when the pointer hovers over it, or when it receives focus. Enable the triggers with the `openOnClick`, `openOnHover` and `openOnFocus` attributes. `openOnClick` is on by default.

- A popover opened on hover closes when the pointer leaves both the target and the popover, so the user can move the pointer into it and click a link. `hoverDelay` and `hideDelay` set the delays in milliseconds before opening and closing.
- A popover opened on focus closes when the focus leaves the target and the popover. `focusDelay` sets the delay before opening.
- With all triggers disabled, the popover opens only from the controller with the `open()` method.

Whatever the trigger, a click outside the popover and the <kbd>Esc</kbd> key close it. To disable this, set `closeOnOutsideClick` and `closeOnEsc` to `false`.
