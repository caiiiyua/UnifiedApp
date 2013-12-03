package org.caiiiyua.unifiedapp.ui;

/**
 * Creates the appropriate {@link ActivityController} to control {@link MailActivity}.
 *
 */
public class ControllerFactory {
    /**
     * Create the appropriate type of ActivityController.
     *
     * @return the appropriate {@link ActivityController} to control {@link MailActivity}.
     */
    public static ActivityController forActivity(MainActivity activity, ViewMode viewMode,
            boolean isTabletDevice) {
        return new OnePanelController(activity, viewMode);
    }
}
