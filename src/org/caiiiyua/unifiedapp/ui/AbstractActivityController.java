package org.caiiiyua.unifiedapp.ui;

import java.util.Deque;

import org.caiiiyua.unifiedapp.utils.LogTag;
import org.caiiiyua.unifiedapp.utils.Utils;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;

import com.google.common.collect.Lists;

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

    private boolean mIsDragHappening;

    private final Deque<UpOrBackHandler> mUpOrBackHandlers = Lists.newLinkedList();

    public AbstractActivityController(MainActivity activity, ViewMode viewMode) {
        mActivity = activity;
        mFragmentManager = mActivity.getFragmentManager();
        mViewMode = viewMode;
        mContext = activity.getApplicationContext();
        // Allow the fragment to observe changes to its own selection set. No other object is
        // aware of the selected set.
//        mSelectedSet.addObserver(this);

        final Resources r = mContext.getResources();
        mIsTablet = Utils.useTabletUI(r);
    }

}
