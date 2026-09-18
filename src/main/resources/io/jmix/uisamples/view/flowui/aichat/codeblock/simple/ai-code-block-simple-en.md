`aiCodeBlock` displays a fragment of source code with syntax highlighting, an optional language label, and actions for copying and wrapping the code. Inside a conversation `aiMessageList` uses it automatically for every fenced code block of an assistant message.

A nested `code` element holds multi-line source, wrapped in `CDATA` so that it arrives verbatim. Write the element with the `aichat` prefix, as `aichat:code`.

The `code` attribute holds a single line instead, and accepts a message key, which is useful when the source is kept in a message bundle.

The `language` attribute does two things: it puts a label in the block's header, and it tells the highlighter which grammar to apply. `AiCodeBlockLanguage` holds the common language ids as constants.

`highlight="false"` keeps the header and the label and renders the code plain, as the last block shows. Omitting the language removes the header too.

A block takes the width of its content and never exceeds the width of its container.
