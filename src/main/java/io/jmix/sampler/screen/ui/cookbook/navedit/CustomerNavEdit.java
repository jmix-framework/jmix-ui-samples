package io.jmix.sampler.screen.ui.cookbook.navedit;

import io.jmix.core.EntityStates;
import io.jmix.core.common.event.Subscription;
import io.jmix.sampler.entity.Customer;
import io.jmix.ui.Dialogs;
import io.jmix.ui.action.Action;
import io.jmix.ui.action.BaseAction;
import io.jmix.ui.action.DialogAction;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.ListComponent;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.model.DataContext;
import io.jmix.ui.model.InstanceLoader;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("CustomerNavEdit")
@UiDescriptor("customer-nav-edit.xml")
@EditedEntityContainer("customerDc")
public class CustomerNavEdit extends StandardEditor<Customer> {

    // Collection container for navigation through its content
    private CollectionContainer<Customer> collectionContainer;

    // A table or another list component for selecting current instance after navigation
    private ListComponent<Customer> listComponent;

    private Subscription postCommitSubscription;
    private boolean wasNewEntity;

    @Autowired
    private DataContext dataContext;
    @Autowired
    private Button nextBtn;
    @Autowired
    private Button prevBtn;
    @Autowired
    private InstanceLoader<Customer> customerDl;
    @Autowired
    private Dialogs dialogs;
    @Autowired
    private EntityStates entityStates;

    // Must be called after creating the editor
    public void setCollectionContainer(CollectionContainer<Customer> collectionContainer) {
        this.collectionContainer = collectionContainer;
    }

    // Must be called after creating the editor
    public void setListComponent(ListComponent<Customer> listComponent) {
        this.listComponent = listComponent;
    }

    @Subscribe("nextBtn")
    public void onNextBtnClick(Button.ClickEvent event) {
        checkChanges(this::gotoNext);
    }

    @Subscribe("prevBtn")
    public void onPrevBtnClick(Button.ClickEvent event) {
        checkChanges(this::gotoPrev);
    }

    @Subscribe("newBtn")
    public void onNewBtnClick(Button.ClickEvent event) {
        checkChanges(this::newEntity);
    }

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        addDataContextPostCommitListener();
        wasNewEntity = entityStates.isNew(getEditedEntity());
        if (collectionContainer.getItemIndex(getEditedEntity()) <= 0)
            prevBtn.setEnabled(false);
        if (collectionContainer.getItemIndex(getEditedEntity()) >= collectionContainer.getItems().size() - 1)
            nextBtn.setEnabled(false);
    }

    // Checks if the edited entity is changed, shows a dialog to save or discard the changes,
    // and then invokes the passed method to continue
    private void checkChanges(Runnable gotoMethod) {
        if (dataContext.hasChanges()) {
            dialogs.createOptionDialog()
                    .withCaption("You have unsaved changes")
                    .withMessage("Do you want to save changes?")
                    .withActions(
                            new DialogAction(DialogAction.Type.OK, Action.Status.PRIMARY)
                                    .withCaption("Save")
                                    .withHandler(e -> {
                                        commitChanges()
                                                .then(() -> {
                                                    if (isShowSaveNotification())
                                                        showSaveNotification();
                                                })
                                                .then(gotoMethod);
                                    }),
                            new BaseAction("discard")
                                    .withCaption("Don't save")
                                    .withHandler(e -> {
                                        dataContext.evictModified();
                                        setModifiedAfterOpen(false);
                                        gotoMethod.run();
                                    }),
                            new DialogAction(DialogAction.Type.CANCEL)
                    )
                    .show();
        } else {
            gotoMethod.run();
        }
    }

    // Navigates to the next instance in the collection container
    private void gotoNext() {
        dataContext.clear();

        int currentIdx = collectionContainer.getItemIndex(getEditedEntity());
        if (currentIdx < collectionContainer.getItems().size() - 1) {
            editEntity(++currentIdx);
            prevBtn.setEnabled(true);
        }
        if (currentIdx == collectionContainer.getItems().size() - 1) {
            nextBtn.setEnabled(false);
        }
    }

    // Navigates to the previous instance in the collection container
    private void gotoPrev() {
        dataContext.clear();

        int currentIdx = collectionContainer.getItemIndex(getEditedEntity());
        if (currentIdx == -1) { // If discarded a new instance
            editEntity(0);
            nextBtn.setEnabled(true);
            prevBtn.setEnabled(false);
        }
        if (currentIdx > 0) {
            editEntity(--currentIdx);
            nextBtn.setEnabled(true);
        }
        if (currentIdx == 0) {
            prevBtn.setEnabled(false);
        }
    }

    // Sets up the screen for editing an entity by its index in the collection container
    private void editEntity(int idx) {
        setEntityToEdit(collectionContainer.getItems().get(idx));
        setupEntityToEdit();
        customerDl.load();
        selectInList(collectionContainer.getItems().get(idx));
        wasNewEntity = false;
        addDataContextPostCommitListener();
    }

    // Sets up the screen for editing a new entity instance
    private void newEntity() {
        Customer customer = dataContext.create(Customer.class);
        setEntityToEdit(customer);
        setupEntityToEdit();
        wasNewEntity = true;
        addDataContextPostCommitListener();
    }

    // Adds a listener to process the changed entity after saving it
    private void addDataContextPostCommitListener() {
        removeDataContextPostCommitListener();
        postCommitSubscription = dataContext.addPostCommitListener(postCommitEvent -> {
            postCommitEvent.getCommittedInstances()
                    .optional(getEditedEntity())
                    .ifPresent(savedEntity -> {
                        if (wasNewEntity) {
                            collectionContainer.getMutableItems().add(0, savedEntity);
                            selectInList(savedEntity);
                        } else {
                            collectionContainer.replaceItem(savedEntity);
                        }
                    });
            removeDataContextPostCommitListener();
        });
    }

    private void selectInList(Customer savedEntity) {
        if (listComponent != null)
            listComponent.setSelected(savedEntity);
    }

    private void removeDataContextPostCommitListener() {
        if (postCommitSubscription != null) {
            postCommitSubscription.remove();
            postCommitSubscription = null;
        }
    }
}