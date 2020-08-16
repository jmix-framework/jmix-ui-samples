package io.jmix.sampler.screen.ui.components.datagrid.detailsgenerator;

import io.jmix.core.DataManager;
import io.jmix.core.FetchPlan;
import io.jmix.core.FetchPlanBuilder;
import io.jmix.core.FetchPlanRepository;
import io.jmix.core.LoadContext;
import io.jmix.core.Metadata;
import io.jmix.core.metamodel.model.MetaClass;
import io.jmix.sampler.entity.OrderItem;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component(DataGridDetailsGeneratorService.NAME)
public class DataGridDetailsGeneratorService {
    public static final String NAME = "sampler_DataGridDetailsGeneratorService";

    @Autowired
    protected DataManager dataManager;
    @Autowired
    protected Metadata metadata;
    @Autowired
    protected FetchPlanRepository fetchPlanRepository;
    @Autowired
    protected ObjectProvider<FetchPlanBuilder> fetchPlanBuilder;

    public List<OrderItem> loadOrderItemsById(UUID orderId) {
        MetaClass metaClass = metadata.getClass(OrderItem.class);
        LoadContext.Query query = new LoadContext.Query("select e from sampler_OrderItem e where e.order.id = :orderId")
                .setParameter("orderId", orderId);
        FetchPlan fetchPlan = fetchPlanBuilder.getObject(OrderItem.class)
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
