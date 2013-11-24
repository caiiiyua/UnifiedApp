package org.caiiiyua.unifiedapp.ui;

import org.caiiiyua.unifiedapp.ui.ViewMode.ModeChangeListener;
import org.caiiiyua.unifiedapp.utils.UnifiedConnectivityManager.ConnectivityStateChangeListener;


public interface ActivityController extends ModeChangeListener, HelpCallback,
                ErrorListener, UpOrBackController, FragmentLauncher,
                ConnectivityStateChangeListener {

}
