package de.bonprix.model.forPrinting;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ParkingZoneToStringDTO {

    Long id;

    String name;

    @Override
    public String toString() {
        return
                "id= " + id +
                "   name= " + name ;
    }
}
