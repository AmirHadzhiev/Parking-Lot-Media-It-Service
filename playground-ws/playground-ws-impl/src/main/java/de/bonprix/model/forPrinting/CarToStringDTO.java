package de.bonprix.model.forPrinting;

import de.bonprix.model.ParkingPlace;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class CarToStringDTO {

    Long id;

    String plateNumber;

    ParkingPlace parkingPlaces;

    @Override
    public String toString() {
        String result =  "   Car id= " + id +
                "        plateNumber= " + plateNumber ;
        if (parkingPlaces!=null){
            result+= "ParkingPlace number = " + parkingPlaces.getNumber();
        }
        return result;
    }
}
