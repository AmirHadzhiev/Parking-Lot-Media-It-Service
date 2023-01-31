package de.bonprix.service.ParkingZone;

import de.bonprix.dto.IdsFilter;
import de.bonprix.model.Paged;

import javax.ws.rs.QueryParam;
import java.util.List;

public class ParkingZoneFilter  extends Paged implements IdsFilter {

    @QueryParam("filterparkingids")
    private List<Long> parkingZoneIds;

    public ParkingZoneFilter() {
        super(DEFAULT_PAGE, DEFAULT_PAGESIZE);
    }
    public ParkingZoneFilter(Integer page, Integer pageSize) {
        super(page, pageSize);
    }

    public List<Long> getParkingZoneIds() {
        return parkingZoneIds;
    }

    @Override
    public void setIds(List<Long> ids) {
        this.parkingZoneIds =ids;
    }

}
