package io.jmix.uisamples.view.flowui.aichat.messagelist.simple;

import io.jmix.aichat.component.messagelist.AiMessageList;
import io.jmix.aichat.component.messagelist.AiMessageListItem;
import io.jmix.flowui.view.MessageBundle;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.Subscribe;
import io.jmix.flowui.view.ViewComponent;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@ViewController("ai-message-list-simple")
@ViewDescriptor("ai-message-list-simple.xml")
public class AiMessageListSimpleSample extends StandardView {

    private static final String ANSWER = """
            ## Loading orders

            Use `DataManager` with a JPQL query:

            ```java
            List<Order> orders = dataManager.load(Order.class)
                    .query("select o from Order o where o.customer = :customer")
                    .parameter("customer", customer)
                    .list();
            ```

            A few things to keep in mind:

            - add a fetch plan when you need referenced entities
            - use `maxResults` to page the result
            """;

    @ViewComponent
    private AiMessageList messageList;
    @ViewComponent
    private MessageBundle messageBundle;

    @Subscribe
    public void onInit(final InitEvent event) {
        messageList.setItems(
                AiMessageListItem.user(messageBundle.getMessage("transcript.question")),
                AiMessageListItem.assistant(ANSWER),
                AiMessageListItem.user(messageBundle.getMessage("transcript.longQuestion"))
        );
    }
}
