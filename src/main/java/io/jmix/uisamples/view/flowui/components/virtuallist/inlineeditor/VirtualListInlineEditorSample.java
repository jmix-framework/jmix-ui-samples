package io.jmix.uisamples.view.flowui.components.virtuallist.inlineeditor;


import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.avatar.AvatarVariant;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.server.streams.DownloadHandler;
import com.vaadin.flow.server.streams.DownloadResponse;
import com.vaadin.flow.server.streams.InputStreamDownloadHandler;
import io.jmix.core.LoadContext;
import io.jmix.core.Messages;
import io.jmix.flowui.DialogWindows;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.model.CollectionContainer;
import io.jmix.flowui.model.DataContext;
import io.jmix.flowui.theme.StyleUtility;
import io.jmix.flowui.view.*;
import io.jmix.uisamples.bean.FoodPlaceholderDataGenerator;
import io.jmix.uisamples.entity.Food;
import io.jmix.uisamples.view.entity.food.FoodDetailView;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayInputStream;
import java.util.List;

@ViewController("virtual-list-inline-editor")
@ViewDescriptor("virtual-list-inline-editor.xml")
public class VirtualListInlineEditorSample extends StandardView {

    @Autowired
    private Messages messages;
    @Autowired
    private DialogWindows dialogWindows;
    @Autowired
    private FoodPlaceholderDataGenerator foodPlaceholderDataGenerator;

    @ViewComponent
    private DataContext dataContext;
    @ViewComponent
    private CollectionContainer<Food> foodDc;

    @Supply(to = "foodList", subject = "renderer")
    private Renderer<Food> foodListRenderer() {
        return new ComponentRenderer<>(item -> {
            HorizontalLayout rootCardLayout = new HorizontalLayout();
            rootCardLayout.setMargin(true);

            VerticalLayout infoLayout = new VerticalLayout();
            infoLayout.setSpacing(false);
            infoLayout.setPadding(false);
            infoLayout.setWidth("30%");

            Avatar avatar = new Avatar();
            avatar.addThemeVariants(AvatarVariant.LUMO_XLARGE);

            if (item.getIcon() != null && item.getIcon().length > 0) {
                String iconFileName = "%s.png".formatted(item.getTitle());
                InputStreamDownloadHandler handler = DownloadHandler.fromInputStream(event -> {
                    byte[] icon = item.getIcon();
                    ByteArrayInputStream inputStream = new ByteArrayInputStream(item.getIcon());

                    return new DownloadResponse(inputStream, iconFileName, "image/png", icon.length);
                });

                avatar.setImageHandler(handler);
            }

            VerticalLayout verticalLayout = new VerticalLayout();
            verticalLayout.setWidthFull();

            HorizontalLayout itemDetailLayout = new HorizontalLayout();
            itemDetailLayout.add(new Text(item.getDescription()));
            itemDetailLayout.add(new Html(
                    messages.formatMessage(getClass(), "foodListItemDescription", item.getPrice()))
            );
            itemDetailLayout.setPadding(false);
            itemDetailLayout.setAlignItems(FlexComponent.Alignment.CENTER);

            infoLayout.add(new Html(messages.formatMessage(getClass(), "foodItemTitle", item.getTitle())));
            infoLayout.add(itemDetailLayout);

            VerticalLayout buttonsPanel = new VerticalLayout();
            buttonsPanel.setWidth("AUTO");
            buttonsPanel.setPadding(false);
            buttonsPanel.setSpacing(false);

            Button detailButton = new Button(new Icon(VaadinIcon.PENCIL));
            detailButton.setText(messages.getMessage("actions.Edit"));
            detailButton.addClickListener(e -> dialogWindows.detail(this, Food.class)
                    .withViewClass(FoodDetailView.class)
                    .editEntity(item)
                    .withAfterCloseListener(closeEvent -> {
                        if (closeEvent.closedWith(StandardOutcome.SAVE)) {
                            foodDc.replaceItem(closeEvent.getSource().getView().getEditedEntity());
                        }
                    })
                    .open());
            detailButton.addClassName(StyleUtility.Button.LINK_BUTTON);

            Button removeButton = new Button(new Icon(VaadinIcon.TRASH));
            removeButton.setText(messages.getMessage("actions.Remove"));
            removeButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
            removeButton.addClassName(StyleUtility.Button.LINK_BUTTON);
            removeButton.addClickListener(e -> {
                foodDc.getMutableItems().remove(item);
                dataContext.remove(dataContext.merge(item));
            });

            buttonsPanel.add(detailButton, removeButton);
            rootCardLayout.add(avatar, infoLayout, buttonsPanel);
            return rootCardLayout;
        });
    }

    @Install(to = "foodDl", target = Target.DATA_LOADER)
    private List<Food> foodDlLoadDelegate(final LoadContext<Food> loadContext) {
        return foodPlaceholderDataGenerator.getFoodSamplesList();
    }

    @Subscribe(id = "addBtn", subject = "clickListener")
    public void onAddBtnClick(final ClickEvent<JmixButton> event) {
        dialogWindows.detail(this, Food.class)
                .withViewClass(FoodDetailView.class)
                .newEntity()
                .withAfterCloseListener(closeEvent -> {
                    if (closeEvent.closedWith(StandardOutcome.SAVE)) {
                        foodDc.replaceItem(closeEvent.getSource().getView().getEditedEntity());
                    }
                })
                .open();
    }
}
