package org.caiiiyua.unifiedapp.ui.view;

import org.caiiiyua.unifiedapp.R;
import org.caiiiyua.unifiedapp.ui.ControllableActivity;
import org.caiiiyua.unifiedapp.utils.LogUtils;

import android.app.FragmentManager;
import android.view.View;

public class ContentPagerController {

    private ContentViewPager mPager;
    private ContentPagerAdapter mPagerAdapter;
    private FragmentManager mFragmentManager;
    private boolean mShown;
    private ControllableActivity mActivity;

    public ContentPagerController(FragmentManager fragmentManager, ControllableActivity activity) {
        mActivity = activity;
        mFragmentManager = fragmentManager;
        mPager = (ContentViewPager) activity.findViewById(R.id.content_pager);
    }

    public void onDestroy() {
        cleanup();
    }

    private void cleanup() {
        if (mPagerAdapter != null) {
            mPagerAdapter.setPager(null);
            mPagerAdapter = null;
        }
    }

    public void hide(boolean changeVisibility) {
        if (!mShown) {
            LogUtils.d(LogUtils.TAG, "IN CPC.hide, but already hidden");
            return;
        }
        mShown = false;
        if (changeVisibility) {
            mPager.setVisibility(View.GONE);
        }

        LogUtils.d(LogUtils.TAG, "IN CPC.hide, clearing adapter and unregistering list observer");
        mPager.setAdapter(null);
        cleanup();
    }

    public void show(int position) {
        if (!mShown) {
            String initContent = ContentListFragment.texts[position];
            mPagerAdapter = new ContentPagerAdapter(mFragmentManager, false, initContent);
            mPagerAdapter.setPager(mPager);
            mPager.setAdapter(mPagerAdapter);
            mPager.setCurrentItem(position);
            mPager.setVisibility(View.VISIBLE);
            mShown = true;
            mActivity.getViewMode().enterContentViewMode();
        } else {
            LogUtils.d(LogUtils.TAG, "Already shown");
        }
    }
}
