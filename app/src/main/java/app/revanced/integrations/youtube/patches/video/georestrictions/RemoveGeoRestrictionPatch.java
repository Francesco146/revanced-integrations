package app.revanced.integrations.youtube.patches.video.georestrictions;

import app.revanced.integrations.shared.utils.Logger;
import app.revanced.integrations.youtube.settings.Settings;

@SuppressWarnings("unused")
public class RemoveGeoRestrictionPatch {
    /**
     * Injection point
     */
    public static String getIp() {
        Logger.printInfo(() -> "[RemoveGeoRestrictionPatch] IP: " + Settings.LOCATIONS_IP.get().IPAddress);
        return Settings.LOCATIONS_IP.get().IPAddress;
    }

    /**
     * Injection point
     */
    public static boolean shouldChangeLocation() {
        Logger.printInfo(() -> "[RemoveGeoRestrictionPatch] Enabled: " + Settings.ENABLE_GEO_RESTRICTION_BYPASS.get());
        return Settings.ENABLE_GEO_RESTRICTION_BYPASS.get();
    }
}
