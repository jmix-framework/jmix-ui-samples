package io.jmix.sampler.screen.ui.components.datagrid.detailsgenerator;

import io.jmix.core.*;
import io.jmix.core.metamodel.model.MetaClass;
import io.jmix.sampler.entity.OrderItem;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component("sampler_DataGridDetailsGeneratorService")
public class DataGridDetailsGeneratorService {

    protected final DataManager dataManager;
    protected final Metadata metadata;
    protected final FetchPlanRepository fetchPlanRepository;
    protected final FetchPlans fetchPlans;

    public DataGridDetailsGeneratorService(DataManager dataManager,
                                           Metadata metadata,
                                           FetchPlanRepository fetchPlanRepository,
                                           FetchPlans fetchPlans) {
        this.dataManager = dataManager;
        this.metadata = metadata;
        this.fetchPlanRepository = fetchPlanRepository;
        this.fetchPlans = fetchPlans;
    }

    public List<OrderItem> loadOrderItemsById(UUID orderId) {
        MetaClass metaClass = metadata.getClass(OrderItem.class);
        LoadContext.Query query = new LoadContext.Query("select e from sampler_OrderItem e where e.order.id = :orderId")
                .setParameter("orderId", orderId);
        FetchPlan fetchPlan = fetchPlans.builder(OrderItem.class)
                .addFetchPlan(fetchPlanRepository.getFetchPlan(OrderItem.class, "_local"))
                .add("product", "_local")
                .add("order", "_local")
                .build();
        LoadContext<OrderItem> lc = new LoadContext<>(metaClass);
        lc.setQuery(query);
        lc.setFetchPlan(fetchPlan);
        return dataManager.loadList(lc);
    }
}
