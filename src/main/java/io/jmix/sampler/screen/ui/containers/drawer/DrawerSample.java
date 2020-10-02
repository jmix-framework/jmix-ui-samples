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

package io.jmix.sampler.screen.ui.containers.drawer;

import io.jmix.ui.component.Button;
import io.jmix.ui.component.CssLayout;
import io.jmix.ui.component.Label;
import io.jmix.ui.component.mainwindow.Drawer;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("drawer-sample")
@UiDescriptor("drawer-sample.xml")
public class DrawerSample extends ScreenFragment  {

    @Autowired
    private Drawer drawer;
    @Autowired
    private Label<String> contentLabel;
    @Autowired
    private Label<String> headerLabel;

    @Subscribe("toggleDrawerButton")
    private void onToggleDrawer(Button.ClickEvent event) {
        drawer.toggle();
        headerLabel.setVisible(!drawer.isCollapsed());
        contentLabel.setVisible(!drawer.isCollapsed());
    }
}