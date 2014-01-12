package org.caiiiyua.unifiedapp.ui;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;

import org.caiiiyua.unifiedapp.R;
import org.caiiiyua.unifiedapp.content.CursorCreator;
import org.caiiiyua.unifiedapp.content.ObjectCursor;
import org.caiiiyua.unifiedapp.content.SyncAdapterController;
import org.caiiiyua.unifiedapp.musicplayer.Volume;
import org.caiiiyua.unifiedapp.ui.view.ContentListFragment;
import org.caiiiyua.unifiedapp.ui.view.ContentPagerController;
import org.caiiiyua.unifiedapp.ui.view.VolumeListFragment;
import org.caiiiyua.unifiedapp.ui.view.VolumesUpdateListener;
import org.caiiiyua.unifiedapp.utils.LogTag;
import org.caiiiyua.unifiedapp.utils.LogUtils;
import org.caiiiyua.unifiedapp.utils.Utils;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SyncAdapterType;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

public abstract class AbstractActivityController implements ActivityController {

    protected final ControllableActivity mActivity;
    protected final Context mContext;
    private final FragmentManager mFragmentManager;
    /** Handler for all our local runnables. */
    protected Handler mHandler = new Handler();

    /**
     * The current mode of the application. All changes in mode are initiated by
     * the activity controller. View mode changes are propagated to classes that
     * attach themselves as listeners of view mode changes.
     */
    protected final ViewMode mViewMode;
    protected ContentResolver mResolver;

    private boolean mDestroyed;

    /** True if running on tablet */
    private final boolean mIsTablet;

    /**
     * Are we in a point in the Activity/Fragment lifecycle where it's safe to execute fragment
     * transactions? (including back stack manipulation)
     * <p>
     * Per docs in {@link FragmentManager#beginTransaction()}, this flag starts out true, switches
     * to false after {@link Activity#onSaveInstanceState}, and becomes true again in both onStart
     * and onResume.
     */
    private boolean mSafeToModifyFragments = true;

    protected static final String LOG_TAG = LogTag.getLogTag();
    private static final String TAG_VOL_LIST = "tag-vol-list";
    private static final String TAG_CONTENT_LIST = "tag-content-list";
    private static final String TAG_CONTENT_VIEW = "tag-content-view";

    // Loaders of Volumes, Tracks and others
    public static final int LOADER_VOLUME_LIST = 0;
    public static final int LOADER_TRACK_LIST = 1;

    private VolumeLoads mVolumeLoads = new VolumeLoads();
    private boolean mIsDragHappening;

    private final Deque<UpOrBackHandler> mUpOrBackHandlers = 
            new LinkedList<UpOrBackController.UpOrBackHandler>();
    protected ContentPagerController mContentPagerController;
    private View mListView;
    private Cursor mVolumeListCursor;
    private LoaderManager mLoaderManager;
    private SyncAdapterController mSyncAdapterController;

    // cache current list of lastest volumes
    private ArrayList<Volume> mVolumeList = new ArrayList<Volume>();
    private ArrayList<VolumesUpdateListener> mVolumesUpdateListeners
                                  = new ArrayList<VolumesUpdateListener>();

    public AbstractActivityController(ControllableActivity activity, ViewMode viewMode) {
        mActivity = activity;
        mFragmentManager = mActivity.getFragmentManager();
        mLoaderManager = mActivity.getLoaderManager();
        mViewMode = viewMode;
        mContext = activity.getApplicationContext();
        mSyncAdapterController = new SyncAdapterController(mContext);
        // Allow the fragment to observe changes to its own selection set. No other object is
        // aware of the selected set.
//        mSelectedSet.addObserver(this);

        final Resources r = mContext.getResources();
        mIsTablet = Utils.useTabletUI(r);
        mContentPagerController = new ContentPagerController(mFragmentManager, mActivity);
        mListView = mActivity.findViewById(R.id.content_pane);
    }

    @Override
    public void onViewModeChanged(int newMode) {
        // Do nothing
    }

    @Override
    public void onConnectivityStateChange(NetworkInfo networkInfo) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void launchFragment(Fragment fragment, int selectPosition) {
        show(selectPosition);
    }

    @Override
    public void addUpOrBackHandler(UpOrBackHandler handler) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void removeUpOrBackHandler(UpOrBackHandler handler) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onError(int errorCode) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public String getHelpContext() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean onBackPressed() {
        boolean handled = false;
        if (mUpOrBackHandlers != null && mUpOrBackHandlers.size() > 0) {
            for (UpOrBackHandler handler : mUpOrBackHandlers) {
                if (handler.onBackPressed()) {
                    handled = true;
                }
            }
            if (handled) {
                return true;
            }
        }
        return upOrBackHandling();
    }

    private boolean upOrBackHandling() {
        // TODO Auto-generated method stub
        switch (mViewMode.getMode()) {
        case ViewMode.CONTENT_VIEW:
            mContentPagerController.hide(true);
            mViewMode.enterContentListMode();
            return true;

        default:
            return false;
        }
    }

    @Override
    public boolean onUpPressed() {
        boolean handled = false;
        if (mUpOrBackHandlers != null && mUpOrBackHandlers.size() > 0) {
            for (UpOrBackHandler handler : mUpOrBackHandlers) {
                if (handler.onBackPressed()) {
                    handled = true;
                }
            }
            if (handled) {
                return true;
            }
        }
        return upOrBackHandling();
    }

    @Override
    public boolean onCreate(Bundle savedState) {
        LogUtils.d(LogUtils.TAG, "AbstractActivityController onCreate");
        initVolumeList();
        if (savedState == null) {
            // Launch volume list by default
            final VolumeListFragment contentListFragment = VolumeListFragment.newInstance(this);
//            final ContentListFragment  contentListFragment = new ContentListFragment(this);
            replaceFragment(contentListFragment, FragmentTransaction.TRANSIT_FRAGMENT_OPEN,
                    TAG_VOL_LIST, R.id.content_pane);
        }
        return true;
    }

    private void launchFragment(Fragment fragment, String tag) {
        replaceFragment(fragment, FragmentTransaction.TRANSIT_FRAGMENT_OPEN,
                tag, R.id.content_pane);
    }

    /**
     * Replace the content_pane with the fragment specified here. The tag is specified so that
     * the {@link ActivityController} can look up the fragments through the
     * {@link android.app.FragmentManager}.
     * @param fragment the new fragment to put
     * @param transition the transition to show
     * @param tag a tag for the fragment manager.
     * @param anchor ID of view to replace fragment in
     * @return transaction ID returned when the transition is committed.
     */
    private int replaceFragment(Fragment fragment, int transition, String tag, int anchor) {
        final FragmentManager fm = mFragmentManager;
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.setTransition(transition);
        fragmentTransaction.replace(anchor, fragment, tag);
        if (!TAG_VOL_LIST.equals(tag)) {
            fragmentTransaction.addToBackStack(tag);
        }
        final int id = fragmentTransaction.commitAllowingStateLoss();
        fm.executePendingTransactions();
        return id;
    }

    @Override
    public void onPostCreate(Bundle savedState) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onStart() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onRestart() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Dialog onCreateDialog(int id, Bundle bundle) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void onPause() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onPrepareDialog(int id, Dialog dialog, Bundle bundle) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        mActivity.getActionBar().show();
        return true;
    }

    @Override
    public void onResume() {
        if (mViewMode.isUnknownMode()) {
            // enter volume list by default
            mViewMode.enterVolumeListMode();
        }
    }

    @Override
    public Cursor getVolumeListCursor() {
        return mVolumeListCursor;
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void executeSearch(String query) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onStop() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void showWaitForInitialization() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void startSearch() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void exitSearchMode() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void startDragMode() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void stopDragMode() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean isDrawerEnabled() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean shouldHideMenuItems() {
        // TODO Auto-generated method stub
        return false;
    }

    // Show content of this view
    public void show(int position) {
        switch (mViewMode.getMode()) {
        case ViewMode.VOL_LIST: 
            ContentListFragment contentListFragment = new ContentListFragment(this);
            launchFragment(contentListFragment, TAG_CONTENT_LIST);
            mViewMode.enterContentListMode();
            break;
        case ViewMode.CONTENT_LIST: 
            mContentPagerController.show(position);
            mViewMode.enterContentViewMode();
            break;
        default:
            break;
        }
    }

    public Deque<UpOrBackHandler> getUpOrBackHandlers() {
        return mUpOrBackHandlers;
    }

    public void registerVolumesUpdateListener(VolumesUpdateListener listener) {
        if (!mVolumesUpdateListeners.contains(listener)) {
            mVolumesUpdateListeners.add(listener);
        }
    }

    public void removeVolumesUpdateListener(VolumesUpdateListener listener) {
        if (!mVolumesUpdateListeners.contains(listener)) {
            mVolumesUpdateListeners.remove(listener);
        }
    }

    private void updateVolumeList() {
        for (VolumesUpdateListener listener : mVolumesUpdateListeners) {
            listener.onVolumesUpdated(mVolumeListCursor);
        }
    }

    public void showListView(boolean show) {
        LogUtils.d(LogUtils.TAG, "%s listview", show ? "show" : "hide");
        if (show) {
            mListView.setVisibility(View.VISIBLE);
        } else {
            mListView.setVisibility(View.INVISIBLE);
        }
    }

    private void initVolumeList() {
        LogUtils.d(LogUtils.TAG, "initVolumeListLoader");
        Loader<Cursor> loader = mLoaderManager.initLoader(LOADER_VOLUME_LIST, null, mVolumeLoads);
    }

    private class VolumeLoads implements LoaderCallbacks<Cursor> {

        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            LogUtils.d(LogUtils.TAG, "VolumeLoads onCreateLoader");
            return new VolumeListLoader(mContext, System.currentTimeMillis());
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
            Cursor volumesCursor = data;
            if (volumesCursor == null || volumesCursor.getCount() == 0) {
                LogUtils.d(LOG_TAG, "onLoadFinished with empty result, get ready to sync");
                mSyncAdapterController.requestSync();
                return;
            }
            mVolumeListCursor = volumesCursor;
            updateVolumeList();
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {
            if (mVolumeListCursor != null && !mVolumeListCursor.isClosed()) {
                mVolumeListCursor.close();
            }
            mVolumeListCursor = null;
        }

    }
}
