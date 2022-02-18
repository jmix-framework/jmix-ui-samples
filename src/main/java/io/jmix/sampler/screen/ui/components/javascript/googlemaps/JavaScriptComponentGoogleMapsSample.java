package io.jmix.sampler.screen.ui.components.javascript.googlemaps;

import elemental.json.JsonArray;
import io.jmix.core.Messages;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.sampler.screen.ui.components.javascript.googlemaps.state.GoogleMapState;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.Form;
import io.jmix.ui.component.JavaScriptComponent;
import io.jmix.ui.component.TextField;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.ScreenValidation;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

@UiController("java-script-component-google-maps")
@UiDescriptor("java-script-component-google-maps.xml")
public class JavaScriptComponentGoogleMapsSample extends ScreenFragment {
    @Autowired
    protected JavaScriptComponent map;
    @Autowired
    private Form markerForm;
    @Autowired
    private TextField<String> markerTitleField;
    @Autowired
    private TextField<Double> markerLngField;
    @Autowired
    private TextField<Double> markerLatField;

    @Autowired
    private ScreenValidation screenValidation;
    @Autowired
    private Messages messages;
    @Autowired
    private Environment environment;
    @Autowired
    private CurrentAuthentication currentAuthentication;

    @Subscribe
    public void onAfterInit(AfterInitEvent event) {
        GoogleMapState state = new GoogleMapState();
        state.lat = 53.225;
        state.lng = 50.195;
        state.zoom = 8;
        state.key = environment.getProperty("jmix.sampler.google-api-key");
        state.language = currentAuthentication.getLocale().getLanguage();
        map.setState(state);

        markerTitleField.setValue(messages.getMessage(JavaScriptComponentGoogleMapsSample.class, "marker"));
        markerLatField.setValue(state.lat);
        markerLngField.setValue(state.lng);

        // Define a function that can be called from client-side
        map.addFunction("onClick", callBackEvent -> {
            JsonArray array = callBackEvent.getArguments();
            markerLatField.setValue(array.getNumber(0));
            markerLngField.setValue(array.getNumber(1));
        });
    }

    @Subscribe("addMarkerBtn")
    public void onAddMarkerBtnClick(Button.ClickEvent event) {
        if (screenValidation.validateUiComponents(markerForm).isEmpty()) {
            // Call a client-side function
            map.callFunction("addMarker", markerLatField.getValue(), markerLngField.getValue(), markerTitleField.getValue());
        }
    }
}
