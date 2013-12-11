package org.caiiiyua.unifiedapp.ui;

import org.caiiiyua.unifiedapp.R;
import org.caiiiyua.unifiedapp.ui.view.ContentListFragment;
import org.caiiiyua.unifiedapp.ui.view.ContentPagerController;
import org.caiiiyua.unifiedapp.utils.UnifiedConnectivityManager;
import org.caiiiyua.unifiedapp.utils.Utils;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;

public class MainActivity extends Activity implements ControllableActivity {

    private static final String TAG_CONTENT_LIST = "tag-content-list";
    private ViewMode mViewMode;
    private ActivityController mController;
    private FragmentManager mFragmentManager;
    private ContentPagerController mContentPagerController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewMode = new ViewMode();
        final boolean tabletUi = Utils.useTabletUI(this.getResources());
        mController = ControllerFactory.forActivity(this, mViewMode, tabletUi);
        mController.onCreate(savedInstanceState);
        mFragmentManager = getFragmentManager();
        mContentPagerController = new ContentPagerController(mFragmentManager, this);
        final ListFragment contentListFragment = new ContentListFragment(mContentPagerController);
        replaceFragment(contentListFragment, FragmentTransaction.TRANSIT_FRAGMENT_OPEN,
                TAG_CONTENT_LIST, R.id.content_pane);
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
        final int id = fragmentTransaction.commitAllowingStateLoss();
        fm.executePendingTransactions();
        return id;
    }

    public ContentPagerController getContentPagerController() {
        return mContentPagerController;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public String getHelpContext() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ViewMode getViewMode() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public UnifiedConnectivityManager getConnectivityManager() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ErrorListener getErrorListener() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public UpOrBackController getUpOrBackController() {
        // TODO Auto-generated method stub
        return null;
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
    public boolean isAccessibilityEnabled() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public FragmentLauncher getFragmentLauncher() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Context getActivityContext() {
        // TODO Auto-generated method stub
        return null;
    }
}
