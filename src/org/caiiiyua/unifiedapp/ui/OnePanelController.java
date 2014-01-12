package org.caiiiyua.unifiedapp.ui;

import org.caiiiyua.unifiedapp.R;
import org.caiiiyua.unifiedapp.ui.view.ContentListFragment;
import org.caiiiyua.unifiedapp.ui.view.ContentPagerController;
import org.caiiiyua.unifiedapp.utils.LogUtils;

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

public class OnePanelController extends AbstractActivityController implements
        ActivityController {

    MainActivity mActivity;
    public OnePanelController(MainActivity activity, ViewMode viewMode) {
        super(activity, viewMode);
        LogUtils.d(LogUtils.TAG, "OnePanelController construct");
        mActivity = activity;
    }

    @Override
    public void onViewModeChanged(int newMode) {
        switch (newMode) {
        case ViewMode.CONTENT_LIST:
            mContentPagerController.hide(true);
            showListView(true);
            break;
        case ViewMode.CONTENT_VIEW:
            View view = mActivity.findViewById(R.id.content_pane);
            view.setVisibility(View.INVISIBLE);
            showListView(false);
            break;
        default:
            break;
        }
    }
}
