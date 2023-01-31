package de.bonprix.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter

@JsonIgnoreProperties(ignoreUnknown = true)
public class CarDTO extends Entity {
    private static final long serialVersionUID = 1L;

    String plateNumber;
   // ParkingPlaceDTO parkingPlace;


}
