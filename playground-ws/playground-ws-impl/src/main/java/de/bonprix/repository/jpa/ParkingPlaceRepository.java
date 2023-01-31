package de.bonprix.repository.jpa;


import de.bonprix.model.ParkingPlace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



public interface ParkingPlaceRepository extends JpaRepository<ParkingPlace, Long>,ParkingPlaceRepositoryCustom  {
    @Override
    <S extends ParkingPlace> S saveAndFlush(S entity);
    @Override
    public void addPLace (ParkingPlace parkingPlace);
}
