package org.caiiiyua.unifiedapp.ui;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;

public class OnePanelController extends AbstractActivityController implements
        ActivityController {

    public OnePanelController(MainActivity activity, ViewMode viewMode) {
        super(activity, viewMode);
    }

    @Override
    public void onViewModeChanged(int newMode) {
        // TODO Auto-generated method stub

    }

    @Override
    public String getHelpContext() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onError(int errorCode) {
        // TODO Auto-generated method stub

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
    public void launchFragment(Fragment fragment, int selectPosition) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onConnectivityStateChange(NetworkInfo networkInfo) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean onBackPressed() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean onUpPressed() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean onCreate(Bundle savedState) {
        // TODO Auto-generated method stub
        return false;
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
        // TODO Auto-generated method stub

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

}
