package de.bonprix.service.car;

import de.bonprix.dto.IdsFilter;
import de.bonprix.model.Paged;

import javax.ws.rs.QueryParam;
import java.util.List;

public class CarFilter  extends Paged implements IdsFilter {

    @QueryParam("filtercarids")
    private List<Long> carIds;

    public CarFilter() {
        super(DEFAULT_PAGE, DEFAULT_PAGESIZE);
    }


    public CarFilter(Integer page, Integer pageSize) {
        super(page, pageSize);
    }

    public List<Long> getCarIds() {
        return this.carIds;

    }

    @Override
    public void setIds(List<Long> ids) {
        this.carIds = ids;
    }
}
