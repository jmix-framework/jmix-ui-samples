`aiChat` is a complete component to work with AI-chat: a message list, a message input, an empty state, and the generation cycle that connects them to a language model.

For the component to function, an `llmProvider` is required that responds to requests with a stream of text. The component renders the answer as Markdown, turns fenced code blocks into `aiCodeBlock` components and shows a thinking indicator while the answer is empty.

This example uses a local stub rather than a real model, so the type of answer is determined by the request. Try `code` for a fenced code block, `long text` for a long answer, or `error` for a failed generation.
