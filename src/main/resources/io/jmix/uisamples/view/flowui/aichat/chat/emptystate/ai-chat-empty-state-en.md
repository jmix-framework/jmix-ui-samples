Before the first message the chat centers its message input in the space and offers two slots around it. The `emptyStateHeader` and `emptyStateFooter` elements fill them, with a greeting, suggestions, or a disclaimer.

The suggestions below are ordinary buttons. Each one submits a prompt with `prompt(String)`, which sends a message as though the user had typed it.

The empty state collapses as soon as the first message lands, and comes back when the conversation is cleared.

The `inputHeader` and `inputFooter` bands are hidden while the chat is empty, because the empty state owns that space. To keep one visible anyway, apply the `input-header-component-always-visible` or `input-footer-component-always-visible` theme variant.
