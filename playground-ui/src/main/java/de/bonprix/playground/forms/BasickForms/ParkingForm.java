package de.bonprix.playground.forms.BasickForms;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;


import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import de.bonprix.dto.ParkingDTO;


import java.util.List;

public class ParkingForm extends FormLayout {
    TextField name = new TextField("Name");
    TextField city = new TextField("City");
    TextField street = new TextField("Street");
    TextField zipCode = new TextField("Zip Code");
    ComboBox<ParkingDTO> parking = new ComboBox<>("Parking");
    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button cancel = new Button("Cancel");
    Binder<ParkingDTO> binder = new BeanValidationBinder<>(ParkingDTO.class);

    public ParkingForm(List<ParkingDTO> parkingList) {
        addClassName("parking-form");
        parking.setItems(parkingList);
        parking.setItemLabelGenerator(ParkingDTO::getName);
        binder.bindInstanceFields(this);



        add(
                name,
                city,
                street,
                zipCode,
                createButtonLayout()

        );

    }

    private Component createButtonLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        cancel.addClickShortcut(Key.ESCAPE);
        return new HorizontalLayout(save, delete, cancel);
    }
    public ParkingDTO setParking(ParkingDTO parkingDTO){
        binder.setBean(parkingDTO);
       return binder.getBean();
    }


}
