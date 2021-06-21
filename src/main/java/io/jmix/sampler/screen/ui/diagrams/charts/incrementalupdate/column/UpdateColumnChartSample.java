package io.jmix.sampler.screen.ui.diagrams.charts.incrementalupdate.column;

import io.jmix.core.Metadata;
import io.jmix.sampler.entity.IncomeExpenses;
import io.jmix.ui.component.Button;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@UiController("update-column-chart")
@UiDescriptor("update-column-chart.xml")
public class UpdateColumnChartSample extends ScreenFragment {

    @Autowired
    private CollectionContainer<IncomeExpenses> incomeExpensesDc;
    @Autowired
    private Metadata metadata;

    private int lastYear = 2014;

    @Subscribe("addIncome")
    protected void onAddIncomeClick(Button.ClickEvent event) {
        incomeExpensesDc.getMutableItems().add(generateIncome());
    }

    @Subscribe("removeIncome")
    protected void onRemoveIncomeClick(Button.ClickEvent event) {
        List<IncomeExpenses> items = incomeExpensesDc.getMutableItems();
        if (!items.isEmpty()) {
            items.remove(0);
        }
    }

    private IncomeExpenses generateIncome() {
        IncomeExpenses incomeExpenses = metadata.create(IncomeExpenses.class);
        incomeExpenses.setYear(++lastYear);
        double income = RandomUtils.nextInt(0, 40) + RandomUtils.nextDouble(0, 1);
        incomeExpenses.setIncome(income);
        incomeExpenses.setDashLengthColumn(0);
        return incomeExpenses;
    }
}