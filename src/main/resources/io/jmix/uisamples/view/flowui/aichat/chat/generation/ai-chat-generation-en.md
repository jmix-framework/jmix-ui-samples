The chat moves between three states (idle, generating, and error) and reports every transition with `GenerationStateChangeEvent`. The indicator above the chat follows it, and the *Stop* button is enabled only while an answer is generating.

The user can stop and regenerate without any code in the application: while an answer is generating the send button becomes a stop button, and a *Retry* button appears on the error banner. The partial answer of a stopped generation stays on screen and is never committed.

The same operations are available from the application using two buttons `stop()` and `regenerate()`. The built-in `aichat_messageRegenerate` action, declared in `assistantActions`, lets the user regenerate the last one answer: it rewinds the conversation to that message and runs it again.

When generation fails, the message list shows its own error banner and the application is notified with `GenerationFailedEvent`, which carries the error and whatever text had arrived before it.
