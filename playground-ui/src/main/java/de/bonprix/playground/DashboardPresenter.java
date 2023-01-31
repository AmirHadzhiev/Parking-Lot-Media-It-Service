package de.bonprix.playground;

import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import de.bonprix.dto.ParkingZoneDTO;
import de.bonprix.service.ParkingZone.ParkingZoneFilter;
import de.bonprix.service.ParkingZone.ParkingZoneService;
import de.bonprix.vaadin.mvp.BasePresenter;

import javax.annotation.Resource;
import java.util.List;

@SpringComponent
@VaadinSessionScope
public class DashboardPresenter extends BasePresenter<DashboardView>  implements DashboardView.Presenter {

    @Resource
    private ParkingZoneService parkingZoneService;
    private ParkingZoneFilter parkingZoneFilter = new ParkingZoneFilter();

    @Override
    public List<ParkingZoneDTO> getAllParkingZones() {
      return   parkingZoneService.findAll(parkingZoneFilter);
    }
}
