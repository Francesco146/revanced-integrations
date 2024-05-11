package app.revanced.integrations.youtube.patches.video;

import static app.revanced.integrations.shared.utils.StringRef.str;

import androidx.annotation.NonNull;

import app.revanced.integrations.shared.settings.IntegerSetting;
import app.revanced.integrations.shared.utils.Utils;
import app.revanced.integrations.youtube.settings.Settings;
import app.revanced.integrations.youtube.shared.VideoInformation;

@SuppressWarnings("unused")
public class VideoQualityPatch {
    private static final int DEFAULT_YOUTUBE_VIDEO_QUALITY = -2;
    private static final IntegerSetting mobileQualitySetting = Settings.DEFAULT_VIDEO_QUALITY_MOBILE;
    private static final IntegerSetting wifiQualitySetting = Settings.DEFAULT_VIDEO_QUALITY_WIFI;

    /**
     * Injection point.
     */
    public static void newVideoStarted(@NonNull String newlyLoadedChannelId, @NonNull String newlyLoadedChannelName,
                                           @NonNull String newlyLoadedVideoId, @NonNull String newlyLoadedVideoTitle,
                                           final long newlyLoadedVideoLength, boolean newlyLoadedLiveStreamValue) {
        final int defaultQuality = Utils.getNetworkType() == Utils.NetworkType.MOBILE
                ? mobileQualitySetting.get()
                : wifiQualitySetting.get();

        if (defaultQuality == DEFAULT_YOUTUBE_VIDEO_QUALITY)
            return;

        long delayMillis;
        if (Settings.SKIP_PRELOADED_BUFFER.get()) {
            delayMillis = 250;
        } else {
            delayMillis = 500;
        }

        Utils.runOnMainThreadDelayed(() ->
                        VideoInformation.overrideVideoQuality(
                                VideoInformation.getAvailableVideoQuality(defaultQuality)
                        ),
                delayMillis
        );
    }

    /**
     * Injection point.
     */
    public static void userSelectedVideoQuality() {
        Utils.runOnMainThreadDelayed(() ->
                        userSelectedVideoQuality(VideoInformation.getVideoQuality()),
                300
        );
    }

    private static void userSelectedVideoQuality(final int defaultQuality) {
        if (!Settings.REMEMBER_VIDEO_QUALITY_LAST_SELECTED.get())
            return;
        if (defaultQuality == DEFAULT_YOUTUBE_VIDEO_QUALITY)
            return;

        final Utils.NetworkType networkType = Utils.getNetworkType();

        switch (networkType) {
            case NONE -> {
                Utils.showToastShort(str("revanced_remember_video_quality_none"));
                return;
            }
            case MOBILE -> mobileQualitySetting.save(defaultQuality);
            default -> wifiQualitySetting.save(defaultQuality);
        }

        Utils.showToastShort(str("revanced_remember_video_quality_" + networkType.getName(), defaultQuality + "p"));
    }
}