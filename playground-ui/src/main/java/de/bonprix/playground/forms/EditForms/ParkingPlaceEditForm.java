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

import de.bonprix.dto.ParkingPlaceDTO;

public class ParkingPlaceEditForm extends FormLayout {

    private ParkingPlaceDTO parkingPlaceDTO;

    TextField numberField = new TextField("Name");


    Binder<ParkingPlaceDTO> binder = new BeanValidationBinder<>(ParkingPlaceDTO.class);

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button cancel = new Button("Cancel");

    public ParkingPlaceEditForm() {
        addClassName("parking-form");
        binder.forField(numberField).asRequired().bind(ParkingPlaceDTO::getNumber, ParkingPlaceDTO::setNumber);
        binder.bindInstanceFields(this);
        add(numberField, createButtonsLayout());

    }

    private HorizontalLayout createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickListener(event -> validateAndSave());
         delete.addClickListener(event -> fireEvent(new ParkingPlaceEditForm.DeleteEvent(this, parkingPlaceDTO)));
          cancel.addClickListener(event -> fireEvent(new ParkingPlaceEditForm.CloseEvent(this)));

        binder.addStatusChangeListener(event -> save.setEnabled(binder.isValid()));

        return new HorizontalLayout(save, delete, cancel);
    }

    public void setParking(ParkingPlaceDTO parkingPlaceDTO) {
        this.parkingPlaceDTO = parkingPlaceDTO;
        binder.readBean(parkingPlaceDTO);
    }

    private void validateAndSave() {
        try {
            binder.writeBean(parkingPlaceDTO)
            ;
              fireEvent(new ParkingPlaceEditForm.SaveEvent(this, parkingPlaceDTO));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    public static abstract class ParkingFormEvent extends ComponentEvent<ParkingPlaceEditForm> {
        private ParkingPlaceDTO parkingPlaceDTO;

        protected ParkingFormEvent(ParkingPlaceEditForm source, ParkingPlaceDTO parkingPlaceDTO) {
            super(source, false);
            this.parkingPlaceDTO = parkingPlaceDTO;
        }

        public ParkingPlaceDTO getParking() {
            return parkingPlaceDTO;
        }
    }

    public static class SaveEvent extends ParkingPlaceEditForm.ParkingFormEvent {
        SaveEvent(ParkingPlaceEditForm source, ParkingPlaceDTO parkingPlaceDTO) {
            super(source, parkingPlaceDTO);
        }
    }

    public static class DeleteEvent extends ParkingPlaceEditForm.ParkingFormEvent {
        DeleteEvent(ParkingPlaceEditForm source, ParkingPlaceDTO parkingPlaceDTO) {
            super(source, parkingPlaceDTO);
        }
    }

    public static class CloseEvent extends ParkingPlaceEditForm.ParkingFormEvent {
        CloseEvent(ParkingPlaceEditForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
