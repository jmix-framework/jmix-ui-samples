package io.jmix.uisamples.view.flowui.dialogsandnotifications.notifications.themevariant;

import com.google.common.base.Strings;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.component.checkbox.JmixCheckbox;
import io.jmix.flowui.component.combobox.JmixComboBox;
import io.jmix.flowui.component.textfield.JmixIntegerField;
import io.jmix.flowui.component.textfield.TypedTextField;
import io.jmix.flowui.kit.component.ComponentUtils;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;
// sample-hide:start
import io.jmix.uisamples.theme.AppTheme;
import io.jmix.uisamples.theme.ThemeManager;
// sample-hide:end
import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedHashMap;
import java.util.Map;

@ViewController("notification-theme-variant")
@ViewDescriptor("notification-theme-variant.xml")
public class NotificationThemeVariantSample extends StandardView {

    @ViewComponent
    private TypedTextField<String> titleField;
    @ViewComponent
    private TypedTextField<String> messageField;
    @ViewComponent
    private JmixCheckbox closeableField;
    @ViewComponent
    private JmixIntegerField durationField;
    @ViewComponent
    private JmixComboBox<Notification.Position> positionField;
    @ViewComponent
    private JmixComboBox<NotificationVariant> themeVariantField;
    @ViewComponent
    private JmixComboBox<Notifications.Type> typeField;

    @Autowired
    private Notifications notifications;
    // sample-hide:start
    @Autowired
    private ThemeManager themeManager;
    // sample-hide:end

    @Subscribe
    public void onInit(InitEvent event) {
        ComponentUtils.setItemsMap(positionField, getPositionItemsMap());
        ComponentUtils.setItemsMap(themeVariantField, getThemeVariantItemsMap());
        ComponentUtils.setItemsMap(typeField, getTypeItemsMap());

        titleField.setTypedValue("Title");
        messageField.setTypedValue("Message");
    }

    @Subscribe("standardNotificationButton")
    public void onStandardNotificationButtonClick(ClickEvent<JmixButton> event) {
        notifications.show("ThemeVariant: Standard");
    }

    // theme-only:lumo
    @Subscribe("primaryNotificationButton")
    public void onPrimaryNotificationButtonClick(ClickEvent<JmixButton> event) {
        notifications.create("ThemeVariant: Primary")
                .withThemeVariant(NotificationVariant.LUMO_PRIMARY)
                .show();
    }
    // theme-only:lumo:end
    @Subscribe("infoNotificationButton")
    public void onInfoNotificationButtonClick(ClickEvent<JmixButton> event) {
        notifications.create("ThemeVariant: Info")
                .withThemeVariant(NotificationVariant.INFO)
                .show();
    }

    @Subscribe("successNotificationButton")
    public void onSuccessNotificationButtonClick(ClickEvent<JmixButton> event) {
        notifications.create("ThemeVariant: Success")
                .withThemeVariant(NotificationVariant.SUCCESS)
                .show();
    }
    
    @Subscribe("warningNotificationButton")
    public void onWarningNotificationButtonClick(ClickEvent<JmixButton> event) {
        notifications.create("ThemeVariant: Warning")
                .withThemeVariant(NotificationVariant.WARNING)
                .show();
    }

    @Subscribe("errorNotificationButton")
    public void onErrorNotificationButtonClick(ClickEvent<JmixButton> event) {
        notifications.create("ThemeVariant: Error")
                .withThemeVariant(NotificationVariant.ERROR)
                .show();
    }

    // theme-only:lumo
    @Subscribe("contrastNotificationButton")
    public void onContrastNotificationButtonClick(ClickEvent<JmixButton> event) {
        notifications.create("ThemeVariant: Contrast")
                .withThemeVariant(NotificationVariant.LUMO_CONTRAST)
                .show();
    }
    // theme-only:lumo:end
    @Subscribe("defaultTypeNotificationButton")
    public void onDefaultTypeNotificationButtonClick(ClickEvent<JmixButton> event) {
        notifications.create("Type: Default")
                .withType(Notifications.Type.DEFAULT)
                .show();
    }

    @Subscribe("successTypeNotificationButton")
    public void onSuccessTypeNotificationButtonClick(ClickEvent<JmixButton> event) {
        notifications.create("Type: Success")
                .withType(Notifications.Type.SUCCESS)
                .show();
    }

    @Subscribe("warningTypeNotificationButton")
    public void onWarningTypeNotificationButtonClick(ClickEvent<JmixButton> event) {
        notifications.create("Type: Warning")
                .withType(Notifications.Type.WARNING)
                .show();
    }

    @Subscribe("errorTypeNotificationButton")
    public void onErrorTypeNotificationButtonClick(ClickEvent<JmixButton> event) {
        notifications.create("Type: Error")
                .withType(Notifications.Type.ERROR)
                .show();
    }

    @Subscribe("systemTypeNotificationButton")
    public void onSystemTypeNotificationButtonClick(ClickEvent<JmixButton> event) {
        notifications.create("Type: System")
                .withType(Notifications.Type.SYSTEM)
                .show();
    }

    @Subscribe("durationNotificationButton")
    public void onDurationTypeNotificationButtonClick(ClickEvent<JmixButton> event) {
        notifications.create("Duration: 5 sec")
                .withDuration(5000)
                .show();
    }

    @Subscribe("closeableNotificationButton")
    public void onCloseableTypeNotificationButtonClick(ClickEvent<JmixButton> event) {
        notifications.create("Closeable: true")
                .withCloseable(true)
                .show();
    }

    @Subscribe("testButton")
    public void onTestButtonClick(ClickEvent<JmixButton> event) {
        Notifications.NotificationBuilder notificationBuilder;

        String title = titleField.getTypedValue();
        String message = messageField.getTypedValue();

        if (!Strings.isNullOrEmpty(title) && !Strings.isNullOrEmpty(message)) {
            notificationBuilder = notifications.create(title, message);
        } else if (!Strings.isNullOrEmpty(title)) {
            notificationBuilder = notifications.create(title);
        } else if (!Strings.isNullOrEmpty(message)) {
            notificationBuilder = notifications.create("", message);
        } else {
            notificationBuilder = notifications.create("", "");
        }

        notificationBuilder.withCloseable(closeableField.getValue());

        Integer duration = durationField.getValue();
        if (duration != null && duration < 10000 && duration > 0) {
            notificationBuilder.withDuration(duration);
        }

        Notification.Position position = positionField.getValue();
        if (position != null) {
            notificationBuilder.withPosition(position);
        }

        NotificationVariant themeVariant = themeVariantField.getValue();
        if (themeVariant != null) {
            notificationBuilder.withThemeVariant(themeVariant);
        }

        Notifications.Type type = typeField.getValue();
        if (type != null) {
            notificationBuilder.withType(type);
        }

        notificationBuilder.show();
    }

    private Map<Notification.Position, String> getPositionItemsMap() {
        LinkedHashMap<Notification.Position, String> map = new LinkedHashMap<>();
        map.put(Notification.Position.TOP_STRETCH, "Top Stretch");
        map.put(Notification.Position.TOP_START, "Top Start");
        map.put(Notification.Position.TOP_CENTER, "Top Center");
        map.put(Notification.Position.TOP_END, "Top End");
        map.put(Notification.Position.MIDDLE, "Middle");
        map.put(Notification.Position.BOTTOM_START, "Bottom Start");
        map.put(Notification.Position.BOTTOM_CENTER, "Bottom Center");
        map.put(Notification.Position.BOTTOM_END, "Bottom End");
        map.put(Notification.Position.BOTTOM_STRETCH, "Bottom Stretch");
        return map;
    }

    private Map<NotificationVariant, String> getThemeVariantItemsMap() {
        LinkedHashMap<NotificationVariant, String> map = new LinkedHashMap<>();
        if (themeManager.getCurrentTheme() == AppTheme.LUMO) {            // sample-hide
            // theme-only:lumo
        map.put(NotificationVariant.LUMO_PRIMARY, "Primary");
            // theme-only:lumo:end
        }                                                                // sample-hide
        map.put(NotificationVariant.INFO, "Info");
        map.put(NotificationVariant.SUCCESS, "Success");
        map.put(NotificationVariant.WARNING, "Warning");
        map.put(NotificationVariant.ERROR, "Error");
        if (themeManager.getCurrentTheme() == AppTheme.LUMO) {            // sample-hide
            // theme-only:lumo
        map.put(NotificationVariant.LUMO_CONTRAST, "Contrast");
            // theme-only:lumo:end
        }                                                                // sample-hide
        return map;
    }

    private Map<Notifications.Type, String> getTypeItemsMap() {
        LinkedHashMap<Notifications.Type, String> map = new LinkedHashMap<>();
        map.put(Notifications.Type.DEFAULT, "Default");
        map.put(Notifications.Type.SUCCESS, "Success");
        map.put(Notifications.Type.WARNING, "Warning");
        map.put(Notifications.Type.ERROR, "Error");
        map.put(Notifications.Type.SYSTEM, "System");
        return map;
    }
}
