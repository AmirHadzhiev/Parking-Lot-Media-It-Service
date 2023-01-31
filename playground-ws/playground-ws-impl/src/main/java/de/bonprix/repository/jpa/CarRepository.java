package de.bonprix.repository.jpa;


import de.bonprix.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface CarRepository extends JpaRepository<Car, Long>, CarRepositoryCustom {






}
