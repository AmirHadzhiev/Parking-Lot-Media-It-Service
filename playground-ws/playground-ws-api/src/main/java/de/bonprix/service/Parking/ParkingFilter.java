package de.bonprix.service.Parking;

import de.bonprix.dto.IdsFilter;
import de.bonprix.model.Paged;

import javax.ws.rs.QueryParam;
import java.util.List;

public class ParkingFilter extends Paged implements IdsFilter {

    @QueryParam("filterparkingids")
    private List<Long> parkingIds;

    public ParkingFilter() {
        super(DEFAULT_PAGE, DEFAULT_PAGESIZE);
    }
    public ParkingFilter(Integer page, Integer pageSize) {
        super(page, pageSize);
    }

    public List<Long> getParkingIds() {
        return parkingIds;
    }
    @Override
    public void setIds(List<Long> ids) {
        this.parkingIds=ids;
    }


}
