package de.bonprix.playground.forms.BasickForms;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import de.bonprix.dto.CarDTO;

import java.util.List;

public class CarForm extends FormLayout {
    TextField plateNumber = new TextField("PLate NUmber");
    ComboBox<CarDTO> car = new ComboBox<>("Car");
    Button  save = new Button("Save");
    Button  delete = new Button("Delete");
    Button  cancel = new Button("Cancel");

    public CarForm(List<CarDTO> cars) {
        addClassName("car-form");
       car.setItems(cars);
       car.setItemLabelGenerator(CarDTO::getPlateNumber);
       add(
               plateNumber,
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
