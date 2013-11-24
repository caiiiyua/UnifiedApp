package org.caiiiyua.unifiedapp.ui;

import org.caiiiyua.unifiedapp.R;
import org.caiiiyua.unifiedapp.R.layout;
import org.caiiiyua.unifiedapp.R.menu;
import org.caiiiyua.unifiedapp.utils.UnifiedConnectivityManager;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MainActivity extends Activity implements ControllableActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

}
