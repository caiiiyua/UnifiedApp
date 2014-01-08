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
//    private ContentPagerController mContentPagerController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewMode = new ViewMode();
        final boolean tabletUi = Utils.useTabletUI(this.getResources());
        mController = ControllerFactory.forActivity(this, mViewMode, tabletUi);
        mController.onCreate(savedInstanceState);
        mFragmentManager = getFragmentManager();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mController.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mController.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mController.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mController.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        mController.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public String getHelpContext() {
        return mController.getHelpContext();
    }

    @Override
    public ViewMode getViewMode() {
        return mViewMode;
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
        return mController;
    }

    @Override
    public void startDragMode() {
        mController.startDragMode();
    }

    @Override
    public void stopDragMode() {
        mController.stopDragMode();
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
        return this;
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        if (!mController.onBackPressed()) {
            super.onBackPressed();
        }
    }
}
