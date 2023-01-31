package de.bonprix.playground;

import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;

import de.bonprix.dto.ParkingDTO;
import de.bonprix.dto.ParkingPlaceDTO;
import de.bonprix.dto.ParkingZoneDTO;
import de.bonprix.service.ParkingPlace.ParkingPlaceFilter;
import de.bonprix.service.ParkingPlace.ParkingPlaceService;
import de.bonprix.service.ParkingZone.ParkingZoneFilter;
import de.bonprix.service.ParkingZone.ParkingZoneService;
import de.bonprix.vaadin.mvp.BasePresenter;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

@SpringComponent
@VaadinSessionScope
public class ParkingPlacePresenter extends BasePresenter<ParkingPlaceView> implements ParkingPlaceView.Presenter {

    @Resource
    private ParkingPlaceService parkingPlaceService;
    @Resource
    private ParkingZoneService parkingZoneService;

    private ParkingPlaceFilter parkingPlaceFilter = new ParkingPlaceFilter();
    private ParkingZoneFilter parkingZoneFilter = new ParkingZoneFilter();

    @Override
    public void afterViewInit() {
        getView().updateList(getAllParkingPlaces());
    }

    @Override
    public List<ParkingPlaceDTO> getAllParkingPlaces() {
        return parkingPlaceService.findAll(parkingPlaceFilter);
    }

    @Override
    public List<ParkingZoneDTO> getAllParkingZones() {
        return parkingZoneService.findAll(parkingZoneFilter);
    }

    @Override
    public void createParkingPlace(ParkingPlaceDTO parkingPlaceDTO) {
        parkingPlaceService.create(parkingPlaceDTO);

    }

    @Override
    public void deleteById(Long id) {
        ParkingPlaceDTO place = parkingPlaceService.findById(id);
        place.setCar(null);
        parkingPlaceService.update(place);

        parkingPlaceService.deleteById(id);

    }

    @Override
    public void updateParking(ParkingPlaceDTO parkingPlace) {
        parkingPlaceService.update(parkingPlace);
    }

    @Override
    public void deleteParking(ParkingPlaceDTO parkingPlace) {
        parkingPlaceService.deleteById(parkingPlace.getId());
    }
}
