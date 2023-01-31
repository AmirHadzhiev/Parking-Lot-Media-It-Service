package de.bonprix.model.forPrinting;

import de.bonprix.model.Car;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class ParkingPlaceToStringDTO {

    Long id ;

    String number;


    Car car;

    @Override
    public String toString() {
        return "id=" + id +
                ", number='" + number ;
    }
}
