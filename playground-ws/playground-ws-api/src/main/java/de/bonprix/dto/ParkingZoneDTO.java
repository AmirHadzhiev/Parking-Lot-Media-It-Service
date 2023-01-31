package de.bonprix.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import de.bonprix.dto.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ParkingZoneDTO extends Entity {
    private static final long serialVersionUID = 1L;
    String name;

    ParkingDTO parking;



}
