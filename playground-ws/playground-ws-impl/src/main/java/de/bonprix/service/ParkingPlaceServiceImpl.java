package de.bonprix.service;

import de.bonprix.annotation.RestService;
import de.bonprix.dto.ParkingPlaceDTO;

import de.bonprix.model.ParkingPlace;
import de.bonprix.repository.jpa.ParkingPlaceRepository;
import de.bonprix.service.ParkingPlace.ParkingPlaceFilter;
import de.bonprix.service.ParkingPlace.ParkingPlaceService;
import org.springframework.security.access.prepost.PreAuthorize;

import javax.annotation.Resource;
import java.util.List;

@RestService
public class ParkingPlaceServiceImpl extends AbstractBasicService<ParkingPlace, ParkingPlaceDTO, ParkingPlaceFilter, ParkingPlaceRepository>
        implements ParkingPlaceService {


    @Resource
    private ParkingPlaceRepository parkingPlaceJpaRepository;

    public ParkingPlaceServiceImpl() {
        super(ParkingPlace.class, ParkingPlaceDTO.class, ParkingPlaceFilter.class);
    }
    public ParkingPlaceServiceImpl(Class<ParkingPlace> entityClazz, Class<ParkingPlaceDTO> dtoClazz, Class<ParkingPlaceFilter> filterClazz) {
        super(entityClazz, dtoClazz, filterClazz);
    }

    @Override
    @PreAuthorize("permitAll")
    protected ParkingPlaceRepository getRepository() {
        return this.parkingPlaceJpaRepository;
    }

    @Override
    @PreAuthorize("permitAll")
    public List<ParkingPlaceDTO> findAll(ParkingPlaceFilter filter) {
        return super.findAllDefault(filter);
    }

    @Override
    @PreAuthorize("permitAll")
    public ParkingPlaceDTO findById(Long id) {
        return super.findByIdDefault(id);
    }

    @Override
    @PreAuthorize("permitAll")
    public void deleteById(Long id) {
        super.deleteByIdDefault(id);
    }

    @Override
    @PreAuthorize("permitAll")
    public long create(ParkingPlaceDTO parkingPlaceDTO) {
        return super.createDefault(parkingPlaceDTO);
    }

    @Override
    @PreAuthorize("permitAll")
    public void update(ParkingPlaceDTO parkingPlaceDTO) {
        super.updateDefault(parkingPlaceDTO.getId(),parkingPlaceDTO);

    }


    @PreAuthorize("permitAll")
    public void specialUpdate(ParkingPlace parkingPlace) {
        parkingPlaceJpaRepository.addPLace(parkingPlace);
    }

}
