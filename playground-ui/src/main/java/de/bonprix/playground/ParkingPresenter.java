package de.bonprix.playground;

import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;

import de.bonprix.dto.ParkingDTO;
import de.bonprix.dto.ParkingPlaceDTO;
import de.bonprix.dto.ParkingZoneDTO;
import de.bonprix.service.Parking.ParkingFilter;
import de.bonprix.service.Parking.ParkingService;
import de.bonprix.service.ParkingPlace.ParkingPlaceFilter;
import de.bonprix.service.ParkingPlace.ParkingPlaceService;
import de.bonprix.service.ParkingZone.ParkingZoneFilter;
import de.bonprix.service.ParkingZone.ParkingZoneService;
import de.bonprix.vaadin.mvp.BasePresenter;
import net.bytebuddy.description.type.TypeList;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@SpringComponent
@VaadinSessionScope
public class ParkingPresenter extends BasePresenter<ParkingView> implements ParkingView.Presenter {

    @Resource
    private ParkingService parkingService;

    @Resource
    private ParkingZoneService parkingZoneService;
    @Resource
    private ParkingPlaceService parkingPlaceService;

    private ParkingPlaceFilter parkingPlaceFilter = new ParkingPlaceFilter();


    private ParkingFilter parkingFilter = new ParkingFilter();

    private ParkingZoneFilter parkingZoneFilter = new ParkingZoneFilter();


    @Override
    public void afterViewInit() {
        getView().updateList(getAllParkings());
    }


    @Override
    public List<ParkingDTO> getAllParkings() {
        return parkingService.findAll(parkingFilter);
    }

    @Override
    public List<ParkingZoneDTO> getAllParkingZones() {
        return parkingZoneService.findAll(parkingZoneFilter);
    }

    @Override
    public List<ParkingPlaceDTO> getAllParkingPlaces() {
        return parkingPlaceService.findAll(parkingPlaceFilter);
    }

    @Override
    public Boolean createParking(ParkingDTO parking) {

            try {
                int zipCode = Integer.parseInt(parking.getZipCode());
            } catch (NumberFormatException nfe){
                return false;
            }
        if (parking.getZipCode().length() == 4){
            parkingService.create(parking);
            return true;
        }
        return false;

    }

    @Override
    public ParkingDTO findByID(Long id) {
        return parkingService.findById(id);
    }

//    @Override
//    public void deleteById(Long id) {
//
//        ParkingDTO parkingDTO = parkingService.findById(id);
//        List<ParkingZoneDTO> zones = getAllParkingZones();
//        for (ParkingZoneDTO zone : zones) {
//            if (zone.getParking().getId()==id){
//                for (ParkingPlaceDTO place : getAllParkingPlaces()) {
//                    if (place.getParkingZone().getId()==zone.getId()){
//                        place.setCar(null);
//                        parkingPlaceService.update(place);
//                        parkingPlaceService.deleteById(place.getId());
//                        parkingZoneService.deleteById(zone.getId());
//                        parkingService.deleteById(id);
//                    }
//                }
//            }
//        }
//
//
//    }

    @Override
    public void updateParking(ParkingDTO parking) {
        parkingService.update(parking);
    }

    @Override
    public void deleteParking(ParkingDTO parking) {
       // parkingService.deleteById(parking.getId());

        ParkingDTO parkingDTO = parkingService.findById(parking.getId());
        List<ParkingZoneDTO> zones = getAllParkingZones();

        for (ParkingZoneDTO zone : zones) {
            if (zone.getParking().getId()==parking.getId()){
                for (ParkingPlaceDTO place : getAllParkingPlaces()) {
                    if (place.getParkingZone().getId()==zone.getId()){
                        place.setCar(null);
                        parkingPlaceService.update(place);
                        parkingPlaceService.deleteById(place.getId());

                    }
                }
                parkingZoneService.deleteById(zone.getId());
            }

        }
        parkingService.deleteById(parking.getId());
    }

}
