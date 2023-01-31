package de.bonprix.playground;


import de.bonprix.dto.ParkingPlaceDTO;
import de.bonprix.dto.ParkingZoneDTO;
import de.bonprix.vaadin.mvp.View;

import java.util.List;

public interface ParkingPlaceView extends View<ParkingPlaceView.Presenter> {

    void updateList(List<ParkingPlaceDTO> parkingPlaces);
    interface Presenter extends de.bonprix.vaadin.mvp.Presenter<ParkingPlaceView>{
        List<ParkingPlaceDTO> getAllParkingPlaces();
        List<ParkingZoneDTO> getAllParkingZones();

        void createParkingPlace(ParkingPlaceDTO parkingPlaceDTO);

        void deleteById(Long id);

        void updateParking(ParkingPlaceDTO parking);

        void deleteParking(ParkingPlaceDTO parking);
    }
}
