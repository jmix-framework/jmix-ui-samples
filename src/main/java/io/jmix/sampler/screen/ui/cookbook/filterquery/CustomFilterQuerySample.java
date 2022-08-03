package io.jmix.sampler.screen.ui.cookbook.filterquery;

import io.jmix.sampler.entity.Customer;
import io.jmix.ui.component.HasValue;
import io.jmix.ui.component.TextField;
import io.jmix.ui.model.CollectionLoader;
import io.jmix.ui.screen.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

@UiController("custom-filter-query")
@UiDescriptor("custom-filter-query.xml")
public class CustomFilterQuerySample extends ScreenFragment {

    @Autowired
    private TextField<String> nameFilterField;
    @Autowired
    private CollectionLoader<Customer> customersDl;

    @Subscribe(target = Target.PARENT_CONTROLLER)
    public void onBeforeShow(Screen.BeforeShowEvent event) {
        reload();
    }

    @Subscribe("nameFilterField")
    public void onNameFilterFieldValueChange(HasValue.ValueChangeEvent<String> event) {
        reload();
    }

    private void reload() {
        String query = "select e from sampler_Customer e ";
        Map<String, Object> paramsMap = new HashMap<>();
        if (StringUtils.isNotEmpty(nameFilterField.getValue())) {
            query += "where (e.name like :name or e.lastName like :name)";
            paramsMap.put("name", "(?i)%" + nameFilterField.getValue() + "%");
        }
        query += " order by e.name";
        customersDl.setQuery(query);
        customersDl.setParameters(paramsMap);
        customersDl.load();
    }
}