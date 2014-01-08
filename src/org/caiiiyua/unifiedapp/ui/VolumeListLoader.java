package org.caiiiyua.unifiedapp.ui;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

public class VolumeListLoader extends AsyncTaskLoader<Cursor> {

    private long mLastUpdate = 0;

    public VolumeListLoader(Context context) {
        super(context);
    }

    public VolumeListLoader(Context context, long lastUpdate) {
        super(context);
        mLastUpdate  = lastUpdate;
    }

    @Override
    public Cursor loadInBackground() {
        String [] projection = buildVolumeProjection();
        String order = buildVolumeOrder();
        Uri uri = Uri.parse(UIProvider.VOLUME_BASE_URI);
        final Cursor volumes = getContext().getContentResolver().query(uri,
                    projection, null, null, order);

        return volumes;
    }

    private String buildVolumeOrder() {
        return UIProvider.VOLUME_COLUMN_VOL_NUM + " DESC";
    }

    private String[] buildVolumeProjection() {
        return UIProvider.VOLUME_PROJECTION;
    }

}
