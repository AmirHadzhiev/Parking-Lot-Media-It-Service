package de.bonprix.playground;


import de.bonprix.dto.CarDTO;

import de.bonprix.dto.ParkingPlaceDTO;
import de.bonprix.vaadin.mvp.View;

import java.util.List;

public interface CarView extends View<CarView.Presenter> {

    void updateList(List<CarDTO> cars);
    //void updateListParkingPlace(List<ParkingPlaceDTO> places);

    interface Presenter extends de.bonprix.vaadin.mvp.Presenter<CarView>{

        List<CarDTO> getAllCars();
        List<ParkingPlaceDTO> getAllParkingPlaces();

      Boolean createCar(CarDTO carDTO, Long id);

        void deleteById(Long id);


        void updateParking(CarDTO car);

        void deleteParking(CarDTO car);

        void parkCar(Long placeId, Long carId);


    }


}

