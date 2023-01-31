package de.bonprix.repository.jpa;

import de.bonprix.jpa.BasicRepositoryCustom;
import de.bonprix.model.ParkingPlace;
import de.bonprix.service.ParkingPlace.ParkingPlaceFilter;

public interface ParkingPlaceRepositoryCustom extends BasicRepositoryCustom<ParkingPlace, ParkingPlaceFilter> {


    <S extends ParkingPlace> S saveAndFlush(S entity);

    public void addPLace (ParkingPlace parkingPlace);


}
