package io.jmix.sampler.screen.ui.components.datagrid.aggregatable;

import io.jmix.sampler.entity.CustomerGrade;
import io.jmix.ui.component.data.aggregation.AggregationStrategy;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Collection;

/**
 * Calculate the most frequent customer grade
 */
public class DataGridCustomerGradeAggregation implements AggregationStrategy<CustomerGrade, String> {
    @Override
    public String aggregate(Collection<CustomerGrade> propertyValues) {
        CustomerGrade mostFrequent = null;
        long max = 0;
        if (CollectionUtils.isNotEmpty(propertyValues)) {
            for (CustomerGrade grade : CustomerGrade.values()) {
                long current = propertyValues.stream()
                        .filter(customerGrade -> customerGrade.equals(grade))
                        .count();

                if (current > max) {
                    mostFrequent = grade;
                    max = current;
                }
            }
        }

        if (mostFrequent != null) {
            return String.format("%s: %d/%d", mostFrequent.name(), max, propertyValues.size());
        }

        return null;
    }

    @Override
    public Class<String> getResultClass() {
        return String.class;
    }
}
