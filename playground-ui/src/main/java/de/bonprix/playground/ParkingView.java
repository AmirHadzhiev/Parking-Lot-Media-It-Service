package de.bonprix.playground;

import de.bonprix.dto.ParkingDTO;
import de.bonprix.dto.ParkingPlaceDTO;
import de.bonprix.dto.ParkingZoneDTO;
import de.bonprix.vaadin.mvp.View;

import java.util.List;
import java.util.Optional;

public interface ParkingView extends View<ParkingView.Presenter> {

    void updateList(List<ParkingDTO> parkings);

    //  void setParking() throws ValidationException;

    interface Presenter extends de.bonprix.vaadin.mvp.Presenter<ParkingView> {
        List<ParkingDTO> getAllParkings();


        List<ParkingZoneDTO> getAllParkingZones();
        List<ParkingPlaceDTO> getAllParkingPlaces();




        Boolean createParking(ParkingDTO parking);

        ParkingDTO findByID (Long id);

       // void deleteById(Long id);


        void updateParking(ParkingDTO parking);

        void deleteParking(ParkingDTO parking);
    }
}
