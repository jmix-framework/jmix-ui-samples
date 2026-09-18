`aiMessageInput` is the component witch the user writes a message.`aiChat` embeds it and configures it on your behalf.

The text reaches the application through `SubmitEvent`, which carries the submitted text.

The component does not expose the text it holds. There is no `getValue()` or `setValue()`, so the application cannot read what the user is typing, pre-fill the field with a suggested prompt, or clear it early. The text becomes available only when the user submits it.

The field grows with its content up to `maxRows` lines, ten by default, and scrolls internally beyond that. By default *Enter* submits the message and *Shift+Enter* inserts a line break; `enterAction="NEWLINE"` swaps the two, so that *Enter* inserts a line break and *Ctrl/Cmd+Enter* submits. The setting also tells mobile keyboards how to label their action key.

A standalone message input has no width of its own, so give it a width or place it in a container that constrains one.
