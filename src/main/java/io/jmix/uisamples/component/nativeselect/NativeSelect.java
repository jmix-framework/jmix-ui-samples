package io.jmix.uisamples.component.nativeselect;

import com.vaadin.flow.component.AbstractSinglePropertyField;
import com.vaadin.flow.component.Focusable;
import com.vaadin.flow.component.HasAriaLabel;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.ItemLabelGenerator;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.data.provider.KeyMapper;
import com.vaadin.flow.dom.Element;
import com.vaadin.flow.shared.Registration;
import io.jmix.core.common.event.Subscription;
import io.jmix.flowui.data.SupportsItemsContainer;
import io.jmix.flowui.data.SupportsValueSource;
import io.jmix.flowui.data.ValueSource;
import io.jmix.flowui.data.binding.impl.AbstractValueBinding;
import io.jmix.flowui.data.binding.impl.FieldValueBinding;
import io.jmix.flowui.model.CollectionContainer;
import org.jspecify.annotations.Nullable;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Tag("select")
public class NativeSelect<V> extends AbstractSinglePropertyField<NativeSelect<V>, V>
        implements HasSize, HasAriaLabel, Focusable<NativeSelect<V>>, SupportsValueSource<V>,
        SupportsItemsContainer<V>, ApplicationContextAware {

    private static final String EMPTY_KEY = "";

    @Nullable
    private ApplicationContext applicationContext;

    private final KeyMapper<V> keyMapper = new KeyMapper<>();
    private List<V> items = List.of();

    private ItemLabelGenerator<V> itemLabelGenerator = String::valueOf;
    @Nullable
    private ItemLabelGenerator<V> groupLabelGenerator;

    @Nullable
    private AbstractValueBinding<V> valueBinding;
    @Nullable
    private Registration itemsContainerRegistration;
    private boolean updatingPresentation;

    public NativeSelect() {
        super("value", null, String.class, NativeSelect::toItem, NativeSelect::toKey);

        setSynchronizedEvent("change");
        addClassName("native-select");

        renderOptions();
    }

    public List<V> getItems() {
        return items;
    }

    public void setItems(Collection<V> items) {
        this.items = List.copyOf(items);
        keyMapper.removeAll();
        renderOptions();
    }

    @Override
    public void setItems(CollectionContainer<V> container) {
        if (itemsContainerRegistration != null) {
            itemsContainerRegistration.remove();
        }

        Subscription collectionChange = container.addCollectionChangeListener(event -> setItems(container.getItems()));
        Subscription itemPropertyChange = container.addItemPropertyChangeListener(event -> renderOptions());
        itemsContainerRegistration = Registration.combine(collectionChange::remove, itemPropertyChange::remove);

        setItems(container.getItems());
    }

    public void setItemLabelGenerator(ItemLabelGenerator<V> itemLabelGenerator) {
        this.itemLabelGenerator = itemLabelGenerator;
        renderOptions();
    }

    public void setGroupLabelGenerator(@Nullable ItemLabelGenerator<V> groupLabelGenerator) {
        this.groupLabelGenerator = groupLabelGenerator;
        renderOptions();
    }

    @Override
    public void setReadOnly(boolean readOnly) {
        super.setReadOnly(readOnly);
        getElement().setAttribute("readonly", readOnly);

        renderOptions();
    }

    @Override
    public void setRequiredIndicatorVisible(boolean requiredIndicatorVisible) {
        super.setRequiredIndicatorVisible(requiredIndicatorVisible);
        renderOptions();
    }

    @Nullable
    @Override
    public ValueSource<V> getValueSource() {
        return valueBinding != null ? valueBinding.getValueSource() : null;
    }

    @Override
    public void setValueSource(@Nullable ValueSource<V> valueSource) {
        if (valueBinding != null) {
            valueBinding.unbind();
            valueBinding = null;
        }

        if (valueSource != null) {
            valueBinding = createValueBinding(valueSource);
            valueBinding.bind();
            valueBinding.activate();
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @SuppressWarnings("unchecked")
    protected AbstractValueBinding<V> createValueBinding(ValueSource<V> valueSource) {
        return Objects.requireNonNull(applicationContext)
                .getBean(FieldValueBinding.class, valueSource, this);
    }

    @Override
    protected void setPresentationValue(@Nullable V value) {
        updatingPresentation = true;
        try {
            super.setPresentationValue(value);
        } finally {
            updatingPresentation = false;
        }
    }

    @Override
    protected boolean hasValidValue() {
        String key = getElement().getProperty("value", EMPTY_KEY);
        return !updatingPresentation && (key.isEmpty() || keyMapper.containsKey(key));
    }

    protected void renderOptions() {
        items.forEach(keyMapper::key);
        String selectedKey = toKey(getValue());

        getElement().removeAllChildren();
        getElement().appendChild(createOption(EMPTY_KEY, "", selectedKey));

        Map<String, Element> groups = new HashMap<>();
        for (V item : items) {
            Element option = createOption(keyMapper.key(item), itemLabelGenerator.apply(item), selectedKey);

            String groupLabel = groupLabelGenerator != null ? groupLabelGenerator.apply(item) : null;
            if (groupLabel != null) {
                groups.computeIfAbsent(groupLabel, this::createGroup).appendChild(option);
            } else {
                getElement().appendChild(option);
            }
        }

        setPresentationValue(getValue());
    }

    protected Element createGroup(String label) {
        Element group = new Element("optgroup").setAttribute("label", label);
        getElement().appendChild(group);
        return group;
    }

    protected Element createOption(String key, String label, String selectedKey) {
        return new Element("option")
                .setAttribute("value", key)
                .setAttribute("selected", key.equals(selectedKey))
                .setAttribute("disabled", isReadOnly() || key.isEmpty() && isRequiredIndicatorVisible())
                .setText(label);
    }

    @Nullable
    protected V toItem(String key) {
        return keyMapper.get(key);
    }

    protected String toKey(@Nullable V item) {
        return item != null && keyMapper.has(item) ? keyMapper.key(item) : EMPTY_KEY;
    }
}
