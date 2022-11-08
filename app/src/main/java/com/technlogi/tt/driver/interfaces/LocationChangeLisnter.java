package com.technlogi.tt.driver.interfaces;

import com.technlogi.tt.user.models.LocationModel;

public interface LocationChangeLisnter {
    void onLocationChange(LocationModel locationModel, boolean moveMap);
}
