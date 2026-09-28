The `position` attribute sets where the popover opens relative to its target: above (`TOP_*`), below (`BOTTOM_*`), before (`START_*`) or after (`END_*`) it. The default is `BOTTOM`. If there is not enough room, the popover moves to the opposite side.

Two theme variants change the look of the popover. Set them with the `themeNames` attribute:

- `arrow` draws an arrow pointing to the target;
- `no-padding` removes the padding around the content, which suits lists and images.

Click the target to open the popover, then change the settings. A click outside does not close the popover because `closeOnOutsideClick` is `false`.
