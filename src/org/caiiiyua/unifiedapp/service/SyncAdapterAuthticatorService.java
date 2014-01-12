package org.caiiiyua.unifiedapp.service;

import org.caiiiyua.unifiedapp.content.SyncAdapterAuthticator;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class SyncAdapterAuthticatorService extends Service {

    private SyncAdapterAuthticator mAdapterAuthticator;

    @Override
    public void onCreate() {
        mAdapterAuthticator = new SyncAdapterAuthticator(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mAdapterAuthticator.getIBinder();
    }

}
