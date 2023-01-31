package de.bonprix.service;

import de.bonprix.annotation.RestService;
import de.bonprix.dto.ParkingZoneDTO;
import de.bonprix.model.ParkingZone;
import de.bonprix.repository.jpa.ParkingZoneRepository;
import de.bonprix.service.ParkingZone.ParkingZoneFilter;
import de.bonprix.service.ParkingZone.ParkingZoneService;
import org.springframework.security.access.prepost.PreAuthorize;

import javax.annotation.Resource;
import java.util.List;

@RestService
public class ParkingZoneServiceImpl  extends AbstractBasicService<ParkingZone, ParkingZoneDTO, ParkingZoneFilter, ParkingZoneRepository>
		implements ParkingZoneService {
    @Resource
    private ParkingZoneRepository parkingZoneJpaRepository;

    public ParkingZoneServiceImpl() {
        super(ParkingZone.class, ParkingZoneDTO.class, ParkingZoneFilter.class);
    }
    public ParkingZoneServiceImpl(Class<ParkingZone> entityClazz, Class<ParkingZoneDTO> dtoClazz, Class<ParkingZoneFilter> filterClazz) {
        super(entityClazz, dtoClazz, filterClazz);
    }

    @Override
    @PreAuthorize("permitAll")
    protected ParkingZoneRepository getRepository() {
        return this.parkingZoneJpaRepository;
    }

    @Override
    @PreAuthorize("permitAll")
    public List<ParkingZoneDTO> findAll(ParkingZoneFilter filter) {
        return super.findAllDefault(filter);
    }

    @Override
    @PreAuthorize("permitAll")
    public ParkingZoneDTO findById(Long id) {
        return super.findByIdDefault(id);
    }

    @Override
    @PreAuthorize("permitAll")
    public void deleteById(Long id) {
        super.deleteByIdDefault(id);
    }

    @Override
    @PreAuthorize("permitAll")
    public long create(ParkingZoneDTO parkingZoneDTO) {
        return super.createDefault(parkingZoneDTO);
    }

    @Override
    @PreAuthorize("permitAll")
    public void update(ParkingZoneDTO parkingZoneDTO) {

        super.updateDefault(parkingZoneDTO.getId(),parkingZoneDTO);
    }
}
