package org.caiiiyua.unifiedapp.ui;

import org.caiiiyua.unifiedapp.utils.LogUtils;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.net.Uri;

public class VolumeListLoader extends CursorLoader {

    private long mLastUpdate = 0;

    public VolumeListLoader(Context context) {
        super(context);
    }

    public VolumeListLoader(Context context, long lastUpdate) {
        super(context, Uri.parse(UIProvider.VOLUME_BASE_URI), buildVolumeProjection(), null,
                null, buildVolumeOrder());
        mLastUpdate  = lastUpdate;
        LogUtils.d(LogUtils.TAG, "VolumeListLoader onCreate");
    }

    @Override
    public Cursor loadInBackground() {
        LogUtils.d(LogUtils.TAG, "VolumeListLoader loadInBackgournd start");
        LogUtils.d(LogUtils.TAG, "VolumeListLoader loadInBackground with uri: %s", getUri());
        final Cursor volumes = super.loadInBackground();
        if (volumes == null) {
            LogUtils.d(LogUtils.TAG, "VolumeListLoader loadInBackground result cursor null");
        }
        LogUtils.d(LogUtils.TAG, "VolumeListLoader loadInBackground end");
        return volumes;
    }

    private static String buildVolumeOrder() {
        return UIProvider.VOLUME_COLUMN_VOL_NUM + " ASC";
    }

    private static String[] buildVolumeProjection() {
        return UIProvider.VOLUME_PROJECTION;
    }

}
