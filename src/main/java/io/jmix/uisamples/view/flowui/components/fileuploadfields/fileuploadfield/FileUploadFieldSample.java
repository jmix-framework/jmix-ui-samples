package io.jmix.uisamples.view.flowui.components.fileuploadfields.fileuploadfield;

import com.vaadin.flow.component.notification.NotificationVariant;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.component.upload.FileStorageUploadField;
import io.jmix.flowui.kit.component.upload.event.FileUploadSucceededEvent;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

@ViewController("file-upload-field")
@ViewDescriptor("file-upload-field.xml")
public class FileUploadFieldSample extends StandardView {

    @Autowired
    private Notifications notifications;

    @Subscribe("fileUploadField")
    public void onFileUploadFieldFileUploadSucceeded(final FileUploadSucceededEvent<FileStorageUploadField, byte[]> event) {
        notifications.create("Your file %s has been uploaded successfully.".formatted(event.getFileName()))
                .withThemeVariant(NotificationVariant.LUMO_PRIMARY)
                .show();
    }
}
