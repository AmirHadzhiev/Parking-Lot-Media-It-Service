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
import de.bonprix.dto.ParkingZoneDTO;


public class ParkingZoneEditForm extends FormLayout{
        private ParkingZoneDTO parkingZone;

        TextField nameField = new TextField("Name");

        Binder<ParkingZoneDTO> binder = new BeanValidationBinder<>(ParkingZoneDTO.class);

        Button save = new Button("Save");
        Button delete = new Button("Delete");
        Button cancel = new Button("Cancel");

        public ParkingZoneEditForm() {
            addClassName("parkingZone-form");
            binder.forField(nameField).asRequired().bind(ParkingZoneDTO::getName, ParkingZoneDTO::setName);
            binder.bindInstanceFields(this);
            add(nameField,createButtonsLayout());

        }

        private HorizontalLayout createButtonsLayout() {
            save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
            cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

            save.addClickListener(event -> validateAndSave());
            delete.addClickListener(event -> fireEvent(new DeleteEvent(this, parkingZone)));
            cancel.addClickListener(event -> fireEvent(new CloseEvent(this)));

            binder.addStatusChangeListener(event -> save.setEnabled(binder.isValid()));

            return new HorizontalLayout(save, delete, cancel);
        }

        public void setParking(ParkingZoneDTO parkingZone) {
            this.parkingZone = parkingZone;
            binder.readBean(parkingZone);
        }

        private void validateAndSave() {
            try {
                binder.writeBean(parkingZone)
                ;
                fireEvent(new ParkingZoneEditForm.SaveEvent(this, parkingZone));
            } catch (ValidationException e) {
                e.printStackTrace();
            }
        }

        public static abstract class ParkingFormEvent extends ComponentEvent<ParkingZoneEditForm> {
            private ParkingZoneDTO parkingZone;

            protected ParkingFormEvent(ParkingZoneEditForm source, ParkingZoneDTO parkingZone) {
                super(source, false);
                this.parkingZone = parkingZone;
            }

            public ParkingZoneDTO getParking() {
                return parkingZone;
            }
        }
        public static class SaveEvent extends ParkingFormEvent {
            SaveEvent(ParkingZoneEditForm source, ParkingZoneDTO parkingZone) {
                super(source, parkingZone);
            }
        }

        public static class DeleteEvent extends ParkingFormEvent {
            DeleteEvent(ParkingZoneEditForm source, ParkingZoneDTO parkingZone) {
                super(source, parkingZone);
            }
        }

        public static class CloseEvent extends ParkingFormEvent {
            CloseEvent(ParkingZoneEditForm source) {
                super(source, null);
            }
        }

        public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                      ComponentEventListener<T> listener) {
            return getEventBus().addListener(eventType, listener);
        }

    }

