package de.bonprix.playground.forms.EditForms;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;
import de.bonprix.dto.CarDTO;


public class CarEditForm extends FormLayout {

    private CarDTO car;

    TextField plateNumberField = new TextField("Plate Number");


    Binder<CarDTO> binder = new BeanValidationBinder<>(CarDTO.class);

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button cancel = new Button("Cancel");

    public CarEditForm() {
        addClassName("car-form");
        binder.forField(plateNumberField).asRequired().bind(CarDTO::getPlateNumber,CarDTO::setPlateNumber);
        binder.bindInstanceFields(this);
        add(plateNumberField, createButtonsLayout());
    }

    private HorizontalLayout createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event ->{
            UI.getCurrent().getPage().reload();
            fireEvent(new CarEditForm.DeleteEvent(this, car));
        });
        cancel.addClickListener(event -> fireEvent(new CarEditForm.CloseEvent(this)));

        binder.addStatusChangeListener(event -> save.setEnabled(binder.isValid()));

        return new HorizontalLayout(save, delete, cancel);
    }

    public void setParking(CarDTO car) {
        this.car = car;
        binder.readBean(car);
    }

    private void validateAndSave() {
        try {
            binder.writeBean(car);
            fireEvent(new CarEditForm.SaveEvent(this, car));
            UI.getCurrent().getPage().reload();
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    public static abstract class ParkingFormEvent extends ComponentEvent<CarEditForm> {
        private CarDTO car;

        protected ParkingFormEvent(CarEditForm source, CarDTO car) {
            super(source, false);
            this.car = car;
        }

        public CarDTO getCar() {
            return car;
        }
    }
    public static class SaveEvent extends CarEditForm.ParkingFormEvent {
        SaveEvent(CarEditForm source, CarDTO car) {
            super(source, car);
        }
    }

    public static class DeleteEvent extends CarEditForm.ParkingFormEvent {
        DeleteEvent(CarEditForm source, CarDTO car) {
            super(source, car);
        }
    }

    public static class CloseEvent extends CarEditForm.ParkingFormEvent {
        CloseEvent(CarEditForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }

}
