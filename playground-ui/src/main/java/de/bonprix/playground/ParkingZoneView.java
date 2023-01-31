package de.bonprix.playground;

import de.bonprix.dto.CarDTO;
import de.bonprix.dto.ParkingDTO;
import de.bonprix.dto.ParkingPlaceDTO;
import de.bonprix.dto.ParkingZoneDTO;
import de.bonprix.vaadin.mvp.View;

import java.util.List;
import java.util.Set;

public interface ParkingZoneView extends View<ParkingZoneView.Presenter> {

    void updateList();


    interface Presenter extends de.bonprix.vaadin.mvp.Presenter<ParkingZoneView>{

        List<ParkingZoneDTO> getAllParkingZones();
        List<ParkingDTO> getAllParkings();

        Boolean createParkingZone(ParkingZoneDTO parkingZoneDTO);

        void deleteById(Long id);

        void updateParking(ParkingZoneDTO parking);

        void deleteParking(ParkingZoneDTO parking);
    }

}
