package de.bonprix.playground;

import de.bonprix.dto.ParkingZoneDTO;
import de.bonprix.vaadin.mvp.Presenter;
import de.bonprix.vaadin.mvp.View;

import java.util.List;

public interface DashboardView  extends View<DashboardView.Presenter> {

    void updateList();
    interface Presenter extends de.bonprix.vaadin.mvp.Presenter<DashboardView> {
        List<ParkingZoneDTO> getAllParkingZones();
    }
}
