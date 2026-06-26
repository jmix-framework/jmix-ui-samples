package io.jmix.uisamples.view.flowui.dialogsandnotifications.dialogs.option;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.notification.NotificationVariant;
import io.jmix.flowui.Dialogs;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.action.DialogAction;
import io.jmix.flowui.kit.action.BaseAction;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.Subscribe;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@ViewController("option-dialog")
@ViewDescriptor("option-dialog.xml")
public class OptionDialogSample extends StandardView {

    @Autowired
    private Dialogs dialogs;
    @Autowired
    private Notifications notifications;

    @Subscribe("okAndCancelButton")
    public void onOkAndCancelButtonClick(ClickEvent<JmixButton> event) {
        dialogs.createOptionDialog()
                .withHeader("Default option dialog")
                .withText("Default actions are here!")
                .withActions(
                        new DialogAction(DialogAction.Type.OK)
                                .withHandler(e ->
                                        notifications.create("OK pressed")
                                                .withThemeVariant(NotificationVariant.SUCCESS)
                                                .show()),
                        new DialogAction(DialogAction.Type.CANCEL))
                .open();
    }

    @Subscribe("allActionsButton")
    public void onAllActionsButtonClick(ClickEvent<JmixButton> event) {
        dialogs.createOptionDialog()
                .withHeader("All option dialog")
                .withText("All actions are here!")
                .withActions(
                        new DialogAction(DialogAction.Type.OK),
                        new DialogAction(DialogAction.Type.CANCEL),
                        new DialogAction(DialogAction.Type.YES),
                        new DialogAction(DialogAction.Type.NO),
                        new DialogAction(DialogAction.Type.CLOSE)
                )
                .withWidth("30em")
                .open();
    }

    @Subscribe("customActionButton")
    public void onCustomActionButtonClick(ClickEvent<JmixButton> event) {
        dialogs.createOptionDialog()
                .withHeader("Custom Action")
                .withText("Custom action are here!")
                .withActions(
                        new BaseAction<>("customAction")
                                .withText("Do something")
                                .withHandler(e ->
                                        notifications.create("Done!")
                                                .withThemeVariant(NotificationVariant.SUCCESS)
                                                .show()),
                        new DialogAction(DialogAction.Type.CANCEL)
                )
                .open();
    }
}
