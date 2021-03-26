package com.espy.mps.interfaces;

import android.location.Location;

public interface LocationUtilsListener {

    void onCurrentLocationReceived(Location location);

    void onLocationSettingsSatisfied();
}
