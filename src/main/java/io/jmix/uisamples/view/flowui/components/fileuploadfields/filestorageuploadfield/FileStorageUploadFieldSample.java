package io.jmix.uisamples.view.flowui.components.fileuploadfields.filestorageuploadfield;

import com.vaadin.flow.component.notification.NotificationVariant;
import io.jmix.core.FileRef;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.component.upload.FileStorageUploadField;
import io.jmix.flowui.kit.component.upload.event.FileUploadSucceededEvent;
import io.jmix.flowui.upload.TemporaryStorage;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.UUID;

@ViewController("file-storage-upload-field")
@ViewDescriptor("file-storage-upload-field.xml")
public class FileStorageUploadFieldSample extends StandardView {

    @ViewComponent
    private FileStorageUploadField fileStorageUploadField;

    @Autowired
    private TemporaryStorage temporaryStorage;
    @Autowired
    private Notifications notifications;

    // This handler is needed only when fileStoragePutMode="MANUAL".
    // In AUTO mode the field automatically moves the uploaded file from temporary storage
    // to the FileStorage and sets the returned FileRef to the entity attribute.
    @Subscribe("fileStorageUploadField")
    public void onFileStorageUploadFieldFileUploadSucceeded(
            final FileUploadSucceededEvent<FileStorageUploadField, TemporaryStorage.FileInfo> event) {
        UUID fileId = event.getData().getId();
        File file = temporaryStorage.getFile(fileId);

        if (file != null) {
            FileRef fileRef = new FileRef("tempStorage", file.getAbsolutePath(), file.getName());
            fileStorageUploadField.setValue(fileRef);
            notifications.create("Your file %s has been uploaded successfully.".formatted(event.getFileName()))
                    .withThemeVariant(NotificationVariant.LUMO_PRIMARY)
                    .show();

            // Remove the uploaded file.
            // In a real-world application you would move the file to FileStorage here using
            // the temporaryStorage.putFileIntoStorage() method.
            temporaryStorage.deleteFile(fileId);
        }
    }
}
