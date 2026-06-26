package io.jmix.uisamples.view.flowui.components.fileuploadfields.upload;

import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.upload.FileRejectedEvent;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.component.upload.UploadSucceededEvent;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.Subscribe;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@ViewController("upload")
@ViewDescriptor("upload.xml")
public class UploadSample extends StandardView {

    @Autowired
    private Notifications notifications;

    @Subscribe("upload")
    public void onUploadSucceeded(final UploadSucceededEvent<byte[]> event) {
        byte[] bytes = event.getData();

        StringBuilder sb = new StringBuilder();
        sb.append("<div>Uploaded file: ").append(event.getFileName());
        sb.append("<p>File content size: ").append(bytes.length).append(" bytes</p>");
        sb.append("</div>");

        notifications.create(new Html(sb.toString()))
                .withThemeVariant(NotificationVariant.LUMO_PRIMARY)
                .withDuration(5000)
                .show();
    }

    @Subscribe("upload")
    public void onUploadFileRejected(final FileRejectedEvent event) {
        notifications.create(event.getErrorMessage())
                .withThemeVariant(NotificationVariant.WARNING)
                .withDuration(5000)
                .show();
    }
}
