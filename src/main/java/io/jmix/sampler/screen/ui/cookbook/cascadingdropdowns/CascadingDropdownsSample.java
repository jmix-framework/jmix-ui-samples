package io.jmix.sampler.screen.ui.cookbook.cascadingdropdowns;

import io.jmix.sampler.entity.Moon;
import io.jmix.sampler.entity.Planet;
import io.jmix.ui.component.EntityComboBox;
import io.jmix.ui.component.HasValue;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("cascading-dropdowns")
@UiDescriptor("cascading-dropdowns.xml")
public class CascadingDropdownsSample extends ScreenFragment {

    @Autowired
    private EntityComboBox<Moon> moonsComboBox;

    @Subscribe("planetsComboBox")
    public void onPlanetsComboBoxValueChange(HasValue.ValueChangeEvent<Planet> event) {
        moonsComboBox.setValue(null);
    }
}