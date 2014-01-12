package org.caiiiyua.unifiedapp.service;

import org.caiiiyua.unifiedapp.musicplayer.Volume;
import org.caiiiyua.unifiedapp.utils.LogUtils;

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

    private static class UnifiedAppSyncAdpaterImpl extends AbstractThreadedSyncAdapter {

        public UnifiedAppSyncAdpaterImpl(Context context) {
            super(context, true);
        }

        @Override
        public void onPerformSync(Account account, Bundle extras,
                String authority, ContentProviderClient provider,
                SyncResult syncResult) {
            LogUtils.d(LogUtils.TAG, "onPerformSync for authority: " + authority);
            final String URL_TEST = "http://www.luoo.net/";
            generateDataFromServer(getContext(), URL_TEST);
        }

        
    }

    private static void generateDataFromServer(Context context, String url) {
        Volume volume = new Volume(url);
        volume.initializeMetaInfo();
        volume.insert(context);
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
