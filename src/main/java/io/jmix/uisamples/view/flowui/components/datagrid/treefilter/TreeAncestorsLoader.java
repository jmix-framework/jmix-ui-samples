package io.jmix.uisamples.view.flowui.components.datagrid.treefilter;

import io.jmix.core.DataManager;
import io.jmix.core.FetchPlan;
import io.jmix.core.Id;
import io.jmix.core.LoadContext;
import io.jmix.flowui.model.CollectionContainer;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Function;

/**
 * Completes the items of a container with their ancestors that are not in the container,
 * so that a tree can show every item under its parents.
 */
@Component
public class TreeAncestorsLoader {

    private final DataManager dataManager;

    public TreeAncestorsLoader(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    /**
     * Returns the container items together with their missing ancestors. The ancestors are loaded
     * with the container fetch plan and placed right before the first item that needs them.
     *
     * @param container      container with the items to show in a tree
     * @param parentProvider function that returns the parent of an item or {@code null} for a root item
     * @param <E>            entity type
     * @return items with their ancestors
     */
    public <E> List<E> withAncestors(CollectionContainer<E> container, Function<E, E> parentProvider) {
        List<E> items = container.getItems();
        Map<Id<E>, E> ancestors = loadMissingAncestors(container, parentProvider);

        Set<Id<E>> addedIds = new HashSet<>();
        for (E item : items) {
            addedIds.add(Id.of(item));
        }

        List<E> result = new ArrayList<>(items.size() + ancestors.size());
        for (E item : items) {
            Deque<E> missingParents = new ArrayDeque<>();
            E parent = parentProvider.apply(item);

            while (parent != null
                    && ancestors.containsKey(Id.of(parent))
                    && addedIds.add(Id.of(parent))) {
                E ancestor = ancestors.get(Id.of(parent));
                missingParents.addFirst(ancestor);
                parent = parentProvider.apply(ancestor);
            }

            result.addAll(missingParents);
            result.add(item);
        }

        return result;
    }

    protected <E> Map<Id<E>, E> loadMissingAncestors(CollectionContainer<E> container,
                                                     Function<E, E> parentProvider) {
        Set<Id<E>> knownIds = new HashSet<>();
        for (E item : container.getItems()) {
            knownIds.add(Id.of(item));
        }

        Map<Id<E>, E> ancestors = new HashMap<>();
        Set<Id<E>> missingIds = getMissingParentIds(container.getItems(), parentProvider, knownIds);
        while (!missingIds.isEmpty()) {
            knownIds.addAll(missingIds);

            List<E> parents = loadByIds(container, missingIds);
            for (E parent : parents) {
                ancestors.put(Id.of(parent), parent);
            }

            missingIds = getMissingParentIds(parents, parentProvider, knownIds);
        }

        return ancestors;
    }

    private <E> Set<Id<E>> getMissingParentIds(Collection<E> entities,
                                               Function<E, E> parentProvider,
                                               Set<Id<E>> knownIds) {
        Set<Id<E>> parentIds = new HashSet<>();
        for (E entity : entities) {
            E parent = parentProvider.apply(entity);

            if (parent != null && !knownIds.contains(Id.of(parent))) {
                parentIds.add(Id.of(parent));
            }
        }

        return parentIds;
    }

    private <E> List<E> loadByIds(CollectionContainer<E> container, Collection<Id<E>> ids) {
        List<Object> idList = ids.stream()
                .map(Id::getValue)
                .toList();

        LoadContext<E> loadContext = new LoadContext<E>(container.getEntityMetaClass())
                .setIds(idList);

        FetchPlan fetchPlan = container.getFetchPlan();
        if (fetchPlan != null) {
            loadContext.setFetchPlan(fetchPlan);
        }

        return dataManager.loadList(loadContext);
    }
}
