package app.revanced.integrations.youtube.patches.swipe;

import android.view.View;

import java.lang.ref.WeakReference;

import app.revanced.integrations.youtube.settings.Settings;

@SuppressWarnings({"unused", "deprecation"})
public class SwipeControlsPatch {
    private static WeakReference<View> fullscreenEngagementOverlayViewRef = new WeakReference<>(null);

    /**
     * Injection point.
     */
    public static boolean disableHDRAutoBrightness() {
        return Settings.DISABLE_HDR_AUTO_BRIGHTNESS.get();
    }

    /**
     * Injection point.
     */
    public static boolean enableSwipeToSwitchVideo() {
        return Settings.ENABLE_SWIPE_TO_SWITCH_VIDEO.get();
    }

    /**
     * Injection point.
     */
    public static boolean enableWatchPanelGestures() {
        return Settings.ENABLE_WATCH_PANEL_GESTURES.get();
    }

    /**
     * Injection point.
     *
     * @param fullscreenEngagementOverlayView R.layout.fullscreen_engagement_overlay
     */
    public static void setFullscreenEngagementOverlayView(View fullscreenEngagementOverlayView) {
        fullscreenEngagementOverlayViewRef = new WeakReference<>(fullscreenEngagementOverlayView);
    }

    public static boolean isEngagementOverlayVisible() {
        final View engagementOverlayView = fullscreenEngagementOverlayViewRef.get();
        return engagementOverlayView != null && engagementOverlayView.getVisibility() == View.VISIBLE;
    }

}
