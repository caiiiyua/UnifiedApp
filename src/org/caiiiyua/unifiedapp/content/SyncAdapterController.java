package org.caiiiyua.unifiedapp.content;

import org.caiiiyua.unifiedapp.R.id;
import org.caiiiyua.unifiedapp.ui.UIProvider;
import org.caiiiyua.unifiedapp.utils.LogUtils;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;

public class SyncAdapterController {

    // Constants
    // The authority for the sync adapter's content provider
    public static final String AUTHORITY = UIProvider.AUTHORITY;
    // An account type, in the form of a domain name
    public static final String ACCOUNT_TYPE = "org.caiiiyua.unifiedapp.luoo";
    // The account name
    public static final String ACCOUNT = "syncaccount";
    // Instance fields
    Account mAccount;
    // latest volume number
    long mVolumeNum = 0;

    private static final String EXTRA_VOLUME_NUM = "latest-volume";
    // Sync extra for specific sync type
    public static final String EXTRA_SYNC = "sync-requirement";
    public static final String EXTRA_SYNC_FIRST = "sync-first";
    public static final String EXTRA_SYNC_UPDATE = "sync-update";
    public static final String EXTRA_SYNC_MORE = "sync-more";

    public SyncAdapterController(Context context) {
        mAccount = CreateSyncAccount(context);
    }

    public SyncAdapterController(Context context, long volumeNum) {
        mAccount = CreateSyncAccount(context);
        mVolumeNum = volumeNum;
    }

    public void requestSync(String extraSync) {
        LogUtils.d(LogUtils.TAG, "Request a sync with latest volume: %d ..... %s",
                mVolumeNum, extraSync);
        // Pass the settings flags by inserting them in a bundle
        Bundle settingsBundle = new Bundle();
        settingsBundle.putBoolean(
                ContentResolver.SYNC_EXTRAS_MANUAL, true);
        settingsBundle.putBoolean(
                ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        if (!TextUtils.isEmpty(extraSync)) {
            settingsBundle.putString(EXTRA_SYNC, extraSync);
        }
        ContentResolver.requestSync(mAccount, AUTHORITY, settingsBundle);
    }

    public void requestSyncFirst() {
        requestSync(EXTRA_SYNC_FIRST);
    }

    public void requestSyncUpdate(long id) {
        mVolumeNum = id;
        requestSync(EXTRA_SYNC_UPDATE);
    }

    public void requestSyncMore(long id) {
        mVolumeNum = id;
        requestSync(EXTRA_SYNC_MORE);
    }

    /**
     * Create a new dummy account for the sync adapter
     *
     * @param context The application context
     */
    public static Account CreateSyncAccount(Context context) {
        // Create the account type and default account
        Account newAccount = new Account(
                ACCOUNT, ACCOUNT_TYPE);
        // Get an instance of the Android account manager
        AccountManager accountManager =
                (AccountManager) context.getSystemService(
                        context.ACCOUNT_SERVICE);
        /*
         * Add the account and account type, no password or user data
         * If successful, return the Account object, otherwise report an error.
         */
        if (accountManager.addAccountExplicitly (newAccount, null, null)) {
            /*
             * If you don't set android:syncable="true" in
             * in your <provider> element in the manifest,
             * then call context.setIsSyncable(account, AUTHORITY, 1)
             * here.
             */
        } else {
            /*
             * The account exists or some other error occurred. Log this, report it,
             * or handle it internally.
             */
        }
        return newAccount;
    }
}
