package org.caiiiyua.unifiedapp.service;

import java.io.IOException;
import java.util.ArrayList;

import org.caiiiyua.unifiedapp.musicplayer.Volume;
import org.caiiiyua.unifiedapp.parser.VolumeListParser;
import org.caiiiyua.unifiedapp.provider.UnifiedContentProvider.Volumes;
import org.caiiiyua.unifiedapp.ui.UIProvider;
import org.caiiiyua.unifiedapp.utils.LogUtils;
import org.caiiiyua.unifiedapp.utils.LuooHelper;

import android.R.integer;
import android.accounts.Account;
import android.app.Service;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.SyncResult;
import android.database.Cursor;
import android.os.Bundle;
import android.os.IBinder;

public class UnifiedAppSyncAdpaterService extends Service {

    private static final String TAG = "UnifiedAppSyncAdpaterService";
    // Sync extra for specific sync type
    private static final String EXTRA_SYNC = "sync-requirement";
    private static final String EXTRA_VOLUME_NUM = "latest-volume";

    private static final String EXTRA_SYNC_FIRST = "sync-first";
    private static final String EXTRA_SYNC_UPDATE = "sync-update";
    private static final String EXTRA_SYNC_MORE = "sync-more";

    private static final String VOLUME_UPDATE_RECORD = "VolumeUpdates";
    private static final String VOLUME_UPDATE_LATEST = "latest_volume";
    private static final String VOLUME_UPDATE_OLDEST = "oldest_volume";
    private static final String VOLUME_LAST_PAGE = "last_page";

    private static final int LOAD_PAGE_MAX = 3;

    private static class UnifiedAppSyncAdpaterImpl extends AbstractThreadedSyncAdapter {

        public UnifiedAppSyncAdpaterImpl(Context context) {
            super(context, true);
        }

        @Override
        public void onPerformSync(Account account, Bundle extras,
                String authority, ContentProviderClient provider,
                SyncResult syncResult) {
            LogUtils.d(LogUtils.TAG, "onPerformSync for authority: %s and extra: %s",
                    authority, extras.toString());
            String extraSync = extras.getString(EXTRA_SYNC);
//            long volumeNum = extras.getLong(EXTRA_VOLUME_NUM);
//            if (volumeNum <= 0 && !EXTRA_SYNC_FIRST.equals(extraSync)) {
//                return;
//            }

            if (EXTRA_SYNC_FIRST.equals(extraSync)
                    || EXTRA_SYNC_UPDATE.equals(extraSync)) {
                syncUpdate(getContext());
            }

            if (EXTRA_SYNC_MORE.equals(extraSync)) {
                syncMore(getContext());
            }
        }

        private void syncMore(Context context) {
            SharedPreferences record = context.getSharedPreferences(VOLUME_UPDATE_RECORD, 0);
            int volumePage = record.getInt(VOLUME_LAST_PAGE, 1);
            String url = LuooHelper.VOL_PAGE_URL_MORE + String.valueOf(volumePage + 1);
            generateDataFromServer(context, url);
        }

        private void syncUpdate(Context context) {
            generateDataFromServer(context, LuooHelper.VOL_PAGE_URL_UPDATE);
        }
    }

    private static void generateDataFromServer(Context context, String url) {
        boolean update = LuooHelper.VOL_PAGE_URL_UPDATE.equals(url);
        SharedPreferences record = context.getSharedPreferences(VOLUME_UPDATE_RECORD, 0);
        long latest = record.getLong(VOLUME_UPDATE_LATEST, 0);
        long oldest = record.getLong(VOLUME_UPDATE_OLDEST, 0);
        VolumeListParser parser = new VolumeListParser(url);
        try {
            for (Volume volume : parser.parse()) {
                if (!volume.isDuplicate(latest, oldest)) {
                    volume.insert(context);
                }
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        int page = Integer.valueOf(url.charAt(url.length() - 1));
        LogUtils.d(TAG, "Last loaded page number [%d]", page);
        updateOldestAndLatestId(context, update ? 1 : page );
        return;
    }

    private static void updateOldestAndLatestId(Context context, int page) {
        long latest = 0;
        long oldest = 0;
        Cursor cursor = context.getContentResolver().query(Volumes.CONTENT_URI,
                Volumes.VOLUME_NUM_PROJECTION, null, null, UIProvider.VOLUME_COLUMN_VOL_NUM + " ASC");
        if (cursor == null || cursor.isClosed()) return;
        try {
            if (cursor.moveToFirst()) {
                latest = cursor.getLong(0);
            }
            if (cursor.moveToLast()) {
                oldest = cursor.getLong(0);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
                cursor = null;
            }
        }
        SharedPreferences record = context.getSharedPreferences(VOLUME_UPDATE_RECORD, 0);
        Editor updator = record.edit();
        updator.putLong(VOLUME_UPDATE_LATEST, latest);
        updator.putLong(VOLUME_UPDATE_OLDEST, oldest);
        if (page > 1) {
            updator.putInt(VOLUME_LAST_PAGE, page);
        }
        
    }
    private UnifiedAppSyncAdpaterImpl mSyncAdapterImpl = null;

    @Override
    public void onCreate() {
        super.onCreate();
        mSyncAdapterImpl = new UnifiedAppSyncAdpaterImpl(getApplicationContext());
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mSyncAdapterImpl.getSyncAdapterBinder();
    }
}
