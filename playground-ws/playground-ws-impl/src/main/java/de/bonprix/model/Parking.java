package de.bonprix.model;

import de.bonprix.dto.ParkingDTO;
import de.bonprix.service.BasicTableDtoConverter;
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
       // name = "parking"
       // schema = "Project"
         )
@SequenceGenerator(
      //  schema = "Project",
        name = "SEQ_parkings",
        sequenceName = "SEQ_parkings",
        allocationSize = 1)
public class Parking implements BasicTableDtoConverter<ParkingDTO> {

    @Id
    @GeneratedValue(
            generator = "SEQ_parkings",
            strategy = GenerationType.SEQUENCE)
    @Column(
            name = "id")
    private Long id;

    @Column
    String name;


    //@OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    //Set<ParkingZone> parkingZones;

    @OneToMany
    private Set<ParkingZone> parkingZones;

    @Column
    String city;

    @Column
    String street;

    @Column
    String zipCode;


    @Override
    public void updateDto(ParkingDTO dto) {
        dto.setId(getId());
        dto.setName(getName());
        dto.setCity(getCity());
        dto.setStreet(getStreet());
        dto.setZipCode(getZipCode());
    }

    @Override
    public void updateEntity(ParkingDTO dto) {
        setId(dto.getId());
        setName(dto.getName());
        setCity(dto.getCity());
        setStreet(dto.getCity());
        setZipCode(dto.getZipCode());

    }
}
