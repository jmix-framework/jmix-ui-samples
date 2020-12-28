package io.jmix.sampler.config;

import io.jmix.core.Messages;
import io.jmix.core.Resources;
import io.jmix.core.common.xmlparsing.Dom4jTools;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.text.StringTokenizer;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Component("sampler_MenuConfig")
public class SamplerMenuConfig {

    public static final String MENU_CONFIG_XML_PROP = "jmix.sampler.menuConfig";

    private static final Log log = LogFactory.getLog(SamplerMenuConfig.class);

    protected List<SamplerMenuItem> rootItems = new ArrayList<>();

    protected volatile boolean initialized;
    protected final ReadWriteLock lock = new ReentrantReadWriteLock();

    @Autowired
    protected Dom4jTools dom4JTools;
    @Autowired
    protected Environment environment;
    @Autowired
    protected Resources resources;
    @Autowired
    protected Messages messages;

    public String getMenuItemCaption(String id) {
        try {
            return messages.getMessage("sampler-menu-config." + id);
        } catch (MissingResourceException e) {
            return id;
        }
    }

    protected void checkInitialized() {
        if (!initialized) {
            lock.readLock().unlock();
            lock.writeLock().lock();
            try {
                if (!initialized) {
                    init();
                    initialized = true;
                }
            } finally {
                lock.readLock().lock();
                lock.writeLock().unlock();
            }
        }
    }

    protected void init() {
        rootItems.clear();

        String configName = environment.getProperty(MENU_CONFIG_XML_PROP);
        StringTokenizer tokenizer = new StringTokenizer(configName);
        for (String location : tokenizer.getTokenArray()) {
            Resource resource = resources.getResource(location);
            if (resource.exists()) {
                try (InputStream stream = resource.getInputStream()) {
                    loadMenuItems(dom4JTools.readDocument(stream).getRootElement(), null);
                } catch (IOException e) {
                    throw new RuntimeException("Unable to read samples menu config from " + location, e);
                }
            } else {
                log.warn("Resource " + location + " not found, ignore it");
            }
        }
    }

    /**
     * Make the config to reload screens on next request.
     */
    public void reset() {
        initialized = false;
    }

    /**
     * Main menu root items
     */
    public List<SamplerMenuItem> getRootItems() {
        lock.readLock().lock();
        try {
            checkInitialized();
            return Collections.unmodifiableList(rootItems);
        } finally {
            lock.readLock().unlock();
        }
    }

    protected void loadMenuItems(Element parentElement, SamplerMenuItem parentItem) {
        for (Element element : parentElement.elements()) {
            SamplerMenuItem menuItem = null;
            String id = element.attributeValue("id");
            if (StringUtils.isNotBlank(id)) {
                if ("menu".equals(element.getName())) {
                    menuItem = new SamplerMenuItem(parentItem, id);
                    menuItem.setImage(element.attributeValue("image"));
                    menuItem.setMenu(true);

                    loadMenuItems(element, menuItem);
                } else if ("item".equals(element.getName())) {
                    menuItem = parseItem(element, parentItem, id);
                } else {
                    log.warn(String.format("Unknown tag '%s' in sample-config", element.getName()));
                }
            } else {
                log.warn("Invalid sample-config: 'id' attribute not defined for tag" + element.getName());
            }

            if (parentItem != null && menuItem != null) {
                parentItem.addChild(menuItem);
            } else {
                rootItems.add(menuItem);
            }
        }
    }

    protected SamplerMenuItem parseItem(Element element, SamplerMenuItem parentItem, String id) {
        SamplerMenuItem menuItem = new SamplerMenuItem(parentItem, id);
        String docUrl = element.attributeValue("docUrlSuffix");
        if (StringUtils.isNotBlank(docUrl)) {
            menuItem.setUrl(docUrl);
        }

        String splitEnabled = element.attributeValue("splitEnabled");
        if (StringUtils.isNotBlank(splitEnabled)) {
            menuItem.setSplitEnabled(splitEnabled);
        }

        String image = element.attributeValue("image");
        if (StringUtils.isNotBlank(image)) {
            menuItem.setImage(image);
        }

        Element otherFilesElement = element.element("otherFiles");
        if (otherFilesElement != null && !otherFilesElement.elements().isEmpty()) {
            List<String> otherFiles = new ArrayList<>();
            for (Element file : otherFilesElement.elements()) {
                String fileName = file.attributeValue("name");
                if (StringUtils.isNotEmpty(fileName))
                    otherFiles.add(fileName);
            }
            menuItem.setOtherFiles(otherFiles);
        }

        Element screenParamsElement = element.element("screenParams");
        if (screenParamsElement != null && !screenParamsElement.elements().isEmpty()) {
            Map<String, Object> params = new HashMap<>();
            for (Element param : screenParamsElement.elements()) {
                String paramName = param.attributeValue("name");
                if (StringUtils.isNotEmpty(paramName)) {
                    String value = param.attributeValue("value");
                    params.put(paramName, value);
                }
            }
            menuItem.setScreenParams(params);
        }

        return menuItem;
    }

    public SamplerMenuItem getItemById(String id) {
        SamplerMenuItem menuItem = IterableUtils.find(getItemsAsList(), new SamplerMenuItemPredicate(id));
        if (menuItem == null) {
            throw new IllegalArgumentException("Unable to find item with id " + id);
        }
        return menuItem;
    }

    @Nullable
    public SamplerMenuItem findItemById(String id) {
        return IterableUtils.find(getItemsAsList(), new SamplerMenuItemPredicate(id));
    }

    protected List<SamplerMenuItem> getItemsAsList() {
        return getItemsAsList(getRootItems());
    }

    protected List<SamplerMenuItem> getItemsAsList(List<SamplerMenuItem> allItems) {
        List<SamplerMenuItem> items = new ArrayList<>();
        for (SamplerMenuItem item : allItems) {
            items.add(item);
            if (item.isMenu())
                items.addAll(getItemsAsList(item.getChildren()));
        }
        return items;
    }

    /**
     * @param itemId id of parent item that contains children
     * @return List of items.
     */
    public List<SamplerMenuItem> getAllChildrenAsList(String itemId) {
        SamplerMenuItem item = getItemById(itemId);
        List<SamplerMenuItem> items = getItemsAsList(Collections.singletonList(item));
        return setCategoriesForSingleItems(items);
    }

    protected List<SamplerMenuItem> setCategoriesForSingleItems(List<SamplerMenuItem> items) {
        for (int i = 0; i < items.size(); i++) {
            SamplerMenuItem item = items.get(i);
            if (item.isMenu()) {
                List<SamplerMenuItem> withoutChildrenList = getItemsWithoutChildren(item.getChildren());
                if (!withoutChildrenList.isEmpty()) {
                    for (SamplerMenuItem menuItem : withoutChildrenList) {
                        int index = items.indexOf(menuItem);
                        SamplerMenuItem itemLabel = new SamplerMenuItem(menuItem.getParent(), menuItem.getId());
                        itemLabel.setMenu(true);
                        items.add(index, itemLabel);
                    }
                }
            }
        }
        return items;
    }

    protected List<SamplerMenuItem> getItemsWithoutChildren(List<SamplerMenuItem> items) {
        List<SamplerMenuItem> itemList = new ArrayList<>();
        for (SamplerMenuItem item : items) {
            if (!item.isMenu())
                itemList.add(item);
        }
        if (itemList.size() == items.size()) {
            return Collections.emptyList();
        }
        return itemList;
    }

    public boolean isRootItem(String itemId) {
        List<SamplerMenuItem> rootsItem = getRootItems();
        return rootsItem.stream().anyMatch(menuItem -> menuItem.getId().equals(itemId));
    }
}
