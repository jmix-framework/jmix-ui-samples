package io.jmix.uisamples.view.flowui.components.image.dataaware;

import io.jmix.core.Metadata;
import io.jmix.core.Resources;
import io.jmix.flowui.model.InstanceContainer;
import io.jmix.flowui.view.*;
import io.jmix.uisamples.entity.Picture;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.UncheckedIOException;

@ViewController("image-dataaware")
@ViewDescriptor("image-dataaware.xml")
public class ImageDataawareSample extends StandardView {

    private static final String SRC_PATH = "META-INF/resources/icons/jmix-icon.png";

    @ViewComponent
    private InstanceContainer<Picture> pictureDc;

    @Autowired
    private Metadata metadata;
    @Autowired
    private Resources resources;

    @Subscribe
    public void onInit(InitEvent event) {
        Picture picture = metadata.create(Picture.class);

        try {
            picture.setContent(resources.getResource(SRC_PATH).getContentAsByteArray());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        pictureDc.setItem(picture);
    }
}
