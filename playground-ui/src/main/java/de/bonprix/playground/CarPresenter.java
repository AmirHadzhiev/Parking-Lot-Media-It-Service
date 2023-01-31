package de.bonprix.playground;

import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;

import de.bonprix.dto.CarDTO;
import de.bonprix.dto.ParkingPlaceDTO;
import de.bonprix.service.ParkingPlace.ParkingPlaceFilter;
import de.bonprix.service.ParkingPlace.ParkingPlaceService;
import de.bonprix.service.car.CarFilter;
import de.bonprix.service.car.CarService;

import de.bonprix.vaadin.mvp.BasePresenter;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@SpringComponent
@VaadinSessionScope
public class CarPresenter extends BasePresenter<CarView> implements CarView.Presenter {

    @Resource
    private CarService carService;
    @Resource
    private ParkingPlaceService parkingPlaceService;

    private CarFilter carFilter = new CarFilter();
    private ParkingPlaceFilter parkingPlaceFilter = new ParkingPlaceFilter();

    @Override
    public void afterViewInit() {
        getView().updateList(getAllCars());
        //getView().updateListParkingPlace(getAllParkingPlace());

    }

    @Override
    public List<CarDTO> getAllCars() {
        return carService.findAll(carFilter)
                .stream()
                .filter(carDTO -> carDTO.getPlateNumber()!=null)
                .collect(Collectors.toList());
    }

    @Override
    public List<ParkingPlaceDTO> getAllParkingPlaces() {
        return parkingPlaceService.findAll(parkingPlaceFilter);
    }

    @Override
    public Boolean createCar(CarDTO carDTO, Long id) {
        for (CarDTO car : getAllCars()) {
           if (car.getPlateNumber().equals(carDTO.getPlateNumber())){
                return false ;
            }
        }
        if (carDTO.getPlateNumber().length() >= 8 && carDTO.getPlateNumber().length() <=10){
            long l = carService.create(carDTO);
            CarDTO carForUpdate = carService.findById(l);
            ParkingPlaceDTO placeForUpdate = parkingPlaceService.findById(id);
            placeForUpdate.setCar(carForUpdate);
            parkingPlaceService.update(placeForUpdate);
            return true;
        }
        return false;
    }

    @Override
    public void deleteById(Long id) {
        carService.deleteById(id);

    }

    @Override
    public void updateParking(CarDTO car) {
        carService.update(car);
    }

    @Override
    public void deleteParking(CarDTO car) {
        Long carId= car.getId();
        for (ParkingPlaceDTO placeDTO : parkingPlaceService.findAll(parkingPlaceFilter)) {
            if (placeDTO.getCar()!=null) {
                if (placeDTO.getCar().getId() == carId) {
                    ParkingPlaceDTO toUpdate = parkingPlaceService.findById(placeDTO.getId());
                    toUpdate.setCar(null);
                    parkingPlaceService.update(toUpdate);


                }
            }
        }
        carService.deleteById(car.getId());

    }

    public void parkCar(Long carId, Long placeId){

        ParkingPlaceDTO parkingPlaceDTO = parkingPlaceService.findById(placeId);

        CarDTO carDTO = carService.findById(carId);
        parkingPlaceDTO.setCar(carDTO);

        parkingPlaceService.update(parkingPlaceDTO);


    }
    public List<ParkingPlaceDTO> getAllParkingPlace (){
        return  parkingPlaceService.findAll(parkingPlaceFilter);
    }




}
