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

package io.jmix.sampler.screen.ui.components.javascript.component;

import java.io.Serializable;

public class TimePickerState implements Serializable {
    public String now;             // hh:mm 24 hour format only, defaults to current time
    public boolean twentyFour;     // Display 24 hour format, defaults to false
    public boolean showSeconds;    // Whether or not to show seconds
}
