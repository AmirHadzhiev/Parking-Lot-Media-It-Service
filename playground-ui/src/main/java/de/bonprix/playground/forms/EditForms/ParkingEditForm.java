package de.bonprix.playground.forms.EditForms;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;
import de.bonprix.dto.ParkingDTO;

public class ParkingEditForm extends FormLayout {


    private ParkingDTO parking;

    TextField nameField = new TextField("Name");
    TextField cityField = new TextField("City");
    TextField streetField = new TextField("Street");
    TextField zipCode = new TextField("Zip code");

    Binder<ParkingDTO> binder = new BeanValidationBinder<>(ParkingDTO.class);

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button cancel = new Button("Cancel");

    public ParkingEditForm() {
        addClassName("parking-form");
        binder.forField(nameField).asRequired().bind(ParkingDTO::getName, ParkingDTO::setName);
        binder.forField(cityField).asRequired().bind(ParkingDTO::getCity, ParkingDTO::setCity);
        binder.forField(streetField).asRequired().bind(ParkingDTO::getStreet, ParkingDTO::setStreet);
        binder.forField(zipCode).asRequired().bind(ParkingDTO::getZipCode, ParkingDTO::setZipCode);
        binder.bindInstanceFields(this);


        add(nameField, cityField, streetField, zipCode, createButtonsLayout());

    }

    private HorizontalLayout createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new DeleteEvent(this, parking)));
        cancel.addClickListener(event -> fireEvent(new CloseEvent(this)));

        binder.addStatusChangeListener(event -> save.setEnabled(binder.isValid()));

        return new HorizontalLayout(save, delete, cancel);
    }

    public void setParking(ParkingDTO parking) {
        this.parking = parking;
        binder.readBean(parking);
    }

    private void validateAndSave() {
        try {
            binder.writeBean(parking)
            ;
            fireEvent(new SaveEvent(this, parking));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    public static abstract class ParkingFormEvent extends ComponentEvent<ParkingEditForm> {
        private ParkingDTO parking;

        protected ParkingFormEvent(ParkingEditForm source, ParkingDTO parking) {
            super(source, false);
            this.parking = parking;
        }

        public ParkingDTO getParking() {
            return parking;
        }
    }
    public static class SaveEvent extends ParkingFormEvent {
        SaveEvent(ParkingEditForm source, ParkingDTO parking) {
            super(source, parking);
        }
    }

    public static class DeleteEvent extends ParkingFormEvent {
        DeleteEvent(ParkingEditForm source, ParkingDTO parking) {
            super(source, parking);
        }
    }

    public static class CloseEvent extends ParkingFormEvent {
        CloseEvent(ParkingEditForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }

}
