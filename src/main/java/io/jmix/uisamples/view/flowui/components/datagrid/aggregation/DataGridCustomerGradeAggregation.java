package io.jmix.uisamples.view.flowui.components.datagrid.aggregation;

import io.jmix.core.Messages;
import io.jmix.flowui.data.aggregation.AggregationStrategy;
import io.jmix.uisamples.entity.CustomerGrade;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

/**
 * Calculates the most frequent customer grade
 */
public class DataGridCustomerGradeAggregation implements AggregationStrategy<CustomerGrade, String> {

    // tag::aggregation[] sample-hide
    @Autowired
    private Messages messages;

    @Override
    public String aggregate(Collection<CustomerGrade> propertyValues) {
        CustomerGrade mostFrequent = null;
        long max = 0;

        if (CollectionUtils.isNotEmpty(propertyValues)) {
            for (CustomerGrade grade : CustomerGrade.values()) {
                long current = propertyValues.stream()
                        .filter(grade::equals)
                        .count();

                if (current > max) {
                    mostFrequent = grade;
                    max = current;
                }
            }
        }

        if (mostFrequent != null) {
            String key = CustomerGrade.class.getSimpleName() + "." + mostFrequent.name();
            return String.format("%s: %d/%d", messages.getMessage(CustomerGrade.class, key), max, propertyValues.size());
        }

        return "NaN";
    }

    @Override
    public Class<String> getResultClass() {
        return String.class;
    }
    // end::aggregation[] sample-hide
}
