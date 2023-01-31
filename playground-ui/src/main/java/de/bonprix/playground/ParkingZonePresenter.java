package de.bonprix.playground;

import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;


import de.bonprix.dto.ParkingDTO;
import de.bonprix.dto.ParkingZoneDTO;
import de.bonprix.service.Parking.ParkingFilter;
import de.bonprix.service.Parking.ParkingService;
import de.bonprix.service.ParkingZone.ParkingZoneFilter;
import de.bonprix.service.ParkingZone.ParkingZoneService;
import de.bonprix.vaadin.mvp.BasePresenter;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

@SpringComponent
@VaadinSessionScope
public class ParkingZonePresenter extends BasePresenter<ParkingZoneView> implements ParkingZoneView.Presenter {
    @Resource
    private ParkingZoneService parkingZoneService;
    @Resource
    private ParkingService parkingService;


    private ParkingZoneFilter parkingZoneFilter = new ParkingZoneFilter();
    private ParkingFilter parkingFilter = new ParkingFilter();

    @Override
    public void afterViewInit() {
        getView().updateList();

    }
    @Override
    public List<ParkingZoneDTO> getAllParkingZones() {
        return parkingZoneService.findAll(parkingZoneFilter);
    }

    @Override
    public List<ParkingDTO> getAllParkings() {
        return parkingService.findAll(parkingFilter);
    }



    @Override
    public Boolean createParkingZone(ParkingZoneDTO parkingZoneDTO) {
        for (ParkingZoneDTO zone : parkingZoneService.findAll(parkingZoneFilter)) {
            if (zone.getName().equals(parkingZoneDTO.getName()) && zone.getParking().getId()==parkingZoneDTO.getParking().getId()){
                return false;
            }
        }
        parkingZoneService.create(parkingZoneDTO);
        return true;
    }

    @Override
    public void deleteById(Long id) {
        parkingZoneService.deleteById(id);
    }

    @Override
    public void updateParking(ParkingZoneDTO parking) {
        parkingZoneService.update(parking);
    }

    @Override
    public void deleteParking(ParkingZoneDTO parking) {
        parkingZoneService.deleteById(parking.getId());
    }


}
