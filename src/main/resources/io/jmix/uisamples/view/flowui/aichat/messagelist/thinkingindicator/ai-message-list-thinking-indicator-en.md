While the list is generating and the answer is still empty, it shows an animated indicator of three pulsing dots, with a label beside it.

The `thinkingIndicator` element replaces the animation with any component, here an `image`. Only the animation is replaced, the label beside it stays.

The label is defined by `thinkingStages`: each stage sets the delay after which its text replaces the previous one.

The indicator is attached to the message being generated. A list switched to `GENERATING` while its last message is a user message shows nothing at all, so the button above adds an empty assistant message first.
