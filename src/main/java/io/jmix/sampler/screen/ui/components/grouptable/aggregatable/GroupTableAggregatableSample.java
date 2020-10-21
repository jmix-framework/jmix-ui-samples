package io.jmix.sampler.screen.ui.components.grouptable.aggregatable;

import io.jmix.sampler.entity.Employee;
import io.jmix.sampler.entity.Experience;
import io.jmix.ui.component.GroupTable;
import io.jmix.ui.screen.Install;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;

import java.math.BigDecimal;

@UiController("grouptable-aggregatable")
@UiDescriptor("grouptable-aggregatable.xml")
public class GroupTableAggregatableSample extends ScreenFragment {

    @Install(to = "employeesTable", subject = "aggregationDistributionProvider")
    private void employeesTableAggregationDistributionProvider(GroupTable.GroupAggregationDistributionContext<Employee> context) {
        BigDecimal value = (BigDecimal) context.getValue();
        if (value == null || value.signum() == -1) {
            return;
        }

        long highExperienceCount = context.getScope().stream()
                .filter(employee -> Experience.HIGH.equals(employee.getExperience()))
                .count();
        long commonExperienceCount = context.getScope().size() - highExperienceCount;

        // high value is 60% of total sum
        BigDecimal valuePerHigh = value
                .multiply(BigDecimal.valueOf(0.6))
                .divide(BigDecimal.valueOf(highExperienceCount), BigDecimal.ROUND_DOWN);

        // common value is 40% of total sum
        BigDecimal valuePerCommon = value
                .multiply(BigDecimal.valueOf(0.4))
                .divide(BigDecimal.valueOf(commonExperienceCount), BigDecimal.ROUND_DOWN);

        for (Employee employee : context.getScope()) {
            if (Experience.HIGH.equals(employee.getExperience())) {
                employee.setSalary(valuePerHigh);
            } else {
                employee.setSalary(valuePerCommon);
            }
        }
    }
}
