package de.bonprix.playground.forms.BasickForms;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import de.bonprix.dto.ParkingPlaceDTO;


import java.util.List;

public class ParkingPlaceForm extends FormLayout {
    TextField number = new TextField("Number");
    ComboBox<ParkingPlaceDTO> parkingPlace = new ComboBox<>("ParkingPlace");
    Button save = new Button("Save");
    Button  delete = new Button("Delete");
    Button  cancel = new Button("Cancel");

    public ParkingPlaceForm(List<ParkingPlaceDTO> cars) {
        addClassName("parkingPlace-form");
        parkingPlace.setItems(cars);
        parkingPlace.setItemLabelGenerator(ParkingPlaceDTO::getNumber);
        add(
                number,
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
