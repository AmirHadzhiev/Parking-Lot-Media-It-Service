package de.bonprix.model;

import de.bonprix.dto.CarDTO;

import de.bonprix.dto.ParkingDTO;
import de.bonprix.dto.ParkingPlaceDTO;
import de.bonprix.service.BasicTableDtoConverter;
import de.bonprix.service.DtoEntityConverterUtils;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.karneim.pojobuilder.GeneratePojoBuilder;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor

@GeneratePojoBuilder(
        intoPackage = "*.builder")
@Entity
@Table(
        name = "car"
       // schema = "Project"
)
@SequenceGenerator(
      //  schema = "Project",
        name = "SEQ_cars",
        sequenceName = "SEQ_cars",
        allocationSize = 1)
public class Car implements BasicTableDtoConverter<CarDTO> {

    @Id
    @GeneratedValue(
            generator = "SEQ_cars",
            strategy = GenerationType.SEQUENCE)
    @Column(
            name = "id")
    private Long id;


    @Column(name = "plate_number",unique = true)
    String plateNumber;


   // @OneToOne()
   // ParkingPlace parkingPlace;



   @Override
   public void updateEntity(CarDTO dto){

           setId(dto.getId());
           setPlateNumber(dto.getPlateNumber());

   }
   @Override
    public void updateDto(CarDTO dto){
       dto.setId(getId());
       dto.setPlateNumber(getPlateNumber());

   }
}
