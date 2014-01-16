package org.caiiiyua.unifiedapp.service;

import java.util.ArrayList;

import org.caiiiyua.unifiedapp.musicplayer.Volume;
import org.caiiiyua.unifiedapp.utils.LogUtils;
import org.caiiiyua.unifiedapp.utils.LuooHelper;

import android.accounts.Account;
import android.app.Service;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.Intent;
import android.content.SyncResult;
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
            long volumeNum = extras.getLong(EXTRA_VOLUME_NUM);
            if (volumeNum <= 0 && !EXTRA_SYNC_FIRST.equals(extraSync)) {
                return;
            }

            if (EXTRA_SYNC_FIRST.equals(extraSync)
                    || EXTRA_SYNC_UPDATE.equals(extraSync)) {
                syncUpdate(getContext(), volumeNum);
            }

            if (EXTRA_SYNC_MORE.equals(extraSync)) {
                syncMore(getContext(), volumeNum);
            }
        }

        private void syncMore(Context context, long oldestNum) {
            long volumeNum = --oldestNum;
            ArrayList<String> loadUrList = new ArrayList<String>();
            for (int i = 0; i < LOAD_PAGE_MAX; i++) {
                loadUrList.add(LuooHelper.URL_BASE + String.valueOf(volumeNum));
                volumeNum--;
            }
            for (String url : loadUrList) {
                Volume volume = generateDataFromServer(context, url);
                volume.insert(context);
            }
        }

        private void syncUpdate(Context context, long latestNum) {
            Volume volume = generateDataFromServer(context, LuooHelper.HOME_URL);
            if (volume.getVolumeNum() > latestNum) {
                volume.insert(context);
            } else {
                // Nothing need to be updated
            }
        }
    }

    private static Volume generateDataFromServer(Context context, String url) {
        Volume volume = new Volume(url);
        volume.initializeMetaInfo();
//        volume.insert(context);
        LogUtils.d(LogUtils.TAG, "onPerformSync generate via %s and got %s",
                url, volume.toString());
        return volume;
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
