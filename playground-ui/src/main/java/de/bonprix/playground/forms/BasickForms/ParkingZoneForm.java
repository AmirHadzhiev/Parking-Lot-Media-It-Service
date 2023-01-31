package de.bonprix.playground.forms.BasickForms;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import de.bonprix.dto.ParkingZoneDTO;


import java.util.List;

public class ParkingZoneForm extends FormLayout {
    TextField name = new TextField("Name");
    ComboBox<ParkingZoneDTO> parkingZone = new ComboBox<>("ParkingZone");
    Button save = new Button("Save");
    Button  delete = new Button("Delete");
    Button  cancel = new Button("Cancel");

    public ParkingZoneForm(List<ParkingZoneDTO> parkingZoneList) {
        addClassName("parkingZone-form");
        parkingZone.setItems(parkingZoneList);
        parkingZone.setItemLabelGenerator(ParkingZoneDTO::getName);
        add(
                name,
                createButtonLayout()
        );

    }

    private Component createButtonLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        cancel.addClickShortcut(Key.ESCAPE);
        return new HorizontalLayout(save,delete,cancel);
    }
}
