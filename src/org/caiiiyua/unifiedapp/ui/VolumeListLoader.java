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
        super(context);
        mLastUpdate  = lastUpdate;
        LogUtils.d(LogUtils.TAG, "VolumeListLoader onCreate");
    }

    @Override
    public Cursor loadInBackground() {
        LogUtils.d(LogUtils.TAG, "VolumeListLoader loadInBackgournd start");
        String [] projection = buildVolumeProjection();
        String order = buildVolumeOrder();
        Uri uri = Uri.parse(UIProvider.VOLUME_BASE_URI);
        LogUtils.d(LogUtils.TAG, "VolumeListLoader loadInBackground with uri: %s", uri);
        final Cursor volumes = getContext().getContentResolver().query(uri,
                    projection, null, null, order);
        if (volumes == null) {
            LogUtils.d(LogUtils.TAG, "VolumeListLoader loadInBackground result cursor null");
        }
        LogUtils.d(LogUtils.TAG, "VolumeListLoader loadInBackground end");
        return volumes;
    }

    private String buildVolumeOrder() {
        return UIProvider.VOLUME_COLUMN_VOL_NUM + " ASC";
    }

    private String[] buildVolumeProjection() {
        return UIProvider.VOLUME_PROJECTION;
    }

}
