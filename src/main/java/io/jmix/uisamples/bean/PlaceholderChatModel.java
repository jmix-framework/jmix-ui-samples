package io.jmix.uisamples.bean;

import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.Prompt;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Local Spring AI chat model that answers the samples without contacting a language model.
 * It returns prepared answers chosen by keywords in the prompt, to exercise streaming,
 * Markdown, code blocks, long-scroll, and the error state:
 * <ul>
 *     <li>{@code code} - an answer with a fenced code block;</li>
 *     <li>{@code long text} - a long answer, for scrolling;</li>
 *     <li>{@code error} - a partial answer, then a failed generation.</li>
 * </ul>
 * The answer is emitted as a live stream: a pause before the first token, then a token
 * every {@code tokenDelay}.
 */
public class PlaceholderChatModel implements ChatModel {

    private static final String ANSWER_DEFAULT = """
            _(This answer comes from a placeholder model, not from a real language model.)_

            I can help you with:

            1. **Answering questions** - from quick facts to in-depth explanations.
            2. **Explaining concepts** - breaking down complex ideas into clear, step-by-step logic.
            3. **Brainstorming & creativity** - generating outlines, stories, or design ideas.
            4. **Guidance & troubleshooting** - walking you through processes or helping debug issues.

            ---

            ### How to get the most out of me

            | Step | What to do | Why it matters |
            |------|------------|----------------|
            | 1 | **State your goal clearly.** | A precise prompt yields a precise answer. |
            | 2 | **Add constraints or context.** | Tailors the response to your needs. |
            | 3 | **Ask follow-ups.** | We can iterate until you are satisfied. |

            ---

            Tip: try prompts containing **"code"**, **"long text"**, or **"error"** to see different behaviours.
            """;

    private static final String ANSWER_CODE = """
            A fenced code block in an answer is rendered as an `aiCodeBlock`, with its language
            label, highlighting, and its own copy and wrap actions:

            ```java
            @Autowired
            private DataManager dataManager;

            public List<Order> loadOrders(Customer customer) {
                return dataManager.load(Order.class)
                        .query("select o from Order o where o.customer = :customer")
                        .parameter("customer", customer)
                        .fetchPlan(fetchPlanBuilder -> fetchPlanBuilder
                                .addFetchPlan(FetchPlan.BASE)
                                .add("customer"))
                        .list();
            }
            ```

            The code streams in token by token like the rest of the answer, and the block is
            highlighted once the language is known.
            """;

    private static final String ANSWER_LONG = """
            _(A long placeholder answer - useful for watching the list follow the newest text.)_

            # Understanding recursion

            Recursion is a technique where a function solves a problem by calling itself on a smaller
            instance of the same problem. Every recursive solution has two essential parts: a **base
            case** that stops the recursion, and a **recursive case** that moves the computation toward
            that base case.

            ## Why it matters

            Many problems are naturally recursive. Traversing a tree, walking a file system, parsing
            nested structures, and divide-and-conquer algorithms all express themselves more clearly as
            recursion than as explicit loops with an auxiliary stack.

            ## The base case

            Without a base case, a recursive function never stops and eventually exhausts the call stack.
            The base case answers the smallest version of the problem directly, with no further calls.

            ## The recursive case

            The recursive case reduces the problem and delegates the rest to another invocation of the
            same function. The key discipline is to ensure every recursive call is **strictly closer**
            to the base case than its caller.

            ## A worked example

            Consider computing the factorial of a number. The factorial of zero is one - that is the base
            case. For any positive number, the factorial is that number multiplied by the factorial of the
            number below it. Each call shrinks the input by one until it reaches zero.

            ## Recursion versus iteration

            Anything expressed recursively can also be written iteratively, and vice versa. Recursion
            trades a little runtime overhead for clarity; iteration trades a little clarity for tighter
            control over memory. Choose based on which makes the intent obvious.

            ## Common pitfalls

            > Forgetting the base case, or writing a recursive case that does not actually shrink the
            > problem, are the two most common mistakes. Both lead to stack overflows.

            ## Tail recursion

            A recursive call is in *tail position* when it is the very last thing a function does. Some
            runtimes optimise tail calls into loops, reusing a single stack frame. On runtimes that do not,
            deep recursion can still overflow even when written in tail form.

            ## Summary

            Recursion is a way of thinking as much as a coding technique: define the trivial case, then
            describe how to take one step toward it. Master those two parts and a surprising number of
            problems become short, readable solutions.
            """;

    /** A word (or whitespace run) plus its trailing whitespace. */
    private static final Pattern TOKEN = Pattern.compile("\\S+\\s*|\\s+");
    private static final int MAX_TOKEN_LENGTH = 8;

    private final Duration subscriptionDelay;
    private final Duration tokenDelay;

    public PlaceholderChatModel() {
        this(Duration.ofSeconds(1), Duration.ofMillis(60));
    }

    public PlaceholderChatModel(Duration subscriptionDelay, Duration tokenDelay) {
        this.subscriptionDelay = subscriptionDelay;
        this.tokenDelay = tokenDelay;
    }

    @Override
    public ChatResponse call(Prompt prompt) {
        return response(answerFor(prompt));
    }

    @Override
    public Flux<ChatResponse> stream(Prompt prompt) {
        String text = promptText(prompt).toLowerCase(Locale.ROOT);
        if (text.contains("error")
                && !text.contains("code") && !text.contains("long text")) {
            // Emit a partial answer, then fail - exercises the error banner and its Retry button.
            return emit("Working on it - let me think ")
                    .concatWith(Flux.error(new RuntimeException("Simulated generation failure")));
        }
        return emit(answerFor(prompt));
    }

    /**
     * Turns one prepared answer into a paced run of small tokens, the way a live model
     * delivers its deltas.
     */
    private Flux<ChatResponse> emit(String answer) {
        return Flux.fromIterable(tokenize(answer))
                .delaySubscription(subscriptionDelay)
                .delayElements(tokenDelay)
                .map(this::response);
    }

    /**
     * Splits the answer into small LLM-like tokens whose concatenation is exactly the
     * original text. A naive {@code split(" ")} is pathological for code: indentation runs
     * degrade into a dribble of empty tokens (a visually frozen stream, one token delay
     * each) while long space-free expressions arrive as one big chunk. Cutting after each
     * whitespace run and capping the token length makes code stream as evenly as prose.
     */
    private static List<String> tokenize(String text) {
        List<String> tokens = new ArrayList<>();
        Matcher matcher = TOKEN.matcher(text);
        while (matcher.find()) {
            String piece = matcher.group();
            for (int i = 0; i < piece.length(); i += MAX_TOKEN_LENGTH) {
                tokens.add(piece.substring(i,
                        Math.min(piece.length(), i + MAX_TOKEN_LENGTH)));
            }
        }
        return tokens;
    }

    private String answerFor(Prompt prompt) {
        String text = promptText(prompt).toLowerCase(Locale.ROOT);
        if (text.contains("code")) {
            return ANSWER_CODE;
        }
        if (text.contains("long text")) {
            return ANSWER_LONG;
        }
        return ANSWER_DEFAULT;
    }

    private String promptText(Prompt prompt) {
        return prompt.getInstructions().stream()
                .map(message -> message.getText() == null ? "" : message.getText())
                .reduce("", (a, b) -> a + " " + b);
    }

    private ChatResponse response(String text) {
        return new ChatResponse(List.of(new Generation(new AssistantMessage(text))));
    }
}
