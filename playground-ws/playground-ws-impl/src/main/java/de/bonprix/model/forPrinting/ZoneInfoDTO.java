package de.bonprix.model.forPrinting;

import de.bonprix.model.ParkingPlace;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ZoneInfoDTO {

    Long id;

    String name;

    List<ParkingPlace> parkingPlace;

    @Override
    public String toString() {
        StringBuilder finalInput = new StringBuilder();

             String firstInput =   "Parking Zone with id " + id  +
                        " have name - " + name + System.lineSeparator() +
                     "Parking Zone contains the following places: " + System.lineSeparator() ;
             finalInput.append(firstInput);
        for (ParkingPlace place : this.parkingPlace) {
           finalInput.append(String.format("%s%n",place.getNumber()));
        }
    return finalInput.toString();
    }
}


