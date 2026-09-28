package io.jmix.uisamples.view.flowui.components.svg;

import com.vaadin.flow.component.AbstractField.ComponentValueChangeEvent;
import com.vaadin.flow.component.Svg;
import io.jmix.flowui.component.slider.JmixIntegerSlider;
import io.jmix.flowui.view.*;

import java.util.Locale;

@ViewController("svg")
@ViewDescriptor("svg.xml")
public class SvgSample extends StandardView {

    private static final String PROGRESS_SVG = """
            <svg xmlns="http://www.w3.org/2000/svg" width="120" height="120" viewBox="0 0 120 120">
                <circle cx="60" cy="60" r="50" fill="none"
                        stroke="currentColor" stroke-opacity="0.15" stroke-width="12"/>
                <circle cx="60" cy="60" r="50" fill="none"
                        stroke="%s" stroke-width="12" pathLength="100" stroke-dasharray="%d 100"
                        transform="rotate(-90 60 60)"/>
                <text x="60" y="60" text-anchor="middle" dominant-baseline="central"
                      font-size="24" font-weight="600" fill="currentColor">%d%%</text>
            </svg>
            """;

    @ViewComponent
    private Svg progressSvg;
    @ViewComponent
    private JmixIntegerSlider progressSlider;

    @Subscribe
    public void onInit(InitEvent event) {
        updateProgress(progressSlider.getValue());
    }

    @Subscribe("progressSlider")
    public void onProgressSliderValueChange(ComponentValueChangeEvent<JmixIntegerSlider, Integer> event) {
        updateProgress(event.getValue());
    }

    private void updateProgress(int value) {
        progressSvg.setSvg(String.format(Locale.ROOT, PROGRESS_SVG, getProgressColor(value), value, value));
    }

    private String getProgressColor(int value) {
        if (value < 30) {
            return "#e53935";
        }

        return value < 70 ? "#fb8c00" : "#43a047";
    }
}
