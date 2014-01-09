package org.caiiiyua.unifiedapp.service;

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
            // TODO Auto-generated method stub
            
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
