package io.jmix.sampler.screen.ui.components.tagfield.simple;

import io.jmix.ui.screen.Install;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@UiController("tagfield-simple")
@UiDescriptor("tagfield-simple.xml")
public class TagFieldSimpleSample extends ScreenFragment {

    private List<String> tags = Arrays.asList("New", "Bestseller", "Guarantee", "Discount");

    @Install(to = "tagField", subject = "searchExecutor")
    private List<String> tagFieldSearchExecutor(String searchString, Map<String, Object> searchParams) {
        return tags.stream()
                .filter(tag -> StringUtils.containsIgnoreCase(tag, searchString))
                .collect(Collectors.toList());
    }
}
