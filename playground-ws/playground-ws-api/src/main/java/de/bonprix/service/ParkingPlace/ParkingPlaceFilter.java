package de.bonprix.service.ParkingPlace;

import de.bonprix.dto.IdsFilter;
import de.bonprix.model.Paged;

import javax.ws.rs.QueryParam;
import java.util.List;

public class ParkingPlaceFilter  extends Paged implements IdsFilter {

    @QueryParam("filterapplicationtypeids")
    private List<Long> parkingPlaceIds;

    public ParkingPlaceFilter() {
        super(DEFAULT_PAGE, DEFAULT_PAGESIZE);
    }

    public ParkingPlaceFilter(Integer page, Integer pageSize) {
        super(page, pageSize);
    }

    @Override
    public void setIds(List<Long> ids) {
        this.parkingPlaceIds = ids;
    }

    public List<Long>  getParkingPlaceIds() {
        return this.parkingPlaceIds;
    }
}
