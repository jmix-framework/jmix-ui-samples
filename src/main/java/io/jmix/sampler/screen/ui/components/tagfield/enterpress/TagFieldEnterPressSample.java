package io.jmix.sampler.screen.ui.components.tagfield.enterpress;

import io.jmix.core.Metadata;
import io.jmix.sampler.entity.ProductTag;
import io.jmix.ui.Notifications;
import io.jmix.ui.component.HasEnterPressHandler;
import io.jmix.ui.component.TagField;
import io.jmix.ui.screen.*;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("tagfield-enterpress")
@UiDescriptor("tagfield-enterpress.xml")
public class TagFieldEnterPressSample extends ScreenFragment {

    @Autowired
    private TagField<ProductTag> tagFieldNewItem;

    @Autowired
    private Metadata metadata;
    @Autowired
    private Notifications notifications;

    @Subscribe
    public void onInit(InitEvent event) {
        tagFieldNewItem.setEnterPressHandler(new TagField.NewTagProvider<ProductTag>() {
            @Nullable
            @Override
            public ProductTag create(String text) {
                ProductTag tag = metadata.create(ProductTag.class);
                tag.setName(text);
                return tag;
            }
        });
    }

    @Install(to = "tagFieldEnterHandler", subject = "enterPressHandler")
    private void tagFieldEnterHandlerEnterPressHandler(HasEnterPressHandler.EnterPressEvent enterPressEvent) {
        notifications.create(Notifications.NotificationType.HUMANIZED)
                .withCaption("Entered text: " + enterPressEvent.getText())
                .show();
    }
}
