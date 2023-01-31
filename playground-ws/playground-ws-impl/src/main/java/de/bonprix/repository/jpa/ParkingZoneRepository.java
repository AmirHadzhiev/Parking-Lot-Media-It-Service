package de.bonprix.repository.jpa;


import de.bonprix.model.ParkingZone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



public interface ParkingZoneRepository extends JpaRepository<ParkingZone, Long>, ParkingZoneRepositoryCustom {



}
