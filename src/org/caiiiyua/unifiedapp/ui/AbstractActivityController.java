package org.caiiiyua.unifiedapp.ui;

import java.util.Deque;
import java.util.LinkedList;

import org.caiiiyua.unifiedapp.R;
import org.caiiiyua.unifiedapp.ui.view.ContentListFragment;
import org.caiiiyua.unifiedapp.ui.view.ContentPagerController;
import org.caiiiyua.unifiedapp.ui.view.VolumeListFragment;
import org.caiiiyua.unifiedapp.utils.LogTag;
import org.caiiiyua.unifiedapp.utils.LogUtils;
import org.caiiiyua.unifiedapp.utils.Utils;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
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

    private boolean mIsDragHappening;

    private final Deque<UpOrBackHandler> mUpOrBackHandlers = 
            new LinkedList<UpOrBackController.UpOrBackHandler>();
    protected ContentPagerController mContentPagerController;
    private View mListView;
    private Cursor mVolumeListCursor;

    public AbstractActivityController(ControllableActivity activity, ViewMode viewMode) {
        mActivity = activity;
        mFragmentManager = mActivity.getFragmentManager();
        mViewMode = viewMode;
        mContext = activity.getApplicationContext();
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
        if (savedState == null) {
            LogUtils.d(LogUtils.TAG, "AbstractActivityController onCreate");
            // Launch volume list by default
            final ListFragment contentListFragment = VolumeListFragment.newInstance(this);
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
        // TODO Auto-generated method stub
        return false;
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
        // TODO Auto-generated method stub
        return false;
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
        // TODO Auto-generated method stub
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

    public void showListView(boolean show) {
        if (show) {
            mListView.setVisibility(View.VISIBLE);
        } else {
            mListView.setVisibility(View.INVISIBLE);
        }
    }
}
