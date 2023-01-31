package de.bonprix.service;

import de.bonprix.annotation.RestService;
import de.bonprix.dto.CarDTO;
import de.bonprix.model.Car;
import de.bonprix.repository.jpa.CarRepository;
import de.bonprix.service.car.CarFilter;
import de.bonprix.service.car.CarService;
import org.springframework.security.access.prepost.PreAuthorize;

import javax.annotation.Resource;
import java.util.List;

@RestService
public class CarServiceImpl extends AbstractBasicService<Car, CarDTO, CarFilter, CarRepository>
        implements CarService {
    @Resource
    private CarRepository carJpaRepository;

    public CarServiceImpl() {
        super(Car.class, CarDTO.class, CarFilter.class);
    }
    public CarServiceImpl(Class<Car> entityClazz, Class<CarDTO> dtoClazz, Class<CarFilter> filterClazz) {
        super(entityClazz, dtoClazz, filterClazz);
    }

    @Override
    @PreAuthorize("permitAll")
    protected CarRepository getRepository() {
        return this.carJpaRepository;
    }

    @PreAuthorize("permitAll")
    @Override
    public List<CarDTO> findAll(CarFilter filter) {
        return super.findAllDefault(filter);
    }

    @Override
    @PreAuthorize("permitAll")
    public CarDTO findById(Long id) {
        return super.findByIdDefault(id);
    }

    @Override
    @PreAuthorize("permitAll")
    public void deleteById(Long id) {
        super.deleteByIdDefault(id);
    }

    @Override
    @PreAuthorize("permitAll")
    public long create(CarDTO carDTO) {
        return super.createDefault(carDTO);
    }

    @Override
    @PreAuthorize("permitAll")
    public void update(CarDTO carDTO) {
        super.updateDefault(carDTO.getId(),carDTO);
    }

}
