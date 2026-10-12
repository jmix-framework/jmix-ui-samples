An application can generate an answer itself, without `aiChat`. To do this you need to add the user message, add an empty assistant message, switch the list to `GENERATING`, append the answer as it arrives, then return the list to `IDLE`.

`addItem`, `appendText` and `setState` must be called on the UI thread. In this example the answer is produced in a `BackgroundTask` and appended in `progress`, which Jmix runs on the UI thread.

While the answer is still empty, the list shows an animated indicator with a label beside it. The `thinkingStages` element defines the label: each stage sets the delay after which its text replaces the previous one.

The thinking status feed shows what the assistant is doing, one line per step. A spinner turns into a check mark when the step completes. These calls are safe from a background thread, and the feed is cleared when the answer is done.

`setState(ERROR)` shows a banner with a *Retry* button. The application handles the click with a retry listener.
