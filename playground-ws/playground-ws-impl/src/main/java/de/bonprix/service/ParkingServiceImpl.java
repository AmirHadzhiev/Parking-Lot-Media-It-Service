package de.bonprix.service;

import java.util.List;

import javax.annotation.Resource;

import de.bonprix.annotation.RestService;

import de.bonprix.dto.ParkingDTO;

import de.bonprix.model.Parking;
import de.bonprix.repository.jpa.ParkingRepository;
import de.bonprix.service.Parking.ParkingFilter;
import de.bonprix.service.Parking.ParkingService;
import org.springframework.security.access.prepost.PreAuthorize;

@RestService
public class ParkingServiceImpl
		extends AbstractBasicService<Parking , ParkingDTO, ParkingFilter, ParkingRepository>
		implements ParkingService {


	@Resource
	private ParkingRepository parkingJpaRepository;

	public ParkingServiceImpl() {
		super(Parking.class, ParkingDTO.class, ParkingFilter.class);
	}
	public ParkingServiceImpl(Class<Parking> entityClazz, Class<ParkingDTO> dtoClazz, Class<ParkingFilter> filterClazz) {
		super(entityClazz, dtoClazz, filterClazz);
	}

	@Override
	@PreAuthorize("permitAll")
	protected ParkingRepository getRepository() {
		return parkingJpaRepository;
	}

	@Override
	@PreAuthorize("permitAll")
	public List<ParkingDTO> findAll(ParkingFilter filter) {
		return super.findAllDefault(filter);
	}

	@Override
	@PreAuthorize("permitAll")
	public ParkingDTO findById(Long id) {
		return super.findByIdDefault(id);
	}

	@Override
	@PreAuthorize("permitAll")
	public void deleteById(Long id) {
		super.deleteByIdDefault(id);
	}

	@Override
	@PreAuthorize("permitAll")
	public long create(ParkingDTO parkingDTO) {
		return super.createDefault(parkingDTO);
	}

	@Override
	@PreAuthorize("permitAll")
	public void update(ParkingDTO parkingDTO) {
		super.updateDefault(parkingDTO.getId(),parkingDTO);
	}
}
