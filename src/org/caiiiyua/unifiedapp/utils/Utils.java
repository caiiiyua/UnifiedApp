package org.caiiiyua.unifiedapp.utils;

import org.caiiiyua.unifiedapp.R;

import android.content.res.Resources;

public class Utils {

    /**
     * Returns a boolean indicating whether the table UI should be shown.
     */
    public static boolean useTabletUI(Resources res) {
        return res.getInteger(R.integer.use_tablet_ui) != 0;
    }
}
