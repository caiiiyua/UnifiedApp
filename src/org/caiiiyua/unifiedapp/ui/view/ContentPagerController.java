package org.caiiiyua.unifiedapp.ui.view;

import org.caiiiyua.unifiedapp.R;
import org.caiiiyua.unifiedapp.ui.RestrictedActivity;

import android.app.FragmentManager;
import android.support.v4.view.ViewPager;

public class ContentPagerController {

    private ContentViewPager mPager;
    private ContentPagerAdapter mPagerAdapter;
    private FragmentManager mFragmentManager;

    public ContentPagerController(FragmentManager fragmentManager, RestrictedActivity activity) {
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

    public void show(int position) {
        String initContent = ContentListFragment.texts[position];
        mPagerAdapter = new ContentPagerAdapter(mFragmentManager, false, initContent);
        mPagerAdapter.setPager(mPager);
        mPager.setAdapter(mPagerAdapter);
        mPager.setCurrentItem(position);
    }
}
