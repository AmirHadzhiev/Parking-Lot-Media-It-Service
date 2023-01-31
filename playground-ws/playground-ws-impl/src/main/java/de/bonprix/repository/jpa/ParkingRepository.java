package de.bonprix.repository.jpa;


import de.bonprix.jpa.BasicRepositoryCustom;
import de.bonprix.model.Parking;
import de.bonprix.service.Parking.ParkingFilter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



public interface ParkingRepository extends BasicRepositoryCustom<Parking, ParkingFilter>, JpaRepository<Parking, Long> {



}
