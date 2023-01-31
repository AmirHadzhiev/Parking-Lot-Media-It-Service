package de.bonprix.model;

import de.bonprix.dto.ParkingDTO;
import de.bonprix.dto.ParkingPlaceDTO;
import de.bonprix.dto.ParkingZoneDTO;
import de.bonprix.service.BasicTableDtoConverter;
import de.bonprix.service.DtoEntityConverterUtils;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(
        name = "parking_zones"
       // schema = "Project"
         )
@SequenceGenerator(
       // schema = "Project",
        name = "SEQ_parking_zone",
        sequenceName = "SEQ_parking_zone",
        allocationSize = 1)
public class ParkingZone implements BasicTableDtoConverter<ParkingZoneDTO> {

    @Id
    @GeneratedValue(
            generator = "SEQ_parking_zone",
            strategy = GenerationType.SEQUENCE)
    @Column(
            name = "id")
    private Long id;

    @Column
    String name;


  // @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
  // Set<ParkingPlace> parkingPlace;

    @OneToMany(mappedBy = "parkingZone")
    private Set<ParkingPlace> parkingPlace;


    @ManyToOne()
    @JoinColumn(name = "PARKING_ID")
    private Parking parking;



    @Override
    public void updateDto(ParkingZoneDTO dto) {
        dto.setId(getId());
        dto.setName(getName());
        dto.setParking(DtoEntityConverterUtils.convertToDto(getParking(), ParkingDTO.class));
    }

    @Override
    public void updateEntity(ParkingZoneDTO dto) {
        setId(dto.getId());
        setName(dto.getName());
        setParking(DtoEntityConverterUtils.convertToEntity(dto.getParking(), Parking.class));
    }
}
