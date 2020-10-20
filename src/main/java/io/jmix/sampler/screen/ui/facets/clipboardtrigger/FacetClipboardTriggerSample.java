/*
 * Copyright 2020 Haulmont.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.jmix.sampler.screen.ui.facets.clipboardtrigger;

import io.jmix.ui.Notifications;
import io.jmix.ui.component.ClipboardTrigger;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("facet-clipboard-trigger")
@UiDescriptor("facet-clipboard-trigger.xml")
public class FacetClipboardTriggerSample extends ScreenFragment {

    @Autowired
    protected Notifications notifications;

    @Subscribe("clipboardTrigger")
    protected void onClipboardTrigger(ClipboardTrigger.CopyEvent event) {
        notifications.create()
                .withCaption("Copied to clipboard")
                .show();
    }
}
