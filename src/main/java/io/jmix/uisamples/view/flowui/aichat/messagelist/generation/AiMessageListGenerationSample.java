package io.jmix.uisamples.view.flowui.aichat.messagelist.generation;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.notification.Notification;
import io.jmix.aichat.component.messagelist.AiMessageList;
import io.jmix.aichat.component.messagelist.AiMessageListItem;
import io.jmix.aichat.component.messagelist.GenerationState;
import io.jmix.aichat.component.messagelist.ThinkingStatusItem;
import io.jmix.aichat.component.messagelist.ThinkingStatusPublisher;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.backgroundtask.BackgroundTask;
import io.jmix.flowui.backgroundtask.BackgroundWorker;
import io.jmix.flowui.backgroundtask.TaskLifeCycle;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.MessageBundle;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.Subscribe;
import io.jmix.flowui.view.ViewComponent;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.concurrent.TimeUnit;

@ViewController("ai-message-list-generation")
@ViewDescriptor("ai-message-list-generation.xml")
public class AiMessageListGenerationSample extends StandardView {

    @ViewComponent
    private AiMessageList messageList;
    @ViewComponent
    private MessageBundle messageBundle;

    @Autowired
    private Notifications notifications;
    @Autowired
    private BackgroundWorker backgroundWorker;

    @Subscribe
    public void onInit(final InitEvent event) {
        messageList.addRetryListener(retryEvent -> {
            AiMessageListItem userMessage = retryEvent.getUserMessage();
            notifications.create(messageBundle.getMessage("retry.notification"),
                            userMessage != null ? userMessage.getText() : "")
                    .withPosition(Notification.Position.BOTTOM_END)
                    .show();
            messageList.setState(GenerationState.IDLE);
        });
    }

    @Subscribe("generateButton")
    public void onGenerateButtonClick(final ClickEvent<JmixButton> event) {
        messageList.addItem(AiMessageListItem.user(messageBundle.getMessage("question.text")));

        AiMessageListItem answer = AiMessageListItem.assistant("");
        messageList.addItem(answer);
        messageList.setState(GenerationState.GENERATING);

        backgroundWorker.handle(new AnswerTask(answer)).execute();
    }

    @Subscribe("failButton")
    public void onFailButtonClick(final ClickEvent<JmixButton> event) {
        messageList.addItem(AiMessageListItem.user(messageBundle.getMessage("failedQuestion.text")));
        messageList.setState(GenerationState.ERROR);
    }

    private class AnswerTask extends BackgroundTask<String, Void> {

        private final AiMessageListItem answer;

        // The task body runs off the UI thread, so the text it needs is taken from the
        // message bundle here, while the click that created the task is still being handled
        private final List<String> answerParts = List.of(
                messageBundle.getMessage("answer.part1"),
                messageBundle.getMessage("answer.part2"),
                messageBundle.getMessage("answer.part3"));
        private final String searchingStatus = messageBundle.getMessage("status.searching");
        private final String searchingResult = messageBundle.getMessage("status.searchResult");
        private final String writingStatus = messageBundle.getMessage("status.writing");

        private AnswerTask(AiMessageListItem answer) {
            super(30, TimeUnit.SECONDS, AiMessageListGenerationSample.this);
            this.answer = answer;
        }

        @Override
        public Void run(TaskLifeCycle<String> taskLifeCycle) throws Exception {
            // The publisher takes the UI lock itself, so it is safe from this thread
            ThinkingStatusPublisher statuses = messageList.getThinkingStatusPublisher();

            ThinkingStatusItem searching = statuses.start(searchingStatus);
            Thread.sleep(1200);
            searching.complete(searchingResult);

            ThinkingStatusItem writing = statuses.start(writingStatus);

            for (String part : answerParts) {
                Thread.sleep(400);
                taskLifeCycle.publish(part + " ");
            }

            writing.complete();
            return null;
        }

        @Override
        public void progress(List<String> parts) {
            // progress runs on the UI thread, which is what appendText requires
            parts.forEach(answer::appendText);
        }

        @Override
        public void done(Void result) {
            messageList.setState(GenerationState.IDLE);
            messageList.getThinkingStatusPublisher().clear();
        }
    }
}
