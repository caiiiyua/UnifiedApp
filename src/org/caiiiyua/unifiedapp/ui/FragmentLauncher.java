/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package org.caiiiyua.unifiedapp.ui;

import android.app.Fragment;

/**
 * Interface that permits elements to display a Fragment.
 */
public interface FragmentLauncher {
    /**
     * Launches the specified {@link Fragment}
     *
     * @param selectPosition The position of the list item to select, or -1
     */
    void launchFragment(Fragment fragment, int selectPosition);
}
