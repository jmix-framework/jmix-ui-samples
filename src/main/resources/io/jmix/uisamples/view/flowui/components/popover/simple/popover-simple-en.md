`Popover` is an overlay anchored to another component, called the target. Unlike a tooltip, it can contain any components and receive focus, so it suits rich, interactive content.

Declare `<popover>` in the descriptor and set the `target` attribute to the id of a component in the same view or fragment. The popover renders nothing in place. By default, it opens on a click on the target and closes on a click outside, on a second click on the target, or on <kbd>Esc</kbd>.

The popover and its content are regular components of the view: inject them with `@ViewComponent` and subscribe to their events. In this sample, the button inside the popover closes it with the `close()` method.
