package de.bonprix.model;


import de.bonprix.dto.CarDTO;
import de.bonprix.dto.ParkingDTO;
import de.bonprix.dto.ParkingPlaceDTO;
import de.bonprix.dto.ParkingZoneDTO;
import de.bonprix.model.Car;
import de.bonprix.service.BasicTableDtoConverter;

import de.bonprix.service.DtoEntityConverterUtils;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.karneim.pojobuilder.GeneratePojoBuilder;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@GeneratePojoBuilder(
        intoPackage = "*.builder")
@Entity
@Table(
        name = "parking_places"
        //schema = "Project"
)
@SequenceGenerator(
       // schema = "Project",
        name = "SEQ_parking_place",
        sequenceName = "SEQ_parking_place",
        allocationSize = 1)
public class ParkingPlace implements BasicTableDtoConverter<ParkingPlaceDTO> {

    @Id
    @GeneratedValue(
            generator = "SEQ_parking_place",
            strategy = GenerationType.SEQUENCE)
    @Column(
            name = "id")
    private Long id;

    @Column(name = "parking_place_number")
    private String number;

//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "car_id")

    @ManyToOne()
    @JoinColumn(name = "car_id")
    private Car car;

    @ManyToOne()
    @JoinColumn(name = "PARKING_ZONE_ID")
    private ParkingZone parkingZone ;


    @Override
    public void updateDto(ParkingPlaceDTO dto) {

        dto.setId(getId());
        dto.setNumber(getNumber());
        dto.setCar(DtoEntityConverterUtils.convertToDto(getCar(),CarDTO.class));
        dto.setParkingZone(DtoEntityConverterUtils.convertToDto(getParkingZone(), ParkingZoneDTO.class));



    }

    @Override
    public void updateEntity(ParkingPlaceDTO dto) {
        setId(dto.getId());
        setNumber(dto.getNumber());
        if (dto.getCar()!=null) {
            setCar(DtoEntityConverterUtils.convertToEntity(dto.getCar(),Car.class));
        } else {
            setCar(null);
        }
        setParkingZone(DtoEntityConverterUtils.convertToEntity(dto.getParkingZone(), ParkingZone.class));
    }
}
