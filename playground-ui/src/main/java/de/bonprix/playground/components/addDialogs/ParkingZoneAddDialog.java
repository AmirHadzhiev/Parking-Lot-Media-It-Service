package de.bonprix.playground.components.addDialogs;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;
import de.bonprix.dto.ParkingDTO;
import de.bonprix.dto.ParkingZoneDTO;


import java.util.List;
import java.util.Set;


public class ParkingZoneAddDialog extends Dialog {
    TextField nameField = new TextField("Name");

    Binder<ParkingZoneDTO> binder = new BeanValidationBinder<>(ParkingZoneDTO.class);

    ComboBox<ParkingDTO> parkings = new ComboBox<>("Parking");

    public ParkingZoneAddDialog(List<ParkingDTO> parkingSet) {
        binder.forField(nameField).asRequired().bind(ParkingZoneDTO::getName, ParkingZoneDTO::setName);
        binder.forField(parkings).asRequired().bind(ParkingZoneDTO::getParking,ParkingZoneDTO::setParking);

        binder.bindInstanceFields(this);
        parkings.setItems(parkingSet);
        parkings.setItemLabelGenerator(ParkingDTO::getName);


        H2 headline = new H2("Create new Parking Zone");


        headline.getStyle().set("margin", "var(--lumo-space-m) 0 0 0")
                .set("font-size", "1.5em").set("font-weight", "bold");



        VerticalLayout fieldLayout = new VerticalLayout(nameField,parkings);
        fieldLayout.setSpacing(false);
        fieldLayout.setPadding(false);
        fieldLayout.setAlignItems(FlexComponent.Alignment.STRETCH);





        Button cancelButton = new Button("Cancel", e -> {
            this.close();
            //nameField.clear();
            UI.getCurrent().getPage().reload();
        });

        Button saveButton = new Button("Save", e -> fireEvent(new ParkingZoneAddDialog.CloseEvent(this)));
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        saveButton.addClickListener(buttonClickEvent -> {
            ParkingZoneDTO parkingZoneDTO = new ParkingZoneDTO();
            try {

                binder.writeBean(parkingZoneDTO);
                fireEvent(new ParkingZoneAddDialog.SaveEvent(this,parkingZoneDTO));
                this.close();
                //nameField.clear();
                UI.getCurrent().getPage().reload();
            } catch (ValidationException e) {
                throw new RuntimeException(e);
            }

        });

        HorizontalLayout buttonLayout = new HorizontalLayout(cancelButton,
                saveButton);
        buttonLayout
                .setJustifyContentMode(FlexComponent.JustifyContentMode.END);

        VerticalLayout dialogLayout = new VerticalLayout(headline, fieldLayout,
                buttonLayout);
        dialogLayout.setPadding(false);
        dialogLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        dialogLayout.getStyle().set("width", "300px").set("max-width", "100%");

        add(dialogLayout);

        binder.addStatusChangeListener(event -> saveButton.setEnabled(binder.isValid()));
    }


    public static abstract class ParkingDialogEvent extends ComponentEvent<Dialog> {


        private ParkingZoneDTO parkingZoneDTO;
        public ParkingDialogEvent(Dialog source, ParkingZoneDTO parkingZoneDTO) {
            super(source, false);
            this.parkingZoneDTO = parkingZoneDTO;
        }

        public ParkingZoneDTO getParkingZoneDTO() {
            return parkingZoneDTO;
        }
    }

    public static class SaveEvent extends ParkingZoneAddDialog.ParkingDialogEvent {

        public SaveEvent(Dialog source, ParkingZoneDTO parkingZoneDTO) {
            super(source, parkingZoneDTO);
        }
    }

    public static class DeleteEvent extends ParkingZoneAddDialog.ParkingDialogEvent {
        DeleteEvent(Dialog source, ParkingZoneDTO parkingZoneDTO) {
            super(source, parkingZoneDTO);
        }

    }

    public static class CloseEvent extends ParkingZoneAddDialog.ParkingDialogEvent {
        CloseEvent(Dialog source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
