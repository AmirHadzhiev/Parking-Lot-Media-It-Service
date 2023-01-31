package de.bonprix.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ParkingPlaceDTO extends Entity {
    private static final long serialVersionUID = 1L;
    String number;

    CarDTO car;

    ParkingZoneDTO parkingZone ;

}
