`aiMessageList` shows the conversation of a chat. `aiChat` embeds it and drives it for you; used on its own it displays a stored conversation, or a conversation the application drives itself.

Messages are items built with the `AiMessageListItem` factory methods. An item instance belongs to one list and may appear in it only once.

An assistant message is rendered as Markdown. A fenced code block becomes a real `aiCodeBlock`, with its language label, highlighting, and its own copy and wrap actions. A user message is rendered as plain text in a bubble, so Markdown in a user message is shown as typed.

A long user message is collapsed to a fixed height with a fade and a *Show more* toggle, as the last message here shows.
