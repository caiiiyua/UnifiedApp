package org.caiiiyua.unifiedapp.ui.view;

import org.caiiiyua.unifiedapp.utils.FragmentStatePagerAdapter2;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v4.view.ViewPager;

public class ContentPagerAdapter extends FragmentStatePagerAdapter2 {

    private static final String contents [] = ContentListFragment.texts;
    private String mInitContent;
    private ContentViewPager mPager;

    public ContentPagerAdapter(FragmentManager fm, boolean enableSavedStates, String initContent) {
        super(fm, enableSavedStates);
        mInitContent = initContent;
    }

    @Override
    public Fragment getItem(int position) {
        // TODO Auto-generated method stub
        return ContentViewFragment.newInstance(contents[position]);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return contents.length;
    }

    public void setPager(ViewPager pager) {
        // TODO Auto-generated method stub
        mPager = (ContentViewPager)pager;
    }

}
