package de.bonprix.model.forPrinting;

import de.bonprix.model.Car;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PlaceInfoDTO {

    Long id ;

    String number;


    Car car;

    @Override
    public String toString() {
        StringBuilder finalInput = new StringBuilder();
        String firstInput = "Parking place with id " + id +
                " have number - " + number + System.lineSeparator();
        finalInput.append(firstInput);
        if (car!=null){
           finalInput.append( String.format("In the place have car with id - %s and number - %s"
                    ,car.getId().toString(),car.getPlateNumber()));
        } else {
            finalInput.append(String.format("Don't have car in the place%n"));
        }

      return finalInput.toString();
    }

}
